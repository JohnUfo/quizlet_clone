package online.muydinov.quizletclone.service;

import com.sun.jdi.request.DuplicateRequestException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.SetAccessRequestDTO;
import online.muydinov.quizletclone.entity.CardSet;
import online.muydinov.quizletclone.entity.SetAccessRequest;
import online.muydinov.quizletclone.entity.User;
import online.muydinov.quizletclone.enums.RequestStatus;
import online.muydinov.quizletclone.repository.CardSetRepository;
import online.muydinov.quizletclone.repository.SetAccessRequestRepository;
import online.muydinov.quizletclone.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public String respondToRequest(Long setId, Long requestId, boolean approve) {
        CardSet cardSet = cardSetRepository.findById(setId)
                .orElseThrow(() -> new RuntimeException("Card Set not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!cardSet.getCreator().getUsername().equals(username)) {
            return "Only the creator can approve or reject requests!";
        }

        SetAccessRequest request = accessRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        if (approve) {
            request.setStatus(RequestStatus.APPROVED);
            cardSet.getApprovedUsers().add(request.getRequester());
            cardSetRepository.save(cardSet);
        } else {
            request.setStatus(RequestStatus.REJECTED);
        }

        accessRequestRepository.save(request);
        return "Request has been " + (approve ? "approved" : "rejected");
    }

    public List<SetAccessRequestDTO> getPendingRequests(Long setId) {
        String username = myUserDetailsService.getUsername();
        String creatorUsername = cardSetRepository.findOwnerUsernameByCardSetId(setId);

        if (creatorUsername.isEmpty()) {
            throw new RuntimeException("Card Set not found");
        }

        if (!creatorUsername.equals(username)) {
            throw new RuntimeException("You are not authorized to view these requests");
        }

        return accessRequestRepository.findPendingRequestsByCardSetId(setId, RequestStatus.PENDING);
    }

}