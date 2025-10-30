import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentBlockPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By BLOCK_TITLE_LOCATOR =
            By.xpath(".//h2[contains(., 'Онлайн пополнение') and contains(., 'без комиссии')]");
    private final By LOGO_VISA = By.xpath(".//img[@alt='Visa']");
    private final By LOGO_MASTERCARD = By.xpath(".//img[@alt='MasterCard']");
    private final By LOGO_MASTERCARD_SECURE = By.xpath(".//img[@alt='MasterCard Secure Code']");
    private final By LOGO_VERIFIED_VISA = By.xpath(".//img[@alt='Verified By Visa']");
    private final By DETAILS_LINK = By.xpath(".//a[contains(@href, 'poryadok-oplaty-i-bezopasnost')]");
    private final By COOKIE_BLOCK = By.cssSelector("div.cookie.show");

    private final By SERVICE_HEADER_LOCATOR_VISUAL = By.xpath("//button[contains(@class,'select__header')]");
    private final By SERVICE_LIST_LOCATOR = By.xpath("//ul[contains(@class,'select__list')]");
    private final By PROCEED_BUTTON = By.xpath(".//form//button[@type='submit' and contains(., 'Продолжить')]");
    private final By SUM_INPUT = By.id("connection-sum");

    private final By PHONE_INPUT = By.id("connection-phone");
    private final By INTERNET_ACCOUNT_INPUT = By.id("internet-account");
    private final By INSTALLMENT_ACCOUNT_INPUT = By.id("score-instalment");
    private final By ARREARS_PHONE_INPUT = By.id("score-arrears");

    private final By PAYMENT_IFRAME = By.xpath("//iframe[contains(@class, 'bepaid-iframe')]");

    public PaymentBlockPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void openPage(String url) {
        driver.get(url);
        try {
            WebElement cookieBlock = wait.withTimeout(Duration.ofSeconds(5))
                    .until(ExpectedConditions.presenceOfElementLocated(COOKIE_BLOCK));
            ((JavascriptExecutor) driver).executeScript("arguments[0].remove();", cookieBlock);
            System.out.println("Информация: Куки-блок удалён из DOM.");
        } catch (TimeoutException ignored) {
            System.out.println("Информация: Куки-блок отсутствует, продолжаем.");
        }
    }

    public void selectService(String serviceName) {
        By optionLocator = By.xpath("//ul[contains(@class,'select__list')]//li[contains(., '" + serviceName + "')]");

        int attempts = 0;
        while (attempts < 2) {
            try {
                WebElement headerButton = wait.until(ExpectedConditions.elementToBeClickable(SERVICE_HEADER_LOCATOR_VISUAL));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", headerButton);
                Thread.sleep(400); // Ожидание для стабилизации UI

                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", headerButton);
                System.out.println("Информация: Выполнен JS-клик по кнопке выбора услуги.");

                WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", optionElement);
                System.out.println("✅ Выбрана услуга (попытка #" + (attempts + 1) + "): " + serviceName);
                wait.until(ExpectedConditions.invisibilityOfElementLocated(SERVICE_LIST_LOCATOR));
                return;
            } catch (Exception e) {
                attempts++;
                System.err.println("⚠️ Попытка #" + attempts + " не удалась: " + e.getMessage().split("\n")[0]);
                if (attempts == 2) {
                    throw new RuntimeException("Не удалось выбрать услугу " + serviceName + " после 2 попыток.", e);
                }
                driver.navigate().refresh();
                openPage(driver.getCurrentUrl());
                wait.until(ExpectedConditions.visibilityOfElementLocated(SERVICE_HEADER_LOCATOR_VISUAL));
            }
        }
    }

    public void fillPhoneNumber(String phone) {
        driver.findElement(PHONE_INPUT).sendKeys(phone);
    }

    public void fillSum(String sum) {
        driver.findElement(SUM_INPUT).sendKeys(sum);
    }

    public void clickProceedButton() {
        WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(PROCEED_BUTTON));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", proceedButton);
        System.out.println("Информация: Форма отправлена.");
    }

    public void submitPhoneServiceForm(String phone, String sum) {
        selectService("Услуги связи");
        fillPhoneNumber(phone);
        fillSum(sum);
        clickProceedButton();
    }

    public void clickDetailsLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(DETAILS_LINK));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
    }

    public BePaidIframePage switchToPaymentIframe() {
        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(PAYMENT_IFRAME));
            driver.switchTo().frame(iframe);
            System.out.println("Информация: Переключились на IFRAME.");
            return new BePaidIframePage(driver);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось найти или переключиться на IFRAME оплаты", e);
        }
    }


    public boolean isTitleDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(BLOCK_TITLE_LOCATOR)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean arePaymentLogosDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGO_VISA));
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGO_VERIFIED_VISA));
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGO_MASTERCARD));
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGO_MASTERCARD_SECURE));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPaymentIframeDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(PAYMENT_IFRAME));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPhoneInputDisplayed() {
        try {
            return driver.findElement(PHONE_INPUT).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInternetAccountInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(INTERNET_ACCOUNT_INPUT)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isInstallmentInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(INSTALLMENT_ACCOUNT_INPUT)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isArrearsInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(ARREARS_PHONE_INPUT)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
