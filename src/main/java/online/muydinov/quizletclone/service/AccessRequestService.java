package online.muydinov.quizletclone.service;

import com.sun.jdi.request.DuplicateRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.AccessRequestDTO;
import online.muydinov.quizletclone.dto.Response;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccessRequestService {

    private final AccessRequestRepository accessRequestRepository;
    private final CardSetRepository cardSetRepository;
    private final UserRepository userRepository;

//    public String requestAccess(Long setId) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//
//        User requester = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        CardSet cardSet = cardSetRepository.findById(setId)
//                .orElseThrow(() -> new RuntimeException("Card Set not found"));
//
//        if (cardSet.getCreator().equals(requester)) {
//            return "You are the owner of this Set";
//        }
//
//        if (cardSet.getApprovedUsers().contains(requester)) {
//            return "You already have access!";
//        }
//
//        accessRequestRepository.findByRequesterAndCardSet(requester, cardSet)
//                .ifPresent(request -> {
//                    throw new DuplicateRequestException("Access request already sent!");
//                });
//
//        AccessRequest request = new AccessRequest();
//        request.setRequester(requester);
//        request.setCardSet(cardSet);
//        request.setStatus("PENDING");
//        accessRequestRepository.save(request);
//
//        return "Request sent successfully!";
//    }

    @Transactional
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

    public List<AccessRequestDTO> getPendingRequests(Long setId, String username) {
        // Fetch the card set owner's username
        String creatorUsername = cardSetRepository.findOwnerUsernameByCardSetId(setId)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));

        if (!creatorUsername.equals(username)) {
            throw new UnauthorizedAccessException("You are not authorized to view these requests");
        }

        return accessRequestRepository.findPendingRequestsByCardSetId(setId, "PENDING")
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