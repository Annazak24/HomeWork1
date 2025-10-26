package pages;

import annotations.Path;
import annotations.UrlTamplate;
import commons.AbsCommon;
import exceptions.PathNotFoundException;
import org.openqa.selenium.WebDriver;

public abstract class AbsBasePage<T> extends AbsCommon {

    private String baseUrl = System.getProperty("base.url");

    public AbsBasePage(WebDriver driver) {
        super(driver);
    }

    private String getPathWithData(String[] data) {
        Class<T> clazz = (Class<T>) this.getClass();
        if (clazz.isAnnotationPresent(UrlTamplate.class)) {
            UrlTamplate urlTamplate = clazz.getDeclaredAnnotation(UrlTamplate.class);
            String template = urlTamplate.value();

            for (int i = 0; i < data.length; i++) {
                template.replace(String.format("{%d}", i + 1), data[i]);
            }
            return template;
        }
        return "";
    }

    private String getPath() {
        Class<T> clazz = (Class<T>) this.getClass();
        if (clazz.isAnnotationPresent(Path.class)) {
            Path pathObj = clazz.getDeclaredAnnotation(Path.class);
            return pathObj.value();
        }

        throw new PathNotFoundException();
    }

    public T open() {
        driver.get(baseUrl + getPath());
        return (T)this;

    }
     public T open(String... data){
        driver.get(baseUrl+ getPathWithData(data));
        return (T)this;
     }
}
