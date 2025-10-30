import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MtsPaymentBlockTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private PaymentBlockPage paymentPage;

    private static final String BASE_URL = "https://www.mts.by/";
    private static final String TEST_PHONE = "297777777";
    private static final String TEST_SUM = "1.00";


    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // wait остается для тестов (напр. смена окна)

        paymentPage = new PaymentBlockPage(driver);
        paymentPage.openPage(BASE_URL);

        String mainWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        if (allWindows.size() > 1) {
            for (String handle : allWindows) {
                if (!handle.equals(mainWindow)) {
                    driver.switchTo().window(handle).close();
                }
            }
            driver.switchTo().window(mainWindow);
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    // --- ТЕСТЫ ---

    @Test
    @Order(1)
    public void test1_CheckTitle() {
        System.out.println("Тест 1: Проверка заголовка блока...");
        assertTrue(paymentPage.isTitleDisplayed(), "Провал: Блок с заголовком не найден!");
        System.out.println("✅ Успех: Заголовок найден и отображается корректно.");
    }

    @Test
    @Order(2)
    public void test2_CheckPaymentLogos() {
        System.out.println("Тест 2: Проверка логотипов платёжных систем...");
        assertTrue(paymentPage.arePaymentLogosDisplayed(), "Провал: Не все логотипы найдены!");
        System.out.println("✅ Успех: Все логотипы платёжных систем отображаются.");
    }

    @Test
    @Order(3)
    public void test3_CheckDetailsLink() {
        System.out.println("Тест 3: Проверка ссылки 'Подробнее о сервисе'...");

        String originalWindow = driver.getWindowHandle();
        paymentPage.clickDetailsLink(); // Действие из PO

        try {
            wait.withTimeout(Duration.ofSeconds(10)).until(ExpectedConditions.numberOfWindowsToBe(2));

            Set<String> allWindows = driver.getWindowHandles();
            String newWindow = allWindows.stream()
                    .filter(handle -> !handle.equals(originalWindow))
                    .findFirst()
                    .orElseThrow(() -> new TimeoutException("Новая вкладка не найдена."));

            driver.switchTo().window(newWindow);
            wait.until(ExpectedConditions.urlContains("poryadok-oplaty-i-bezopasnost"));
            System.out.println("✅ Успех: Ссылка 'Подробнее о сервисе' работает.");
            driver.close();
            driver.switchTo().window(originalWindow);
        } catch (TimeoutException e) {// Если вкладка не открылась, проверяем текущий URL (альтернативное поведение)
            if (driver.getWindowHandles().size() == 1) {
                wait.until(ExpectedConditions.urlContains("poryadok-oplaty-i-bezopasnost"));
                System.out.println("✅ Успех: Ссылка сработала в текущем окне.");
                driver.get(BASE_URL); // Возвращаемся назад для следующих тестов
            } else {
                fail("Провал: Новая вкладка не открылась или URL неверный.");
            }
        }
    }

    @Test
    @Order(4)
    public void test4_1_CheckIframeAppeared() {
        System.out.println("--- Проверка 4.1: Появление IFRAME ---");
        paymentPage.submitPhoneServiceForm(TEST_PHONE, TEST_SUM);
        assertTrue(paymentPage.isPaymentIframeDisplayed(), "Провал: IFRAME оплаты 'bepaid' не обнаружен.");
        System.out.println("✅ Успех: IFRAME оплаты 'bepaid' обнаружен.");
        driver.switchTo().defaultContent(); // Сброс на всякий случай
    }

    @Test
    @Order(5)
    public void test4_2_VerifyPhoneInIframe() {
        System.out.println("--- Проверка 4.2: Корректность номера телефона в IFRAME ---");
        paymentPage.submitPhoneServiceForm(TEST_PHONE, TEST_SUM);
        BePaidIframePage iframePage = paymentPage.switchToPaymentIframe();

        try {
            assertTrue(iframePage.getDescriptionText().contains(TEST_PHONE),
                    "Провал: В описании не найден номер телефона.");
            System.out.println("✅ Успех: Номер телефона отображается.");
        } catch (Exception e) {
            fail("Провал: Ошибка при проверке телефона в IFRAME. " + e.getMessage().split("\n")[0]);
        } finally {
            iframePage.switchToDefaultContent();
        }
    }

    @Test
    @Order(6)
    public void test4_3_VerifySumInIframe() {
        System.out.println("--- Проверка 4.3: Корректность суммы в IFRAME ---");
        paymentPage.submitPhoneServiceForm(TEST_PHONE, TEST_SUM);
        BePaidIframePage iframePage = paymentPage.switchToPaymentIframe();

        try {
            assertTrue(iframePage.getAmountText().contains(TEST_SUM),
                    "Провал: Сумма отображается некорректно.");
            System.out.println("✅ Успех: Сумма отображается.");
        } catch (Exception e) {
            fail("Провал: Ошибка при проверке суммы в IFRAME. " + e.getMessage().split("\n")[0]);
        } finally {
            iframePage.switchToDefaultContent();
        }
    }

    @Test
    @Order(7)
    public void test4_4_VerifyLogosInIframe() {
        System.out.println("--- Проверка 4.4: Наличие логотипов платежных систем в IFRAME ---");
        paymentPage.submitPhoneServiceForm(TEST_PHONE, TEST_SUM);
        BePaidIframePage iframePage = paymentPage.switchToPaymentIframe();

        try {
            assertTrue(iframePage.areLogosDisplayed(), "Провал: Контейнер с логотипами не отображается.");
            System.out.println("✅ Успех: Логотипы платежных систем присутствуют.");
        } catch (Exception e) {
            fail("Провал: Ошибка при проверке логотипов в IFRAME. " + e.getMessage().split("\n")[0]);
        } finally {
            iframePage.switchToDefaultContent();
        }
    }

    @Test
    @Order(8)
    public void test5_CheckInternetPaymentFields() {
        System.out.println("--- Проверка 5: Появление полей 'Домашний интернет' ---");
        paymentPage.selectService("Домашний интернет");

        assertTrue(paymentPage.isInternetAccountInputDisplayed(), "Провал: Поле для Домашнего интернета не появилось.");
        assertFalse(paymentPage.isPhoneInputDisplayed(), "Провал: Поле 'Телефон' не исчезло.");
        System.out.println("✅ Успех: Поля для 'Домашний интернет' отображаются корректно.");
    }

    @Test
    @Order(9)
    public void test6_CheckInstallmentPaymentFields() {
        System.out.println("--- Проверка 6: Появление полей 'Рассрочка' ---");
        paymentPage.selectService("Рассрочка");

        assertTrue(paymentPage.isInstallmentInputDisplayed(), "Провал: Поле для Рассрочки не появилось.");
        assertFalse(paymentPage.isPhoneInputDisplayed(), "Провал: Поле 'Телефон' не исчезло.");
        System.out.println("✅ Успех: Поля для 'Рассрочка' отображаются корректно.");
    }

    @Test
    @Order(10)
    public void test7_CheckArrearsPaymentFields() {
        System.out.println("--- Проверка 7: Появление полей 'Задолженность' ---");
        paymentPage.selectService("Задолженность");

        assertTrue(paymentPage.isArrearsInputDisplayed(), "Провал: Поле для Задолженности не появилось.");
        assertFalse(paymentPage.isPhoneInputDisplayed(), "Провал: Поле 'Телефон' не исчезло.");
        System.out.println("✅ Успех: Поля для 'Задолженность' отображаются корректно.");
    }
}
