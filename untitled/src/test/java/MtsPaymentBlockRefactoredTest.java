import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class MtsPaymentBlockRefactoredTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String BASE_URL = "https://www.mts.by/";
    private final String TEST_PHONE = "297777777";
    private final String TEST_SUM = "1.00";

    private final By BLOCK_TITLE_LOCATOR = By.xpath(".//h2[contains(., 'Онлайн пополнение') and contains(., 'без комиссии')]");

    private final By LODO_VISA_LOCATOR = By.xpath(".//img[@alt='Visa']");
    private final By LODO_MASTERCARD_LOCATOR = By.xpath(".//img[@alt='MasterCard']");
    private final By LODO_BELCARD_LOCATOR = By.xpath(".//img[@alt='MasterCard Secure Code']");
    private final By LODO_VERIFIED_VISA_LOCATOR = By.xpath(".//img[@alt='Verified By Visa']");

    private final By DETAILS_LINK_LOCATOR = By.xpath(".//a[contains(@href, 'poryadok-oplaty-i-bezopasnost')]");

    private final By PHONE_INPUT_LOCATOR = By.id("connection-phone");
    private final By SUM_INPUT_LOCATOR = By.id("connection-sum");

    private final By PROCEED_BUTTON_LOCATOR = By.xpath(".//form//button[@type='submit' and contains(., 'Продолжить')]");

    private final By PAYMENT_IFRAME_LOCATOR = By.tagName("iframe");

    private final By COOKIE_VISIBILITY_LOCATOR = By.cssSelector("div.cookie.show");

    // ----------------------------------------------------------------------------------

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.get(BASE_URL);

        String originalWindow = driver.getWindowHandle();
        if (driver.getWindowHandles().size() > 1) {
            for (String windowHandle : driver.getWindowHandles()) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle).close();
                }
            }
            driver.switchTo().window(originalWindow); // Возвращаемся на главную
            System.out.println("Информация: Закрыты лишние окна/вкладки.");
        }

        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement cookieShowBlock = shortWait.until(ExpectedConditions.presenceOfElementLocated(COOKIE_VISIBILITY_LOCATOR));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].remove();", cookieShowBlock);

            shortWait.until(ExpectedConditions.invisibilityOfElementLocated(COOKIE_VISIBILITY_LOCATOR));

            System.out.println("Успех: Куки-блок на главной странице полностью удален из DOM.");
        } catch (Exception ignored) {
            System.out.println("Информация: Блок куки не найден или не мешает.");
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ТЕСТ 1: Проверка Заголовка

    @Test
    public void test1_CheckTitle() {
        System.out.println("--- Проверка 1: Название блока ---");

        WebElement titleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(BLOCK_TITLE_LOCATOR));
        assertTrue(titleElement.isDisplayed(), "Провал: Блок не отображается.");
        System.out.println("Успех: Блок найден и название соответствует.");
    }

    // ТЕСТ 2: Проверка Логотипов Платежных Систем

    @Test
    public void test2_CheckPaymentLogosPresence() {
        System.out.println("--- Проверка 2: Наличие логотипов ---");

        wait.until(ExpectedConditions.visibilityOfElementLocated(LODO_VISA_LOCATOR));
        wait.until(ExpectedConditions.visibilityOfElementLocated(LODO_VERIFIED_VISA_LOCATOR));
        wait.until(ExpectedConditions.visibilityOfElementLocated(LODO_MASTERCARD_LOCATOR));
        wait.until(ExpectedConditions.visibilityOfElementLocated(LODO_BELCARD_LOCATOR));

        System.out.println("Успех: Все указанные логотипы найдены и отображаются.");
    }

    // ТЕСТ 3: Проверка Ссылки "Подробнее о сервисе"

    @Test
    public void test3_CheckDetailsLink() {
        System.out.println("--- Проверка 3: Работа ссылки 'Подробнее о сервисе' ---");

        String originalUrl = driver.getCurrentUrl();

        WebElement detailsLink = wait.until(ExpectedConditions.elementToBeClickable(DETAILS_LINK_LOCATOR));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", detailsLink);
        System.out.println("Успех: Ссылка 'Подробнее о сервисе' нажата через JavaScript.");

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl)));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("poryadok") || currentUrl.contains("info") || currentUrl.contains("подробнее"),
                "Провал: Ссылка не перевела на страницу 'Подробнее'. URL: " + currentUrl);

        System.out.println("Успех: Ссылка 'Подробнее' работает и открывает новую страницу: " + currentUrl);

    }

    // ТЕСТ 4: Заполнение формы и проверка открытия

    @Test
    public void test4_CheckFormSubmission() {
        System.out.println("--- Проверка 4: Заполнение формы и кнопки 'Продолжить' ---");

        driver.findElement(PHONE_INPUT_LOCATOR).sendKeys(TEST_PHONE);
        driver.findElement(SUM_INPUT_LOCATOR).sendKeys(TEST_SUM);

        WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(PROCEED_BUTTON_LOCATOR));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedButton);
        System.out.println("Успех: Нажата кнопка 'Продолжить' через JavaScript.");

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(PAYMENT_IFRAME_LOCATOR));

            driver.switchTo().frame(driver.findElement(PAYMENT_IFRAME_LOCATOR));

            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("html")));

            System.out.println("Успех: Обнаружен IFRAME оплаты. Модальное окно загружено.");

        } catch (Exception e) {
            fail("Провал: Не удалось найти и переключиться на IFRAME оплаты. " +
                    "Текущий URL: " + driver.getCurrentUrl() +
                    " Ошибка: " + e.getMessage().split("\n")[0]);
        }

        driver.switchTo().defaultContent();
        String paymentUrl = driver.getCurrentUrl();
        assertTrue(paymentUrl.contains(BASE_URL),
                "Провал: URL изменился, хотя должно было открыться модальное окно. URL: " + paymentUrl);

        System.out.println("Успех: Тест 4 завершен.");
    }
}