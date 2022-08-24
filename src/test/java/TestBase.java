import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://www.mvideo.ru/";
        Configuration.browserSize = "1366x768";
        Configuration.holdBrowserOpen = true;
    }
}
