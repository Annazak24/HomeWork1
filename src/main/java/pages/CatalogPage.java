package pages;

import annotations.Path;
import dto.CourseInfo;
import java.util.NoSuchElementException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import waiters.Waiter;

@Path("/catalog/courses")
public class CatalogPage extends AbsBasePage {

   public CatalogPage(WebDriver driver) {
      super(driver);
   }

    @FindBy(xpath = "//div[contains(@class,'sc-hrqzy3-1') and contains(@class,'jEGzDf') and not(contains(text(),'месяц'))]")
    private List<WebElement> courseTitles;

    @FindBy(xpath = "//div[contains(@class,'sc-hrqzy3-1') and contains(@class,'jEGzDf') and contains(text(),'месяц')]")
    private List<WebElement> courseDateBlocks;

   @FindBy(xpath = "//button[contains(.,'Показать еще')]")
   private List<WebElement>  showMoreButton;

   private static final DateTimeFormatter RUS_DATE_FORMATTER =
       DateTimeFormatter.ofPattern("d MMMM, yyyy", new Locale("ru"));

   public void clickCourseByName(String courseName) {
      WebElement course = courseTitles.stream()
          .filter(e -> e.getText().trim().equalsIgnoreCase(courseName))
          .findFirst()
          .orElseThrow(() -> new NoSuchElementException("Курс не найден: " + courseName));

      scrollAndHighlight(course);
      waitUntilClickable(course).click();
   }

   public String getCourseTitle() {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      WebElement title = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));
      return title.getText().trim();
   }

   private LocalDate parseDate(String text) {
      try {
         if (text == null || text.isBlank()) return null;
         String clean = text.replace("·", "")
             .replaceAll("месяц(ев|а)?", "")
             .trim();

         Matcher matcher = Pattern.compile("\\d{1,2}\\s+\\p{IsCyrillic}+[,]\\s+\\d{4}").matcher(clean);
         if (matcher.find()) {
            return LocalDate.parse(matcher.group(), RUS_DATE_FORMATTER);
         }
      } catch (Exception e) {
         System.out.println("Unable to pars " + text);
      }
      return null;
   }

   /** 📦 Վերադարձնում է բոլոր դասընթացները՝ անուն + ամսաթիվ */
   public List<CourseInfo> getAllCourses() {
       List<CourseInfo> courses = new ArrayList<>();

       for (int i = 0; i < courseDateBlocks.size(); i++) {
           String dateText = courseDateBlocks.get(i).getText().trim();

           if (dateText.isEmpty() || !dateText.matches(".*\\d{4}.*|.*месяц.*")) {
               continue;
           }

           LocalDate parsed = parseDate(dateText);
           if (parsed == null) continue; // չհաջողվեց parse անել → skip

           if (i < courseTitles.size()) {
               String name = courseTitles.get(i).getText().trim();
               courses.add(new CourseInfo(name, parsed, courseTitles.get(i)));
           }
       }

       // 🔹 Ցուցադրում ենք՝ քանի իրական (ամսաթվով) կուրս գտնվեց
       System.out.println("📘 Найдено курсов с датой: " + courses.size());
       return courses;
   }

   /** 🕓 Ամենավաղ դասընթաց */
   public String getEarliestCourse(List<CourseInfo> courses) {
      CourseInfo courseInfo =  courses.stream()
          .filter(c -> c.getDate() != null)
          .reduce((a, b) -> a.getDate().isBefore(b.getDate()) ? a : b)
          .orElseThrow(() -> new NoSuchElementException("Нет курсов с датами"));
      return courseInfo.getName();
   }

   /** 🕒 Ամենաուշ դասընթաց */
   public String getLatestCourse(List<CourseInfo> courses) {
      CourseInfo courseInfo= courses.stream()
          .filter(c -> c.getDate() != null)
          .reduce((a, b) -> a.getDate().isAfter(b.getDate()) ? a : b)
          .orElseThrow(() -> new NoSuchElementException("Нет курсов с датами"));
      return courseInfo.getName();
   }


   // ---------------- Օգտակար մեթոդներ ----------------

   private WebElement waitUntilClickable(WebElement element) {
      return new WebDriverWait(driver, Duration.ofSeconds(5))
          .until(ExpectedConditions.elementToBeClickable(element));
   }

   private void scrollAndHighlight(WebElement element) {
      ((JavascriptExecutor) driver).executeScript(
          "arguments[0].scrollIntoView({block:'center'}); " +
              "arguments[0].style.border='3px solid red'; " +
              "arguments[0].style.transition='0.3s';", element);
   }

//   public void openCourse(CourseInfo course) {
//      WebElement element = course.getElement();
//
//      // scroll to view
//      ((JavascriptExecutor) driver).executeScript(
//          "arguments[0].scrollIntoView({block:'center'});", element);
//
//      // highlight visually
//      ((JavascriptExecutor) driver).executeScript(
//          "arguments[0].style.border='3px solid red'; arguments[0].style.transition='0.3s';", element);
//
//      // wait until clickable
//      new WebDriverWait(driver, Duration.ofSeconds(5))
//          .until(ExpectedConditions.elementToBeClickable(element));
//
//      try {
//         element.click();
//      } catch (ElementClickInterceptedException e) {
//         // fallback → JS click if normal click fails
//         ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
//      }
//   }

   public void loadAllCourses() {
      Waiter waiter = new Waiter(driver);
      JavascriptExecutor js = (JavascriptExecutor) driver;

      int previousCount = 0;
      while (true) {
         try {
            if (showMoreButton.isEmpty()) break;
            WebElement button = showMoreButton.get(showMoreButton.size() - 1);

            // 1️⃣ scroll դեպի կոճակը
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", button);
            waiter.waitForCondition(ExpectedConditions.elementToBeClickable(button));

            // 2️⃣ հիշում ենք ներկայիս դասընթացների քանակը
            int currentCount = driver.findElements(By.xpath("//div[contains(@class,'sc-hrqzy3-1') and contains(@class,'jEGzDf')]")).size();

            // 3️⃣ փորձում ենք click անել՝ երեք եղանակով
            try {
               button.click();
            } catch (Exception e1) {
               try {
                  // եթե սովորական click-ը չի ստացվել
                  js.executeScript("arguments[0].click();", button);
               } catch (Exception e2) {
                  System.out.println("⚠️ Կոճակը չկարողացանք սեղմել, հավանաբար այլևս չկա։");
                  break;
               }
            }

            // 4️⃣ սպասում ենք, որ նոր կուրսեր հայտնվեն
            boolean newCoursesLoaded = waiter.waitForCondition(d ->
                d.findElements(By.xpath("//div[contains(@class,'sc-hrqzy3-1') and contains(@class,'jEGzDf')]")).size() > currentCount
            );

            if (!newCoursesLoaded) {
               System.out.println("✅ Բոլոր դասընթացները բեռնվեցին (" + currentCount + ").");
               break;
            }

            previousCount = currentCount;
         } catch (StaleElementReferenceException e) {
            System.out.println("♻️ DOM թարմացվեց, փորձում ենք նորից...");
         } catch (Exception e) {
            System.out.println("⚠️ Վերջ — այլևս դասընթացներ չկան։");
            break;
         }
      }
   }
}
