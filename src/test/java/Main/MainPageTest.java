package Main;

import extensions.UiExtensions;
import com.google.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.MainPage;

@ExtendWith(UiExtensions.class)
public class MainPageTest {

    @Inject
    private MainPage mainPage;

    @Test
    public void checkClickArticleTile(){

    }
}
