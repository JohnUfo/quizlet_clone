package online.muydinov.quizletclone.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.record.AccessRequestRecord;
import online.muydinov.quizletclone.record.Response;
import online.muydinov.quizletclone.record.UserRecord;
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

        UserRecord requester = userRepository.findUserRecordByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User user = new User();
        user.setId(requester.id());

        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException("Card set not found"));

        if (cardSet.getCreator().getId().equals(requester.id())) {
            return new Response("message", "You are the creator of this card set.");
        }

        if (cardSet.getApprovedUsers().stream().anyMatch(u -> u.getId().equals(requester.id()))) {
            return new Response("message", "You already have access to this card set.");
        }

        Optional<AccessRequest> existingRequest = accessRequestRepository
                .findByCardSetIdAndRequesterId(cardSetId, requester.id());

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
        AccessRequest request = accessRequestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Access request not found"));

        if (!request.getCardSet().getId().equals(cardSetId)) {
            throw new RuntimeException("Invalid cardSetId for the given request");
        }

        String username = myUserDetailsService.getUsername();
        Long requesterId = myUserDetailsService.getUserIdByUsername(username);

        if (!request.getCardSet().getCreator().getId().equals(requesterId)) {
            throw new UnauthorizedAccessException("You are not authorized to respond to this request");
        }

        if (approve) {
            request.setStatus("APPROVED");
            cardSetRepository.addApprovedUser(cardSetId, request.getRequester().getId());
            accessRequestRepository.delete(request);
        } else {
            request.setStatus("REJECTED");
            accessRequestRepository.save(request);
        }

        return new Response("message", "Request has been " + (approve ? "approved" : "rejected"));
    }

    public List<AccessRequestRecord> getPendingRequests(Long cardsetId) {
        String creatorUsername = cardSetRepository.findOwnerUsernameByCardSetId(cardsetId)
                .orElseThrow(() -> new CardSetNotFoundException("Card Set not found"));

        String username = myUserDetailsService.getUsername();
        if (!creatorUsername.equals(username)) {
            throw new UnauthorizedAccessException("You are not authorized to view these requests");
        }

        return accessRequestRepository.findPendingRequestsByCardSetId(cardsetId, "PENDING")
                .stream()
                .map(this::convertToRecord)
                .collect(Collectors.toList());
    }

    private AccessRequestRecord convertToRecord(AccessRequest request) {
        return new AccessRequestRecord(
                request.getId(),
                request.getCardSet().getId(),
                request.getCardSet().getName(),
                request.getRequester().getUsername(),
                request.getStatus()
        );
    }
}