package online.muydinov.quizletclone.controller;

import lombok.RequiredArgsConstructor;
import online.muydinov.quizletclone.dto.AccessRequestDTO;
import online.muydinov.quizletclone.dto.Response;
import online.muydinov.quizletclone.service.AccessRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class AccessRequestController {

    private final AccessRequestService accessRequestService;

    @PostMapping("/{cardSetId}/request-access")
    public ResponseEntity<Response> requestAccess(@PathVariable Long cardSetId) {
        return ResponseEntity.ok(accessRequestService.requestAccess(cardSetId));
    }

    @GetMapping("/{cardSetId}/requests")
    public ResponseEntity<List<AccessRequestDTO>> getPendingRequests(
            @PathVariable Long cardSetId) {
        List<AccessRequestDTO> pendingRequests = accessRequestService.getPendingRequests(cardSetId);
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
