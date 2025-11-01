package dto;

import org.openqa.selenium.WebElement;
import java.time.LocalDate;

public class CourseInfo {
   private final String name;
   private final LocalDate date;

   public CourseInfo(String name, LocalDate date, WebElement element) {
      this.name = name;
      this.date = date;
   }

   public String getName() {
      return name;
   }

   public LocalDate getDate() {
      return date;
   }
}

