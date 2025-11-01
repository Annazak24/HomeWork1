package listeners;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsDriver;
import org.openqa.selenium.support.events.WebDriverListener;

public class HighlightListener implements WebDriverListener {

   private void highlight(WebDriver driver, WebElement element) {
      if (element == null)
         return;
      try {
         JavascriptExecutor js = (JavascriptExecutor) driver;
         // 🌟 կարմիր շրջանակ + փայլուն էֆեկտ
         js.executeScript(
             "arguments[0].style.transition='all 0.2s ease';" +
                 "arguments[0].style.border='3px solid red';" +
                 "arguments[0].style.boxShadow='0 0 10px red';"+
                 "setTimeout(() => { arguments[0].style.border=''; arguments[0].style.boxShadow=''; }, 800);",
             element
         );


         // հետո վերականգնում ենք սկզբնական տեսքը
         js.executeScript(
             "arguments[0].style.border=''; arguments[0].style.boxShadow='';", element
         );

      } catch (Exception e) {
         System.out.println("⚠️ Highlight error: " + e.getMessage());
      }
   }

   @Override
   public void beforeClick(WebElement element) {
      WebDriver driver = ((WrapsDriver) element).getWrappedDriver();
      highlight(driver, element);
   }

   @Override
   public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
      WebDriver driver = ((WrapsDriver) element).getWrappedDriver();
      highlight(driver, element);
   }
}
