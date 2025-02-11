package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class DeleteProductFunctionalTest {
    /**
     * The port number assigned to the running application during test execution.
     * Set automatically during each test run by Spring Framework’s test context.
     */
    @LocalServerPort
    private int serverPort;

    /**
     * The base URL for testing. Default to {@code http://localhost}.
     */
    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void editProduct_isCorrect (ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.id("checkProduct")).click();
        driver.findElement(By.id("createProduct")).click();

        driver.findElement(By.id("nameInput")).sendKeys("Shampoo Ultra");
        driver.findElement(By.id("quantityInput")).sendKeys("50");
        driver.findElement(By.id("submit")).click();

        WebElement productRow = driver.findElement(By.xpath("//tbody/tr[1]"));
        WebElement productNameCell = productRow.findElement(By.xpath("./td[1]"));
        String idAttribute = productNameCell.getAttribute("id");

        String productId = (idAttribute != null) ? idAttribute.replace("productName_", "") : "";
        driver.findElement(By.id("productDelete_" + productId)).click();

        boolean isProductStillThere = driver.findElements(By.xpath("//tr[td[contains(text(), 'Shampoo Ultra')]]")).size() > 0;
        assertFalse(isProductStillThere);
    }
}