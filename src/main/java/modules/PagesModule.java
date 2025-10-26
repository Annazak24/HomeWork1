package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

public class PagesModule extends AbstractModule {

    private WebDriver driver;

    public PagesModule(WebDriver driver){
        this.driver = driver;
    }


    @Provides
    @Singleton
    public MainPage getMainPage() {
        return new MainPage(driver);
    }
}
