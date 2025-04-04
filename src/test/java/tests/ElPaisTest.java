package tests;

import api.TranslateAPI;
import base.BaseTest;
import models.Article;
import org.testng.annotations.Test;
import pages.OpinionPage;
import utils.TranslationUtil;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

public class ElPaisTest extends BaseTest {

    @Test
    public void elPaisScrapeTranslateAnalyze() throws Exception {
        OpinionPage page = new OpinionPage(driver);
        List<Article> articles = page.fetchTopArticles(5);

        List<String> translatedTitles = new ArrayList<>();

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get(i);
            System.out.println("\nArticle " + (i + 1));
            System.out.println("Título: " + article.title);
            System.out.println("Contenido:\n" + article.content.substring(0, Math.min(300, article.content.length())) + "...\n");

            if (!article.imageUrl.isEmpty()) {
                downloadImage(article.imageUrl, "article_" + i + ".jpg");
            }

            String translated = TranslateAPI.translate(article.title);
            translatedTitles.add(translated);
            System.out.println("Translated Title: " + translated);
        }

        Map<String, Integer> repeated = TranslationUtil.getRepeatedWords(translatedTitles);
        System.out.println("\nRepeated Words:");
        repeated.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    private void downloadImage(String imageUrl, String fileName) {
        try (InputStream in = new URL(imageUrl).openStream()) {
            Files.copy(in, Paths.get("images/" + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error downloading image: " + e.getMessage());
        }
    }
}
