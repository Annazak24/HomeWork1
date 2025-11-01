package pages;

import annotations.Path;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Random;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Path("/")
public class MainPage extends AbsBasePage<MainPage> {

   public MainPage(WebDriver driver) {
      super(driver);
   }

   @Inject
   private CatalogPage catalogPage;

   @FindBy(xpath = "//a[text() = \"Все курсы\"]")
   private WebElement allCourses;

   @FindBy(xpath = "//span[text()=\"Обучение\"]")
   private WebElement training;

   @FindBy(xpath = "//div[contains(@class, 'lhsLfs')]//a")
   private List<WebElement> categories;

   public CatalogPage clickAllCourses() {
      allCourses.click();
      return catalogPage;
   }

   public MainPage hoverTraining() {
      actions.moveToElement(training).perform();
      return this;
   }

   public MainPage clickRandomCategory() {
      if (categories.isEmpty()) {
         throw new IllegalStateException("No categories found");
      }

      int randomIndex = new Random().nextInt(categories.size());
      categories.get(randomIndex).click();

      return this;
   }

//
//   public MainPage(WebDriver driver) {
//      super(driver);
//   }
//
//   @Inject
//   private ArticlePage articlePage;
//
//   @FindBy(css = "a.photo_title")
//   private List<WebElement> articles;
//
//   public String getPhotoTitleByIndex(int index) {
//      return articles.get(index - 1).getText();
//   }
//
//   public ArticlePage clickArticleByTitle(String title) {
//
//      this.clickElementByPredicate.accept(articles,
//          (WebElement element) -> element.getText().equals(title));
//
//      return articlePage;
//   }
}
