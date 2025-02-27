package online.muydinov.quizletclone.record;

public record AccessRequestRecord(
        Long id,
        Long cardSetId,
        String cardSetName,
        String requesterUsername,
        String status
) {
}
