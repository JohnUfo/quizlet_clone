package online.muydinov.quizletclone.record;

public record CardSetRecord(Long id,
                         String name,
                         Boolean isPublic,
                         String firstLanguage,
                         String secondLanguage,
                         Long creatorId,
                         String creatorName,
                         String accessType) {

}