package online.muydinov.quizletclone.service;

import com.sun.jdi.request.DuplicateRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.AccessRequestDTO;
import online.muydinov.quizletclone.dto.CardSetDTO;
import online.muydinov.quizletclone.dto.Response;
import online.muydinov.quizletclone.dto.UserDTO;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.AccessRequest;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.exceptions.CardSetNotFoundException;
import online.muydinov.quizletclone.exceptions.UnauthorizedAccessException;
import online.muydinov.quizletclone.repository.CardSetRepository;
import online.muydinov.quizletclone.repository.AccessRequestRepository;
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

        // Fetch the card set
        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException("Card set not found"));

        // Check if the user is the creator or already has access
        if (cardSet.getCreator().getId().equals(requester.getId())) {
            return new Response("message", "You are the creator of this card set.");
        }

        if (cardSet.getApprovedUsers().stream().anyMatch(u -> u.getId().equals(requester.getId()))) {
            return new Response("message", "You already have access to this card set.");
        }

        // Check if the user already has a pending or declined request
        Optional<AccessRequest> existingRequest = accessRequestRepository
                .findByCardSetIdAndRequesterId(cardSetId, requester.getId());

        if (existingRequest.isPresent()) {
            AccessRequest request = existingRequest.get();
            if (request.getStatus().equals("PENDING")) {
                return new Response("message", "You already have a pending request for this card set.");
            } else if (request.getStatus().equals("DECLINED")) {
                return new Response("message", "Your previous request for this card set was declined.");
            }
        }

        // If the user doesn't have access, create a new access request
        AccessRequest request = new AccessRequest();
        request.setRequester(user);
        request.setCardSet(cardSet);
        request.setStatus("PENDING");
        accessRequestRepository.save(request);

        return new Response("message", "Request sent successfully!");
    }

    public Response respondToRequest(Long cardSetId, Long requestId, boolean approve) {
        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));

        AccessRequest request = accessRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (!request.getCardSet().getId().equals(cardSetId)) {
            throw new RuntimeException("Request does not belong to the specified card set");
        }

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!cardSet.getCreator().getUsername().equals(username)) {
            return new Response("message", "Only the creator can approve or reject requests!");
        }

        if (approve) {
            request.setStatus("APPROVED");
            cardSet.getApprovedUsers().add(request.getRequester());
            cardSetRepository.save(cardSet);
        } else {
            request.setStatus("REJECTED");
        }

        accessRequestRepository.save(request);
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