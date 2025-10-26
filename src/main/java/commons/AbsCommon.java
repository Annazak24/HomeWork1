package commons;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public abstract class AbsCommon {

    protected WebDriver driver;

    protected Actions actions;


    public AbsCommon(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
    }

    public BiConsumer<List<WebElement>, Predicate<WebElement>> clickElementByPredicate =
            (List<WebElement> elements, Predicate<WebElement> elementPredicate) -> elements.stream().filter(elementPredicate)
                    .findFirst().get().click();
}
