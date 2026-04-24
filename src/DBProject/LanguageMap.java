package DBProject;

import java.util.HashMap;
import java.util.Map;

public class LanguageMap {

    public static Map<String, String> getLanguages() {

        Map<String, String> languages = new HashMap<>();

        languages.put("English", "en");
        languages.put("Tamil", "ta");
        languages.put("Hindi", "hi");
        languages.put("Malayalam", "ml");
        languages.put("Telugu", "te");
        languages.put("Kannada", "kn");
        languages.put("French", "fr");
        languages.put("Spanish", "es");
        languages.put("German", "de");
        languages.put("Japanese", "ja");

        return languages;
    }
}