package pages;

import annotations.UrlTamplate;
import annotations.Urls;
import com.google.inject.Provides;
import org.openqa.selenium.WebDriver;
@UrlTamplate(value = "article")
public class ArticlePage extends AbsBasePage {

    public ArticlePage(WebDriver driver) {
        super(driver);
    }
}
