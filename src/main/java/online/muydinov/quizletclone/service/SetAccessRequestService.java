package online.muydinov.quizletclone.service;

import com.sun.jdi.request.DuplicateRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.SetAccessRequestDTO;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.SetAccessRequest;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.enums.RequestStatus;
import online.muydinov.quizletclone.exceptions.CardSetNotFoundException;
import online.muydinov.quizletclone.exceptions.UnauthorizedAccessException;
import online.muydinov.quizletclone.repository.CardSetRepository;
import online.muydinov.quizletclone.repository.SetAccessRequestRepository;
import online.muydinov.quizletclone.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SetAccessRequestService {

    private final SetAccessRequestRepository accessRequestRepository;
    private final CardSetRepository cardSetRepository;
    private final UserRepository userRepository;
    private final MyUserDetailsService myUserDetailsService;

    public String requestAccess(Long setId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User requester = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        CardSet cardSet = cardSetRepository.findById(setId)
                .orElseThrow(() -> new RuntimeException("Card Set not found"));

        if (cardSet.getCreator().equals(requester)) {
            return "You are the owner of this Set";
        }

        if (cardSet.getApprovedUsers().contains(requester)) {
            return "You already have access!";
        }

        accessRequestRepository.findByRequesterAndCardSet(requester, cardSet)
                .ifPresent(request -> { throw new DuplicateRequestException("Access request already sent!"); });

        SetAccessRequest request = new SetAccessRequest();
        request.setRequester(requester);
        request.setCardSet(cardSet);
        request.setStatus(RequestStatus.PENDING);
        accessRequestRepository.save(request);

        return "Request sent successfully!";
    }

    @Transactional
    public String respondToRequest(Long cardSetId, Long requestId, boolean approve) {
        System.out.println("Card Set ID: " + cardSetId);
        System.out.println("Request ID: " + requestId);
        System.out.println("Approve: " + approve);

        // Validate cardSetId
        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));

        // Validate requestId
        SetAccessRequest request = accessRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Ensure the request belongs to the card set
        if (!request.getCardSet().getId().equals(cardSetId)) {
            throw new RuntimeException("Request does not belong to the specified card set");
        }

        // Ensure the logged-in user is the creator of the card set
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!cardSet.getCreator().getUsername().equals(username)) {
            return "Only the creator can approve or reject requests!";
        }

        // Update the request status
        if (approve) {
            request.setStatus(RequestStatus.APPROVED);
            cardSet.getApprovedUsers().add(request.getRequester()); // Add the user to the approved list
            cardSetRepository.save(cardSet);
        } else {
            request.setStatus(RequestStatus.REJECTED);
        }

        accessRequestRepository.save(request);
        return "Request has been " + (approve ? "approved" : "rejected");
    }

    public List<SetAccessRequestDTO> getPendingRequests(Long setId, String username) {
        // Fetch the card set owner's username
        String creatorUsername = cardSetRepository.findOwnerUsernameByCardSetId(setId)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));

        // Check if the logged-in user is the owner of the card set
        if (!creatorUsername.equals(username)) {
            throw new UnauthorizedAccessException("You are not authorized to view these requests");
        }

        // Fetch pending requests for the card set
        return accessRequestRepository.findPendingRequestsByCardSetId(setId, RequestStatus.PENDING)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SetAccessRequestDTO convertToDTO(SetAccessRequest request) {
        SetAccessRequestDTO dto = new SetAccessRequestDTO();
        dto.setId(request.getId());
        dto.setCardSetId(request.getCardSet().getId());
        dto.setCardSetName(request.getCardSet().getName());
        dto.setRequesterUsername(request.getRequester().getUsername());
        dto.setStatus(request.getStatus());
        return dto;
    }
}