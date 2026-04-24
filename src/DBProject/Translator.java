package DBProject;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class Translator {
    public static String decodeUnicode(String unicodeStr) {
        Properties prop = new Properties();
        try {
             prop.load(new StringReader("key=" + unicodeStr));
            return prop.getProperty("key");
        } catch (IOException e) {
            e.printStackTrace();
            return unicodeStr;  // fallback to original string if error
        }
    }

    public static String translateText(String text, String sourceLang, String targetLang) {
        try {
            // Encode text and language pair for URL safely
            String encodedText = URLEncoder.encode(text, java.nio.charset.StandardCharsets.UTF_8);
            String langPair = URLEncoder.encode(sourceLang + "|" + targetLang, java.nio.charset.StandardCharsets.UTF_8);

            String url = "https://api.mymemory.translated.net/get?q=" + encodedText + "&langpair=" + langPair;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String jsonResponse = response.body();

            // Extract translatedText field from JSON response (simple substring parsing)
            int startIndex = jsonResponse.indexOf("\"translatedText\":\"") + 18;
            int endIndex = jsonResponse.indexOf("\"", startIndex);
            String translatedText = jsonResponse.substring(startIndex, endIndex);

            // Decode any Unicode escape sequences in the translated text
            String decodedText = decodeUnicode(translatedText);

            return decodedText;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Translation Failed";
        }
    }
}

