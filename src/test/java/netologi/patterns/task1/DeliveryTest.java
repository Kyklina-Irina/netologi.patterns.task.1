package netologi.patterns.task1;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import netologi.patterns.task1.data.TestData;
import netologi.patterns.task1.utils.DataGenerator;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        // Включаем логирование Selenide с Allure
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void openBrowser() {
        // Запускаем целевой сервис (app-replan-delivery.jar) перед каждым тестом
        // Предполагается, что он запущен на порту 9999
        // Если вы запускаете его вручную, закомментируйте эту строку
        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver"); // если нужно
        open("http://localhost:9999");
    }

    @Test
    @Order(1)
    void shouldSuccessfullyPlanMeeting() {
        // Генерируем данные для первого заказа
        TestData firstData = new TestData(
                DataGenerator.generateCity(),
                DataGenerator.generateDate(3), // Дата через 3 дня
                DataGenerator.generateName(),
                DataGenerator.generatePhone()
        );

        // Заполняем форму
        $("[data-test-id=city] input").setValue(firstData.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE); // Очищаем поле
        $("[data-test-id=date] input").setValue(firstData.getDate());
        $("[data-test-id=name] input").setValue(firstData.getName());
        $("[data-test-id=phone] input").setValue(firstData.getPhone());
        $("[data-test-id=agreement]").click();

        // Нажимаем "Запланировать"
        $(byText("Запланировать")).click();

        // Проверяем, что появилось сообщение об успешном планировании
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на"))
                .shouldHave(Condition.text(firstData.getDate()));
    }

    @Test
    @Order(2)
    void shouldReplanMeetingWithNewDate() {
        // Генерируем те же данные, что и в первом тесте, но с новой датой
        // Для простоты, мы можем использовать те же данные, но изменить дату.
        // В реальности, вы можете сохранить firstData из предыдущего теста,
        // но поскольку тесты должны быть независимыми, мы сгенерируем новые данные,
        // а затем подменим дату.

        // Генерируем данные, как в первом тесте
        String city = DataGenerator.generateCity();
        String name = DataGenerator.generateName();
        String phone = DataGenerator.generatePhone();

        // Первая дата (для первого заказа)
        String firstDate = DataGenerator.generateDate(3);

        // Вторая дата (для перепланирования)
        String secondDate = DataGenerator.generateDate(5); // Дата через 5 дней

        // --- Первый заказ ---
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();

        // Ждем появления уведомления о успешном планировании
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на"))
                .shouldHave(Condition.text(firstDate));

        // --- Второй заказ (перепланирование) ---
        // Заполняем форму теми же данными, но с другой датой
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(secondDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();

        // Появляется модальное окно с подтверждением
        $(byText("Необходимо подтверждение")).shouldBe(Condition.visible);
        $(byText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(Condition.visible);

        // Нажимаем кнопку "Перепланировать"
        $(byText("Перепланировать")).click();

        // Проверяем, что появилось сообщение об успешном перепланировании
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на"))
                .shouldHave(Condition.text(secondDate));
    }
}
