package pages;

import models.Article;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class OpinionPage {
    private WebDriver driver;
    private final By articleLinks = By.cssSelector("section[data-dtm-region='Opinion'] article a");

    public OpinionPage(WebDriver driver) {
        this.driver = driver;
    }

    public List<Article> fetchTopArticles(int count) {
        driver.get("https://elpais.com/");
        driver.findElement(By.xpath("//a[contains(text(),'Opinión')]")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.presenceOfElementLocated(articleLinks));

        List<WebElement> links = driver.findElements(articleLinks);
        List<Article> articles = new ArrayList<>();

        for (int i = 0; i < Math.min(count, links.size()); i++) {
            String url = links.get(i).getAttribute("href");
            driver.navigate().to(url);
            String title = driver.findElement(By.tagName("h1")).getText();
            String content = driver.findElement(By.tagName("article")).getText();
            String imgSrc = "";
            try {
                WebElement img = driver.findElement(By.cssSelector("article img"));
                imgSrc = img.getAttribute("src");
            } catch (NoSuchElementException ignored) {}
            articles.add(new Article(title, content, imgSrc));
            driver.navigate().back();
        }
        return articles;
    }
}
