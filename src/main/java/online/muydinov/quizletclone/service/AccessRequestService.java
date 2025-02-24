package online.muydinov.quizletclone.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.AccessRequestDTO;
import online.muydinov.quizletclone.dto.Response;
import online.muydinov.quizletclone.dto.UserDTO;
import online.muydinov.quizletclone.entity.AccessRequest;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.exceptions.CardSetNotFoundException;
import online.muydinov.quizletclone.exceptions.UnauthorizedAccessException;
import online.muydinov.quizletclone.repository.AccessRequestRepository;
import online.muydinov.quizletclone.repository.CardSetRepository;
import online.muydinov.quizletclone.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccessRequestService {

    private final AccessRequestRepository accessRequestRepository;
    private final CardSetRepository cardSetRepository;
    private final UserRepository userRepository;
    private final MyUserDetailsService myUserDetailsService;

    public Response requestAccess(Long cardSetId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserDTO requester = userRepository.findUserDTOByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User user = new User();
        user.setId(requester.getId());

        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException("Card set not found"));

        if (cardSet.getCreator().getId().equals(requester.getId())) {
            return new Response("message", "You are the creator of this card set.");
        }

        if (cardSet.getApprovedUsers().stream().anyMatch(u -> u.getId().equals(requester.getId()))) {
            return new Response("message", "You already have access to this card set.");
        }

        Optional<AccessRequest> existingRequest = accessRequestRepository
                .findByCardSetIdAndRequesterId(cardSetId, requester.getId());

        if (existingRequest.isPresent()) {
            AccessRequest request = existingRequest.get();
            if (request.getStatus().equals("PENDING")) {
                return new Response("message", "You already have a pending request for this card set.");
            } else if (request.getStatus().equals("REJECTED")) {
                existingRequest.get().setStatus("PENDING");
                accessRequestRepository.save(existingRequest.get());
                return new Response("message", "Request sent successfully!");
            }
        }

        AccessRequest request = new AccessRequest();
        request.setRequester(user);
        request.setCardSet(cardSet);
        request.setStatus("PENDING");
        accessRequestRepository.save(request);

        return new Response("message", "Request sent successfully!");
    }

    @Transactional
    public Response respondToRequest(Long cardSetId, Long requestId, boolean approve) {
        // Fetch the existing AccessRequest from the database
        AccessRequest request = accessRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Access request not found"));

        // Validate that the request belongs to the specified cardSetId
        if (!request.getCardSet().getId().equals(cardSetId)) {
            throw new RuntimeException("Invalid cardSetId for the given request");
        }

        // Get the current user's details
        String username = myUserDetailsService.getUsername();
        Long requesterId = myUserDetailsService.getUserIdByUsername(username);

        // Check if the current user is authorized to respond to this request
        if (!request.getCardSet().getCreator().getId().equals(requesterId)) {
            throw new UnauthorizedAccessException("You are not authorized to respond to this request");
        }

        if (approve) {
            // Approve the request
            request.setStatus("APPROVED");

            // Add the user to the approvedUsers set
            cardSetRepository.addApprovedUser(cardSetId, request.getRequester().getId());

            // Delete the access request after approval
            accessRequestRepository.delete(request);
        } else {
            // Reject the request
            request.setStatus("REJECTED");

            // Save the updated request (no need to create a new one)
            accessRequestRepository.save(request);
        }

        return new Response("message", "Request has been " + (approve ? "approved" : "rejected"));
    }

    public List<AccessRequestDTO> getPendingRequests(Long cardsetId) {
        String creatorUsername = cardSetRepository.findOwnerUsernameByCardSetId(cardsetId)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));

        String username = myUserDetailsService.getUsername();
        if (!creatorUsername.equals(username)) {
            throw new UnauthorizedAccessException("You are not authorized to view these requests");
        }

        return accessRequestRepository.findPendingRequestsByCardSetId(cardsetId, "PENDING")
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AccessRequestDTO convertToDTO(AccessRequest request) {
        AccessRequestDTO dto = new AccessRequestDTO();
        dto.setId(request.getId());
        dto.setCardSetId(request.getCardSet().getId());
        dto.setCardSetName(request.getCardSet().getName());
        dto.setRequesterUsername(request.getRequester().getUsername());
        dto.setStatus(request.getStatus());
        return dto;
    }
}