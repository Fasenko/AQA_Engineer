import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BePaidIframePage {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By IFRAME_DESCRIPTION = By.xpath(".//div[@class='pay-description']");
    private final By IFRAME_AMOUNT = By.xpath(".//div[@class='pay-amount']");
    private final By IFRAME_LOGOS = By.xpath(".//div[@class='pay-system-container']");

    public BePaidIframePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    public String getDescriptionText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(IFRAME_DESCRIPTION)).getText();
        } catch (Exception e) {
            return "Не удалось найти описание";
        }
    }

    public String getAmountText() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(IFRAME_AMOUNT)).getText();
        } catch (Exception e) {
            return "Не удалось найти сумму";
        }
    }

    public boolean areLogosDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(IFRAME_LOGOS)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
        System.out.println("Информация: Вернулись из IFRAME.");
    }
}

