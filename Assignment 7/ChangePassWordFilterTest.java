package com.example.demo;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ChangePassWordFilterTest {

  private WebDriver driver;
  private WebDriverWait wait;

  // ================= SETUP & TEARDOWN =================

  @Before
  public void setUp() {
    driver = new ChromeDriver();
    driver.manage().window().maximize();
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
  }

  @After
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  // ================= COMMON FUNCTIONS =================

  private void login() {
    driver.get("https://dienmaycholon.com/dang-nhap");

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")))
            .sendKeys("phamhoangtien832003@gmail.com");

    driver.findElement(By.id("password")).sendKeys("12345678");

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn_register"))).click();
  }

  private void openChangePasswordPage() {
    login();

    By changePw = By.xpath("//a[contains(text(),'Đổi mật khẩu')]");
    wait.until(ExpectedConditions.elementToBeClickable(changePw)).click();
  }

  // ================= CHANGE PASSWORD TEST CASES =================

  // TC_CCP_1: Đổi mật khẩu hợp lệ
  @Test
  public void TC_CCP_1_valid() {
    openChangePasswordPage();

    driver.findElement(By.name("oldpassword")).sendKeys("12345678");
    driver.findElement(By.name("password")).sendKeys("12345678");
    driver.findElement(By.name("repassword")).sendKeys("12345678");

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn_confirm"))).click();
  }

  // TC_CCP_2: Sai mật khẩu cũ
  @Test
  public void TC_CCP_2_wrongOldPassword() {
    openChangePasswordPage();

    driver.findElement(By.name("oldpassword")).sendKeys("123456");
    driver.findElement(By.name("password")).sendKeys("12345678");
    driver.findElement(By.name("repassword")).sendKeys("12345678");

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn_confirm"))).click();
  }

  // TC_CCP_3: Mật khẩu mới và nhập lại không khớp
  @Test
  public void TC_CCP_3_passwordNotMatch() {
    openChangePasswordPage();

    driver.findElement(By.name("oldpassword")).sendKeys("12345678");
    driver.findElement(By.name("password")).sendKeys("123456");
    driver.findElement(By.name("repassword")).sendKeys("1234567");

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn_confirm"))).click();
  }

  // TC_CCP_4: Mật khẩu quá ngắn
  @Test
  public void TC_CCP_4_passwordTooShort() {
    openChangePasswordPage();

    driver.findElement(By.name("oldpassword")).sendKeys("12345678");
    driver.findElement(By.name("password")).sendKeys("123");
    driver.findElement(By.name("repassword")).sendKeys("123");

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn_confirm"))).click();
  }

  // ================= FILTER TEST CASES =================

  // TC_FILTER_1: Lọc theo giá 5-7 triệu
  @Test
  public void TC_FILTER_1_priceRange() {
    driver.get("https://dienmaycholon.com/tu-khoa/tivi");

    wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".filter-total > .filter-item__title"))).click();

    wait.until(ExpectedConditions.elementToBeClickable(
            By.linkText("Từ 5 - 7 triệu"))).click();

    wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".filter-button--total > .btn-filter-readmore"))).click();
  }

  // TC_FILTER_2: Lọc theo hãng (icon)
  @Test
  public void TC_FILTER_2_brandIcon() {
    driver.get("https://dienmaycholon.com/tu-khoa/tivi");

    wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".filter-total > .filter-item__title"))).click();

    By brandIcon = By.cssSelector(".filter-list:nth-child(2) > .c-btnbox:nth-child(2) > img");
    wait.until(ExpectedConditions.elementToBeClickable(brandIcon)).click();

    wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".filter-button--total > .btn-filter-readmore"))).click();
  }

  // TC_FILTER_3: Lọc nhiều hãng
  @Test
  public void TC_FILTER_3_multiBrand() {
    driver.get("https://dienmaycholon.com/tu-khoa/tivi");
    driver.manage().window().setSize(new Dimension(1296, 688));

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    driver.findElement(By.cssSelector(".filter-total > .filter-item__title")).click();

    WebElement brand1 = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".filter-list:nth-child(2) > .filter-logo:nth-child(2)")
            )
    );
    brand1.click();

    WebElement brand2 = wait.until(
            ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".filter-list:nth-child(2) > .filter-logo:nth-child(3)")
            )
    );
    brand2.click();

    driver.findElement(By.cssSelector(".filter-button--total > .btn-filter-readmore")).click();
  }

}
