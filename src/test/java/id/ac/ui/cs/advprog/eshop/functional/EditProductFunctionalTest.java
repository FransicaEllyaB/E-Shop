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
public class EditProductFunctionalTest {
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
        driver.findElement(By.id("productEdit_" + productId)).click();

        driver.findElement(By.id("nameInput")).clear();
        driver.findElement(By.id("nameInput")).sendKeys("Shampoo Super Ultra");
        driver.findElement(By.id("quantityInput")).clear();
        driver.findElement(By.id("quantityInput")).sendKeys("100");

        driver.findElement(By.id("submit")).click();

        WebElement editedProductName = driver.findElement(By.id("productName_" + productId));
        WebElement editedProductQuantity = driver.findElement(By.id("productQuantity_" + productId));

        assertEquals("Shampoo Super Ultra", editedProductName.getText());
        assertEquals("100", editedProductQuantity.getText());
    }
}