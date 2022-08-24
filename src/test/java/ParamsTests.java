
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static java.util.Arrays.asList;

public class ParamsTests extends TestBase {

    @DisplayName("Поиск соковыжималки в магазине мвидео")
    @Test
    void pageSearch(){
        Selenide.open("https://www.mvideo.ru/");
        $(".input__field").setValue("Соковыжималка шнековая Kitfort КТ-1102-1");
        $(".search-icon-wrap.ng-star-inserted").click();
        $(".product-cards-row").find(withText("Соковыжималка шнековая Kitfort КТ-1102-1")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @ValueSource(strings = {"Соковыжималка шнековая Kitfort КТ-1102-1","Соковыжималка шнековая Kitfort КТ-1106-1 красный","Соковыжималка шнековая Kitfort КТ-1105-1 красный"})
    @ParameterizedTest(name = "При поиске на сайте мвидео{0} в результатах получаем {0}")
    void mvideoListSearchTest(String testData) {
        Selenide.open("https://www.mvideo.ru/");
        $(".input__field").setValue(testData);
        $(".search-icon-wrap.ng-star-inserted").click();
        $(".product-cards-row").find(withText(testData)).shouldBe(visible, Duration.ofSeconds(15));

    }

    @EnumSource(list.class)
    @ParameterizedTest
    void enumTestWithList(list list) {
       Selenide.open("https://www.mvideo.ru/");
       $(".input__field").setValue(list.desc);
       $(".search-icon-wrap.ng-star-inserted").click();
        $(".product-cards-row").find(withText(list.desc)).shouldBe(visible, Duration.ofSeconds(15));

    }

    static Stream<Arguments> listData() {
        return Stream.of(
                Arguments.of("Соковыжималка шнековая Kitfort КТ-1102-1", asList("Соковыжималка шнековая Kitfort КТ-1102-1")),
                Arguments.of("Соковыжималка шнековая Kitfort КТ-1106-1 красный", asList("Соковыжималка шнековая Kitfort КТ-1106-1 красный"))
        );
    }

    @MethodSource(value = "listData")
    @ParameterizedTest(name = "При поиске соковыжималки на маркете {0} в результатах получаем {0}")
    void mvideoListSearchAdditionSearch(String testData, List<String> expectedResult) {
        Selenide.open("https://www.mvideo.ru/");
        $(".input__field").setValue(testData);
        $(".search-icon-wrap.ng-star-inserted").click();
        $(".product-cards-row").find(withText(testData)).shouldBe(visible, Duration.ofSeconds(15));
    }

    @CsvSource(value = {
            "Соковыжималка шнековая Kitfort КТ-1106-1, Осталась одна штука!",
            "Соковыжималка шнековая Kitfort КТ-1105-1, Бонусных рублей"
    }
    )

    @ParameterizedTest(name = "Найти соковыжималки {0}, {1}")
    void searchWithCsv(String searchItem, String expectedResult) {
        Selenide.open("https://www.mvideo.ru/");
        $(".input__field").setValue(searchItem);
        $(".search-icon-wrap.ng-star-inserted").click();
        $(".product-cards-row").find(withText(expectedResult)).shouldBe(visible, Duration.ofSeconds(15));
    }

    @CsvFileSource(resources = "list.csv")
    @ParameterizedTest(name = "Найти соковыжималки {0}, {1}")
    void searchCSVFile(String searchItem, String expectedResult) {
        Selenide.open("https://www.mvideo.ru/");
        $(".input__field").setValue(searchItem);
        $(".search-icon-wrap.ng-star-inserted").click();
        $(".product-cards-row").find(withText(expectedResult)).shouldBe(visible, Duration.ofSeconds(15));
    }
}
