import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class LuuNguyenThanhVu_21130612_Lab8 {

  // WebDriver instance for interacting with the browser
  private WebDriver driver;

  // JavascriptExecutor for executing JS scripts in the browser
  private JavascriptExecutor js;

  // WebDriverWait for waiting on elements or conditions
  private WebDriverWait wait;

  // Map to store temporary variables (if needed)
  private Map<String, Object> vars;
  private String orderConfirmationLink;
  //-----------------------------------------
  // Setup method runs before each test
  //-----------------------------------------
  @BeforeEach
  public void setUp() {
    WebDriverManager.chromedriver().setup();
    driver = new ChromeDriver();
    js = (JavascriptExecutor) driver; // Khởi tạo JS Executor
    vars = new HashMap<String, Object>();

    // Tăng thời gian chờ lên 15s cho chắc ăn
    wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // 1. Vào trang sản phẩm
    driver.get("https://dienmaycholon.com/tivi/smart-tivi-lg-ai-4k-75-inch-75ua8450psa");
    driver.manage().window().maximize();

    try {
      // 2. Tìm nút mua hàng (Dùng XPath chính xác nhất)
      WebElement buyBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
              By.xpath("//button[contains(@class, 'click_buy')]")
      ));

      // 3. Scroll xuống chỗ nút đó (đề phòng nút nằm dưới cùng màn hình)
      js.executeScript("arguments[0].scrollIntoView(true);", buyBtn);
      Thread.sleep(1000); // Nghỉ 1 xíu cho màn hình cuộn xong

      // 4. "CHIÊU CUỐI": Dùng Javascript để Click (Bất chấp popup che)
      js.executeScript("arguments[0].click();", buyBtn);

    } catch (Exception e) {
      System.out.println("Lỗi: Vẫn không click được. Hãy kiểm tra lại link hoặc mạng.");
      e.printStackTrace(); // In lỗi chi tiết ra để xem
    }
  }

  //-----------------------------------------
  // Teardown method runs after each test
  //-----------------------------------------
  @AfterEach
  public void tearDown() {
    if (driver != null) {
      // Quit the browser and close all windows
      driver.quit();
    }
  }

  //-----------------------------------------
  // TC_OD_2 - Fill all required fields correctly
  //-----------------------------------------
  @Test
  @Order(1)
  public void TC_OD_2_CheckFullField() {
    // Wait for the Name field to be visible
    WebElement nameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("name")));
    nameField.sendKeys("Vũ");

    WebElement phoneField = driver.findElement(By.name("phone"));
    phoneField.sendKeys("012-345-6789");

    WebElement noteField = driver.findElement(By.name("note"));
    noteField.sendKeys("123");

    // Select Province
    driver.findElement(By.cssSelector("#vs1__combobox .vs__search")).click();
    driver.findElement(By.id("vs1__option-0")).click();

    // Select District
    driver.findElement(By.cssSelector("#vs2__combobox .vs__search")).click();
    driver.findElement(By.id("vs2__option-2")).click();

    // Enter Address
    WebElement addressField = driver.findElement(By.name("address"));
    addressField.sendKeys("test");

    // Select Payment Method
    driver.findElement(By.cssSelector("li:nth-child(1) > label")).click();
  }

  //-----------------------------------------
  // TC_OD_3 - Leave Name / Phone blank
  //-----------------------------------------
  @Test
  @Order(2)
  public void TC_OD_3_BlankRequiredFields() {
    // Only enter note
    WebElement noteField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("note")));
    noteField.sendKeys("Test blank fields");

    // Click submit order button
    driver.findElement(By.className("btn_submit_book")).click();

    // Verify Name error message is displayed
    WebElement nameError = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(),'Họ tên không để trống')]")
    ));

    // Verify Phone error message is displayed
    WebElement phoneError = driver.findElement(
            By.xpath("//*[contains(text(),'Số điện thoại không để trống')]")
    );

    Assertions.assertTrue(nameError.isDisplayed());
    Assertions.assertTrue(phoneError.isDisplayed());
  }

  //-----------------------------------------
  // TC_OD_4 - Enter invalid phone number
  //-----------------------------------------
  @Test
  @Order(3)
  public void TC_OD_4_InvalidPhone() {
    // Fill Name field
    WebElement nameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("name")));
    nameField.sendKeys("Vũ Test");

    // Fill Phone field with invalid format
    WebElement phoneField = driver.findElement(By.name("phone"));
    phoneField.sendKeys("12345");

    // Click submit
    driver.findElement(By.className("btn_submit_book")).click();

    // Verify phone error message appears
    WebElement phoneError = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(text(),'Số điện thoại không đúng định dạng')]")
    ));
    Assertions.assertTrue(phoneError.isDisplayed());
  }

  //-----------------------------------------
  // TC_OD_5 - Switch delivery method
  //-----------------------------------------
  @Test
  @Order(4)
  public void TC_OD_5_DeliveryMethodSwitch() {
    // Select "Home Delivery" option
    WebElement homeOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector(".select_delivery-up > .container_cart:nth-child(1)")
    ));
    homeOption.click();

    // Verify Address section appears
    WebElement addressSection = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.name("address")
    ));
    Assertions.assertTrue(addressSection.isDisplayed());

    // Select "Pick up at Store" option
    WebElement storeOption = driver.findElement(
            By.cssSelector(".select_delivery-up > .container_cart:nth-child(2)")
    );
    storeOption.click();

    // Verify store list is displayed
    WebElement storeList = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector(".go_market, .position_market")
    ));
    Assertions.assertTrue(storeList.isDisplayed());
  }
  //-----------------------------------------
  // TC_CO_2, TC_CO_3 - OrderProduct
  //-----------------------------------------
  @Test
  @Order(5)
  public void TC_CO_2_3_ClickOrderButton() {
    // Wait for the Name field to be visible
    WebElement nameField = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("name")));
    nameField.sendKeys("Vũ");

    WebElement phoneField = driver.findElement(By.name("phone"));
    phoneField.sendKeys("035-295-1234");

    WebElement noteField = driver.findElement(By.name("note"));
    noteField.sendKeys("123");

    // Select Province
    driver.findElement(By.cssSelector("#vs1__combobox .vs__search")).click();
    driver.findElement(By.id("vs1__option-0")).click();

    // Select District
    driver.findElement(By.cssSelector("#vs2__combobox .vs__search")).click();
    driver.findElement(By.id("vs2__option-2")).click();

    // Enter Address
    WebElement addressField = driver.findElement(By.name("address"));
    addressField.sendKeys("test");

    // Select Payment Method
    driver.findElement(By.cssSelector("li:nth-child(1) > label")).click();

    // Click the order button
    WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.className("btn_submit_book") // assuming this is the button ID
    ));
    orderButton.click();
  }
}
