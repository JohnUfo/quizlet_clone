package online.muydinov.quizletclone.enums;

import lombok.Getter;

@Getter
public enum Language {
    ENGLISH("English"),
    SPANISH("Spanish"),
    FRENCH("French"),
    GERMAN("German"),
    RUSSIAN("Russian"),
    CHINESE("Chinese"),
    ARABIC("Arabic"),
    ITALIAN("Italian"),
    PORTUGUESE("Portuguese"),
    JAPANESE("Japanese"),
    KOREAN("Korean"),
    HINDI("Hindi"),
    TURKISH("Turkish"),
    DUTCH("Dutch"),
    SWEDISH("Swedish"),
    POLISH("Polish"),
    GREEK("Greek"),
    ROMANIAN("Romanian"),
    INDONESIAN("Indonesian"),
    VIETNAMESE("Vietnamese"),
    THAI("Thai"),
    BENGALI("Bengali"),
    PERSIAN("Persian"),
    URDU("Urdu"),
    HEBREW("Hebrew"),
    UZBEK("Uzbek");

    private final String name;

    Language(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
