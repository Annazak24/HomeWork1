package components;

import annotations.Component;
import org.openqa.selenium.WebDriver;

@Component("css:")
public class DayNewsComponent extends AbsComponent {

   public DayNewsComponent(WebDriver driver) {
      super(driver);
   }
}
