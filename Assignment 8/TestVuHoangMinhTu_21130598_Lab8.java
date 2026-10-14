package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class VuHoangMinhTu_21130598_Lab8 {
    private WebDriver driver;
    private WebDriverWait wait;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;

        // Wait tối đa 15 giây (mạng lag thì đợi lâu hơn chút)
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Hàm hỗ trợ click bằng JS (Click bất tử)
    public void clickByJS(String cssSelector) {
        js.executeScript("document.querySelector('" + cssSelector + "').click()");
    }

    // --- TEST CASE 1: Đăng nhập thành công ---
    @Test
    public void test_01_a_loginSuccess() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");

        // Nghỉ 2s để web load hết quảng cáo
        Thread.sleep(2000);

        // Click icon đăng nhập
        clickByJS("#user_box .icon_login");

        // Nhập thông tin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("21130598@st.hcmuaf.edu.vn");
        driver.findElement(By.id("password")).sendKeys("Minhtu2003");

        // Click nút đăng nhập
        clickByJS(".btn_register > span");

        // --- QUAN TRỌNG: Đợi chuyển trang và kiểm tra ---
        Thread.sleep(3000); // Đợi 3s để web xử lý login và redirect

        String currentUrl = driver.getCurrentUrl();
        System.out.println("URL sau khi login: " + currentUrl); // In ra để kiểm tra

        // 1. BẮT LỖI NGHIỆP VỤ (Bug Catching)
        // Nếu URL chứa chữ "thong-tin-tai-khoan" -> Nghĩa là web đã redirect sai -> FAIL test ngay
        if (currentUrl.contains("thong-tin-tai-khoan")) {
            Assert.fail("LỖI NGHIỆP VỤ: Đăng nhập thành công nhưng lại bị chuyển sang trang 'Thông tin tài khoản' thay vì 'Trang chủ'!");
        }

        // 2. KIỂM TRA ĐĂNG NHẬP THÀNH CÔNG (Sanity Check)
        // Nếu code chạy xuống được đây (tức là không bị fail ở trên), ta kiểm tra xem đã thực sự login chưa
        // Bằng cách check xem có hiện tên user "Minh Tú" hoặc nút Logout không
        try {
            boolean isLoggedIn = driver.getPageSource().contains("Xin chào") ||
                    driver.findElements(By.cssSelector(".logout")).size() > 0;

            Assert.assertTrue("Lỗi: Đăng nhập không thành công (Không thấy lời chào hoặc nút Logout)", isLoggedIn);
        } catch (Exception e) {
            Assert.fail("Lỗi kiểm tra phần tử sau đăng nhập: " + e.getMessage());
        }
    }

    // --- TEST CASE 2: Đăng xuất ---
    @Test
    public void test_02_a_logout() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");

        // Nghỉ 2s để web load hết quảng cáo
        Thread.sleep(2000);

        // Click icon đăng nhập
        clickByJS("#user_box .icon_login");

        // Nhập thông tin
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("21130598@st.hcmuaf.edu.vn");
        driver.findElement(By.id("password")).sendKeys("Minhtu2003");

        // Click nút đăng nhập
        clickByJS(".btn_register > span");

        // --- QUAN TRỌNG: Đợi chuyển trang và kiểm tra ---
        Thread.sleep(3000); // Đợi 3s để web xử lý login và redirect

        Thread.sleep(2000); // Đợi web ổn định

        // Click đăng xuất bằng JS
        clickByJS(".logout > span");

        // Kiểm tra đã logout chưa (icon login hiện lại)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#user_box .icon_login")));
    }

    // --- TEST CASE 3: Đăng nhập sai định dạng email
    @Test
    public void test_03_loginWithInvalidEmail_CheckBlur() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");
        Thread.sleep(2000);

        clickByJS("#user_box .icon_login");

        // 1. Nhập liệu sai định dạng vào ô Username
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        userField.sendKeys("0982407940ab");

        // 2. KÍCH HOẠT SỰ KIỆN BLUR (Quan trọng)
        // Để web biết là mình đã nhập xong và rời khỏi ô đó -> Nó mới chạy code check lỗi
        // Cách tốt nhất là click chuột sang ô Password
        driver.findElement(By.id("password")).click();

        // 3. KIỂM TRA LỖI (Bắt TimeoutException)
        try {
            // Chờ xem class .text_error có xuất hiện ngay dưới ô username không
            // (Thời gian chờ mặc định theo biến 'wait' của bạn, thường là 10-15s)
            WebElement errorMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".text_error")));

            // Nếu tìm thấy thì in ra console
            System.out.println("Pass: Đã hiện lỗi blur -> " + errorMsg.getText());

        } catch (TimeoutException e) {
            // --- XỬ LÝ FAIL ---
            // Nếu chờ mãi mà không thấy class .text_error hiện ra -> Đánh Fail ngay
            Assert.fail("TEST FAILED: Đã nhập sai format và blur ra ngoài, nhưng giao diện KHÔNG hiện thông báo lỗi (class .text_error)!");
        }
    }
    // --- TEST CASE 4: Đăng nhập sai mật khẩu ---
    @Test
    public void test_04_loginWithWrongPassword() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");
        Thread.sleep(2000);

        clickByJS("#user_box .icon_login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("21130598@st.hcmuaf.edu.vn");
        driver.findElement(By.id("password")).sendKeys("matkhausai123");

        clickByJS(".btn_register");

        // Thêm đoạn wait hoặc assert ở đây nếu cần kiểm tra thông báo lỗi
        Thread.sleep(2000);
    }

    // --- TEST CASE 5: Đăng ký thành công ---
    @Test
    public void test_05_registerSuccess() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");
        Thread.sleep(2000);

        // Click nút đăng ký bằng JS
        clickByJS("#user_box .icon_register");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("toiyeu12nguoi2003");
        driver.findElement(By.id("email")).sendKeys("toiyeu12nguoi2003@gmail.com");
        driver.findElement(By.id("password")).sendKeys("Minhtu2003");
        driver.findElement(By.id("repassword")).sendKeys("Minhtu2003");
        driver.findElement(By.name("fullname")).sendKeys("Minh Tú");
        driver.findElement(By.id("phone")).sendKeys("0385528994");

        // --- XỬ LÝ DROPDOWN ---
        // Vẫn giữ Class Select vì nó ổn định hơn click option thủ công
        new Select(driver.findElement(By.id("day"))).selectByVisibleText("11");
        new Select(driver.findElement(By.id("month"))).selectByVisibleText("08");
        new Select(driver.findElement(By.id("year"))).selectByVisibleText("2005");

        // Phần Tỉnh/Thành hay bị lỗi tên, hãy check kỹ tên trên web
        // Nếu Select lỗi, có thể dùng lại cách click JS cũ của bạn cho Tỉnh
        try {
            new Select(driver.findElement(By.id("city"))).selectByVisibleText("Hồ Chí Minh");
        } catch (Exception e) {
            // Fallback: Nếu không tìm thấy tên TP, click đại cái đầu tiên
            new Select(driver.findElement(By.id("city"))).selectByIndex(1);
        }

        driver.findElement(By.id("address")).sendKeys("123 Đường ABC");

        // Click checkbox Đồng ý
        clickByJS("#agree");

        // Click Đăng ký
        clickByJS(".btn_register");

        Thread.sleep(3000); // Đợi xử lý
        String currentUrl = driver.getCurrentUrl();
        if (currentUrl.contains("thong-tin-tai-khoan")) {
            // Nếu vào đây nghĩa là BUG -> Cho Test Fail luôn
            Assert.fail("LỖI NGHIÊM TRỌNG: Đăng ký xong vào thẳng trang 'Thông tin tài khoản' mà không bắt xác minh!");
        }
    }

    // --- TEST CASE 6: Đăng ký không đúng định dạng email ---
    @Test
    public void test_06_registerWithInvalidEmail() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");
        Thread.sleep(2000);

        clickByJS("#user_box .icon_register");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys("testuser123");

        // Nhập email sai định dạng
        driver.findElement(By.id("email")).sendKeys("emailkhongdungdinhdang");

        // Điền đại mấy trường bắt buộc khác
        driver.findElement(By.id("password")).sendKeys("Pass123");
        driver.findElement(By.id("repassword")).sendKeys("Pass123");
        driver.findElement(By.name("fullname")).sendKeys("Test User");
        driver.findElement(By.id("phone")).sendKeys("0909123123");

        // Click checkbox Đồng ý
        clickByJS("#agree");

        // Click Đăng ký để trigger lỗi
        clickByJS(".btn_register");

        // --- ĐOẠN XỬ LÝ CHECK LỖI ---
        try {
            // 1. Chờ tối đa (theo time của wait) để element .text_error xuất hiện
            WebElement errorText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".text_error")));

            // 2. Nếu xuất hiện -> Kiểm tra nội dung text
            // (Dùng Assert của JUnit cho đơn giản và chuẩn xác)
            String actualMsg = errorText.getText();
            Assert.assertEquals("Nội dung thông báo lỗi không đúng!", "Email không đúng định dạng", actualMsg);

        } catch (TimeoutException e) {
            // 3. NẾU KHÔNG THẤY ELEMENT (Hết thời gian chờ):
            // Thay vì throw e (báo lỗi Exception), ta dùng Assert.fail để báo Test Fail "nhẹ nhàng"
            Assert.fail("TEST FAILED: Đã nhập email sai nhưng không thấy hiện thông báo lỗi class '.text_error'!");
        }
    }
}