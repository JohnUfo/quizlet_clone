package online.muydinov.quizletclone.controller;

import online.muydinov.quizletclone.record.AccessRequestRecord;
import online.muydinov.quizletclone.record.Response;
import online.muydinov.quizletclone.service.AccessRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
public class AccessRequestController {

    private final AccessRequestService accessRequestService;

    public AccessRequestController(AccessRequestService accessRequestService) {
        this.accessRequestService = accessRequestService;
    }

    @PostMapping("/{cardSetId}/request-access")
    public ResponseEntity<Response> requestAccess(@PathVariable Long cardSetId) {
        return ResponseEntity.ok(accessRequestService.requestAccess(cardSetId));
    }

    @GetMapping("/{cardSetId}/requests")
    public ResponseEntity<List<AccessRequestRecord>> getPendingRequests(
            @PathVariable Long cardSetId) {
        List<AccessRequestRecord> pendingRequests = accessRequestService.getPendingRequests(cardSetId);
        return ResponseEntity.ok(pendingRequests);
    }

    @PutMapping("/{cardSetId}/requests/{requestId}")
    public ResponseEntity<Response> respondToRequest(
            @PathVariable Long cardSetId,
            @PathVariable Long requestId,
            @RequestParam boolean approve) {
        Response response = accessRequestService.respondToRequest(cardSetId, requestId, approve);
        return ResponseEntity.ok(response);
    }

}
