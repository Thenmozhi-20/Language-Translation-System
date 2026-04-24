package DBProject;

public class TranslationHistoryModel {
    private Integer userId;
    private String email;
    private String sourceLang;
    private String destLang;
    private String sourceText;
    private String translatedText;
    private String translationDate;

    public TranslationHistoryModel(Integer userId, String email, String sourceLang, String destLang,
                                   String sourceText, String translatedText, String translationDate) {
        this.userId = userId;
        this.email = email;
        this.sourceLang = sourceLang;
        this.destLang = destLang;
        this.sourceText = sourceText;
        this.translatedText = translatedText;
        this.translationDate = translationDate;
    }

    // Getters
    public Integer getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getSourceLang() { return sourceLang; }
    public String getDestLang() { return destLang; }
    public String getSourceText() { return sourceText; }
    public String getTranslatedText() { return translatedText; }
    public String getTranslationDate() { return translationDate; }
}
