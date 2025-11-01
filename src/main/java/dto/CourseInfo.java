package dto;
import org.openqa.selenium.WebElement;
import java.time.LocalDate;


public class CourseDetails {
      public final String name;
      public final LocalDate startDate;
      public final WebElement element;

      public CourseInfo(String name, LocalDate startDate, WebElement element) {
         this.name = name;
         this.startDate = startDate;
         this.element = element;
      }
   }

