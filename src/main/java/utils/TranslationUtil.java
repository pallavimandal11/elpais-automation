package utils;

import java.util.*;

public class TranslationUtil {
    public static Map<String, Integer> getRepeatedWords(List<String> titles) {
        Map<String, Integer> wordCount = new HashMap<>();

        for (String title : titles) {
            String[] words = title.toLowerCase().split("\\W+");
            for (String word : words) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        Map<String, Integer> repeated = new HashMap<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 2) repeated.put(entry.getKey(), entry.getValue());
        }
        return repeated;
    }
}
