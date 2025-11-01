package Main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Inject;
import extensions.UiExtensions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.CatalogPage;

@ExtendWith(UiExtensions.class)
public class Scenario1 {

   @Inject
   private CatalogPage catalogPage;

   @Inject
   private WebDriver driver;

   @Test
   public void findCourseByNameTest() {
      String courseName = "Vue.js разработчик"; // կամ Vue.js разработчик

      catalogPage.open();
      catalogPage.clickCourseByName(courseName);

      String title = catalogPage.getCourseTitle();

      assertEquals(courseName, title,
          String.format("Սպասվում էր '%s', բայց ստացվել է '%s'", courseName, title));
   }
}
