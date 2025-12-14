package netologi.patterns.task1.pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DeliveryPage {

    // Элементы формы
    @FindBy(css = "[data-test-id=city] input")
    private SelenideElement cityInput;

    @FindBy(css = "[data-test-id=date] input")
    private SelenideElement dateInput;

    @FindBy(css = "[data-test-id=name] input")
    private SelenideElement nameInput;

    @FindBy(css = "[data-test-id=phone] input")
    private SelenideElement phoneInput;

    @FindBy(css = "[data-test-id=agreement]")
    private SelenideElement agreementCheckbox;

    @FindBy(css = "[data-test-id=order-button]")
    private SelenideElement orderButton;

    // Элементы модального окна
    @FindBy(css = "[data-test-id=replan] button")
    private SelenideElement replanButton;

    @FindBy(css = "[data-test-id=success-notification] .notification__content")
    private SelenideElement successNotification;

    // Метод для заполнения формы
    public void fillForm(String city, String date, String name, String phone) {
        cityInput.setValue(city);
        dateInput.doubleClick().clear(); // Очищаем поле даты
        dateInput.setValue(date).pressEnter(); // Вводим дату и подтверждаем
        nameInput.setValue(name);
        phoneInput.setValue(phone);
        agreementCheckbox.click();
    }

    // Метод для отправки формы
    public void submitOrder() {
        orderButton.click();
    }

    // Метод для перепланирования встречи
    public void replanMeeting() {
        replanButton.click();
    }

    // Метод для получения текста успешного уведомления
    public String getSuccessNotificationText() {
        successNotification.shouldBe(Condition.visible).shouldHave(Condition.text("Успешно!"));
        return successNotification.getText();
    }

    // Метод для проверки, что появилось окно подтверждения перепланирования
    public boolean isReplanConfirmationVisible() {
        return $("[data-test-id=replan]").shouldBe(Condition.visible).exists();
    }

    // Открытие страницы
    public static DeliveryPage open() {
        Selenide.open("http://localhost:9999");
        return new DeliveryPage();
    }
}