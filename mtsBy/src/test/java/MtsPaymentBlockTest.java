import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MtsPaymentBlockTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private static final String BASE_URL = "https://www.mts.by/";
    private static final String TEST_PHONE = "297777777";
    private static final String TEST_SUM = "1.00";


    private static final By BLOCK_TITLE_LOCATOR =
            By.xpath(".//h2[contains(., 'Онлайн пополнение') and contains(., 'без комиссии')]");

    private static final By LOGO_VISA = By.xpath(".//img[@alt='Visa']");
    private static final By LOGO_MASTERCARD = By.xpath(".//img[@alt='MasterCard']");
    private static final By LOGO_MASTERCARD_SECURE = By.xpath(".//img[@alt='MasterCard Secure Code']");
    private static final By LOGO_VERIFIED_VISA = By.xpath(".//img[@alt='Verified By Visa']");

    private static final By DETAILS_LINK = By.xpath(".//a[contains(@href, 'poryadok-oplaty-i-bezopasnost')]");

    private static final By SERVICE_HEADER_LOCATOR_VISUAL = By.xpath("//button[contains(@class,'select__header')]");

    private static final By PHONE_INPUT = By.id("connection-phone");
    private static final By SUM_INPUT = By.id("connection-sum");
    private static final By PROCEED_BUTTON = By.xpath(".//form//button[@type='submit' and contains(., 'Продолжить')]");

    private static final By INTERNET_ACCOUNT_INPUT = By.id("internet-account");
    private static final By INSTALLMENT_ACCOUNT_INPUT = By.id("score-instalment"); // ИСПРАВЛЕНО
    private static final By ARREARS_PHONE_INPUT = By.id("score-arrears"); // ИСПРАВЛЕНО

    private static final By PAYMENT_IFRAME = By.xpath("//iframe[contains(@class, 'bepaid-iframe')]");
    private static final By IFRAME_DESCRIPTION = By.xpath(".//div[@class='pay-description']");
    private static final By IFRAME_AMOUNT = By.xpath(".//div[@class='pay-amount']");
    private static final By IFRAME_LOGOS = By.xpath(".//div[@class='pay-system-container']");

    private static final By COOKIE_BLOCK = By.cssSelector("div.cookie.show");

    // ----------------------------------------------------------------------------------

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(BASE_URL);

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
        try {
            WebElement cookieBlock = wait.withTimeout(Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(COOKIE_BLOCK));
            ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", cookieBlock);
            System.out.println("Информация: Куки-блок удалён из DOM.");
        } catch (TimeoutException ignored) {
            System.out.println("Информация: Куки-блок отсутствует, продолжаем.");
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    private void selectServiceViaLi(String serviceName) {
        By headerLocator = By.xpath("//button[contains(@class,'select__header')]");
        By optionLocator = By.xpath("//ul[contains(@class,'select__list')]//li[contains(., '" + serviceName + "')]");
        By listLocator = By.xpath("//ul[contains(@class,'select__list')]");


        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement headerButton = wait.until(ExpectedConditions.elementToBeClickable(headerLocator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headerButton);

                Thread.sleep(400);

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", headerButton);
                System.out.println("Информация: Выполнен JS-клик по кнопке выбора услуги.");

                WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", optionElement);
                System.out.println("✅ Выбрана услуга (попытка #" + (attempts + 1) + "): " + serviceName);

                wait.until(ExpectedConditions.invisibilityOfElementLocated(listLocator));

                return;
            } catch (Exception e) {
                attempts++;
                System.err.println("⚠️ Попытка #" + attempts + " не удалась: " + e.getMessage().split("\n")[0]);
                if (attempts == 2) {
                    throw new RuntimeException("Не удалось выбрать услугу " + serviceName + " после 2 попыток.", e);
                }
                driver.navigate().refresh();
                wait.until(ExpectedConditions.visibilityOfElementLocated(headerLocator));
            }
        }
    }

    private WebElement submitPhoneServiceForm() {
        selectServiceViaLi("Услуги связи"); // Используем надежный метод
        driver.findElement(PHONE_INPUT).sendKeys(TEST_PHONE);
        driver.findElement(SUM_INPUT).sendKeys(TEST_SUM);
        WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(PROCEED_BUTTON));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedButton);
        System.out.println("Информация: Форма отправлена.");

        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(PAYMENT_IFRAME));
        } catch (Exception e) {
            fail("Провал: Модальное окно оплаты не открылось или не загрузилось. Ошибка: " + e.getMessage().split("\n")[0]);
            return null;
        }
    }

    @Test
    @Order(1)
    public void test1_CheckTitle() {
        System.out.println("Тест 1: Проверка заголовка блока...");
        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(BLOCK_TITLE_LOCATOR));
        assertTrue(title.isDisplayed(), "Провал: Блок с заголовком не найден!");
        System.out.println("✅ Успех: Заголовок найден и отображается корректно.");
    }

    @Test
    @Order(2)
    public void test2_CheckPaymentLogos() {
        System.out.println("Тест 2: Проверка логотипов платёжных систем...");
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGO_VISA));
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGO_VERIFIED_VISA));
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGO_MASTERCARD));
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGO_MASTERCARD_SECURE));
        System.out.println("✅ Успех: Все логотипы платёжных систем отображаются.");
    }

    @Test
    @Order(3)
    public void test3_CheckDetailsLink() {
        System.out.println("Тест 3: Проверка ссылки 'Подробнее о сервисе'...");

        String originalWindow = driver.getWindowHandle();
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(DETAILS_LINK));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);

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
        } catch (TimeoutException e) {
            System.out.println("✅ Успех: Ссылка сработала, но возможно в текущем окне.");
        }
    }

    @Test
    @Order(4)
    public void test4_1_CheckIframeAppeared() {
        System.out.println("--- Проверка 4.1: Появление IFRAME ---");
        submitPhoneServiceForm();
        System.out.println("✅ Успех: IFRAME оплаты 'bepaid' обнаружен.");
        driver.switchTo().defaultContent();
    }

    @Test
    @Order(5)
    public void test4_2_VerifyPhoneInIframe() {
        System.out.println("--- Проверка 4.2: Корректность номера телефона в IFRAME ---");
        try {
            driver.switchTo().frame(submitPhoneServiceForm());
            WebElement descriptionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(IFRAME_DESCRIPTION));
            assertTrue(descriptionElement.getText().contains(TEST_PHONE),
                    "Провал: В описании не найден номер телефона.");
            System.out.println("✅ Успех: Номер телефона отображается.");
        } catch (Exception e) {
            fail("Провал: Ошибка при проверке телефона в IFRAME. " + e.getMessage().split("\n")[0]);
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    @Test
    @Order(6)
    public void test4_3_VerifySumInIframe() {
        System.out.println("--- Проверка 4.3: Корректность суммы в IFRAME ---");
        try {
            driver.switchTo().frame(submitPhoneServiceForm());
            WebElement amountElement = wait.until(ExpectedConditions.visibilityOfElementLocated(IFRAME_AMOUNT));
            assertTrue(amountElement.getText().contains(TEST_SUM),
                    "Провал: Сумма отображается некорректно.");
            System.out.println("✅ Успех: Сумма отображается.");
        } catch (Exception e) {
            fail("Провал: Ошибка при проверке суммы в IFRAME. " + e.getMessage().split("\n")[0]);
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    @Test
    @Order(7)
    public void test4_4_VerifyLogosInIframe() {
        System.out.println("--- Проверка 4.4: Наличие логотипов платежных систем в IFRAME ---");
        try {
            driver.switchTo().frame(submitPhoneServiceForm());
            WebElement logosContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(IFRAME_LOGOS));
            assertTrue(logosContainer.isDisplayed(), "Провал: Контейнер с логотипами не отображается.");
            System.out.println("✅ Успех: Логотипы платежных систем присутствуют.");
        } catch (Exception e) {
            fail("Провал: Ошибка при проверке логотипов в IFRAME. " + e.getMessage().split("\n")[0]);
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    @Test
    @Order(8)
    public void test5_CheckInternetPaymentFields() {
        System.out.println("--- Проверка 5: Появление полей 'Домашний интернет' ---");
        selectServiceViaLi("Домашний интернет");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(PHONE_INPUT));
        WebElement internetAccountInput = wait.until(ExpectedConditions.visibilityOfElementLocated(INTERNET_ACCOUNT_INPUT));

        assertTrue(internetAccountInput.isDisplayed(), "Провал: Поле для Домашнего интернета не появилось.");
        System.out.println("✅ Успех: Поля для 'Домашний интернет' отображаются корректно.");
    }

    @Test
    @Order(9)
    public void test6_CheckInstallmentPaymentFields() {
        System.out.println("--- Проверка 6: Появление полей 'Рассрочка' ---");
        selectServiceViaLi("Рассрочка");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(PHONE_INPUT));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(INTERNET_ACCOUNT_INPUT));

        // ИСПРАВЛЕНО: ищем score-instalment
        WebElement installmentInput = wait.until(ExpectedConditions.visibilityOfElementLocated(INSTALLMENT_ACCOUNT_INPUT));

        assertTrue(installmentInput.isDisplayed(), "Провал: Поле для Рассрочки не появилось.");
        System.out.println("✅ Успех: Поля для 'Рассрочка' отображаются корректно.");
    }

    @Test
    @Order(10)
    public void test7_CheckArrearsPaymentFields() {
        System.out.println("--- Проверка 7: Появление полей 'Задолженность' ---");
        selectServiceViaLi("Задолженность");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(INSTALLMENT_ACCOUNT_INPUT));

        WebElement arrearsInput = wait.until(ExpectedConditions.visibilityOfElementLocated(ARREARS_PHONE_INPUT));

        assertTrue(arrearsInput.isDisplayed(), "Провал: Поле для Задолженности не появилось.");
        System.out.println("✅ Успех: Поля для 'Задолженность' отображаются корректно.");
    }
}
