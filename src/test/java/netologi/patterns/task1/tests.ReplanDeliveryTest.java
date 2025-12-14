package netologi.patterns.task1.tests;

import netologi.patterns.task1.data.DataGenerator;
import netologi.patterns.task1.pages.DeliveryPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReplanDeliveryTest {

    private DeliveryPage deliveryPage;

    @BeforeEach
    void setUp() {
        // Запуск приложения (если не запущено)
        // В реальности это может быть через ProcessBuilder или Docker
        // Для простоты предположим, что приложение запущено на localhost:9999
        deliveryPage = DeliveryPage.open();
    }

    @Test
    void shouldReplanDeliveryWhenSameUserOrdersAgainWithDifferentDate() {
        // Генерируем данные пользователя
        DataGenerator.UserInfo userInfo = DataGenerator.generateUserInfo();

        // Генерируем первую дату (например, через 3 дня)
        String firstDate = DataGenerator.generateDate(3);
        // Генерируем вторую дату (например, через 5 дней)
        String secondDate = DataGenerator.generateDate(5);

        // 1. Заполняем форму первым набором данных и отправляем
        deliveryPage.fillForm(userInfo.city, firstDate, userInfo.name, userInfo.phone);
        deliveryPage.submitOrder();

        // 2. Ждем успешного уведомления (можно добавить ожидание, если нужно)
        // В реальности здесь может быть ожидание, но для простоты пропустим

        // 3. Заполняем форму теми же данными, но с другой датой
        deliveryPage.fillForm(userInfo.city, secondDate, userInfo.name, userInfo.phone);
        deliveryPage.submitOrder();

        // 4. Проверяем, что появилось окно подтверждения перепланирования
        assertTrue(deliveryPage.isReplanConfirmationVisible(), "Окно подтверждения перепланирования не появилось!");

        // 5. Нажимаем кнопку "Перепланировать"
        deliveryPage.replanMeeting();

        // 6. Проверяем, что появилось уведомление об успешном перепланировании
        String successText = deliveryPage.getSuccessNotificationText();
        assertTrue(successText.contains(secondDate), "Дата в уведомлении не соответствует ожидаемой!");
    }
}