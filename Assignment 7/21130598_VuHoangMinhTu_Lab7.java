import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

public class DienMayChoLonTestSuite {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @Before
    public void setUp() {
        // Khởi tạo trình duyệt trước mỗi Test Case
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<String, Object>();
    }

    @After
    public void tearDown() {
        // Đóng trình duyệt sau khi chạy xong mỗi Test Case
        driver.quit();
    }

    // --- TEST CASE 1: Đăng nhập với tài khoản, mật khẩu đúng ---
    @Test
    public void loginSuccess() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");
        driver.manage().window().setSize(new Dimension(1552, 840));
        
        // Click mở popup đăng nhập
        js.executeScript("document.querySelector(\"#user_box .icon_login\").click()");
        
        driver.findElement(By.name("username")).sendKeys("21130598@st.hcmuaf.edu.vn");
        driver.findElement(By.id("password")).sendKeys("Minhtu2003");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.cssSelector(".btn_register > span")).click();
        
        // Kiểm tra URL
        Thread.sleep(2000); // Chờ chuyển trang
        vars.put("current_url", js.executeScript("return window.location.href"));
        assertEquals(vars.get("current_url").toString(), "https://dienmaycholon.com/");
    }

    // --- TEST CASE 2: Đăng nhập với thông tin tài khoản không đúng định dạng email hoặc số điện thoại ---
    @Test
    public void loginWithInvalidEmailOrPhoneNumber() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");
        driver.manage().window().setSize(new Dimension(1552, 840));
        
        js.executeScript("document.querySelector(\"#user_box .icon_login\").click()");
        
        driver.findElement(By.name("username")).sendKeys("21130598@st.hcmuaf.edu.vn");
        driver.findElement(By.id("password")).sendKeys("Minhtu2003");
        driver.findElement(By.cssSelector(".content")).click();
        driver.findElement(By.name("username")).sendKeys("nguyenquangbuu2121");
        driver.findElement(By.id("password")).click(); // Click ra ngoài để kích hoạt validation
        
        Thread.sleep(1000);
        assertThat(driver.findElement(By.cssSelector(".error_msg")).getText(), is("Vui lòng nhập đúng định dạng email"));
        Thread.sleep(2000);
    }

    // --- TEST CASE 3: Đăng nhập với tài khoản đúng ,mật khẩu sai ---
    @Test
    public void loginWithWrongPassword() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");
        driver.manage().window().setSize(new Dimension(1552, 840));
        
        js.executeScript("document.querySelector(\"#user_box .icon_login\").click()");
        
        driver.findElement(By.name("username")).sendKeys("21130598@st.hcmuaf.edu.vn");
        driver.findElement(By.id("password")).sendKeys("Minhtu2003");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.cssSelector(".datacustomer")).click();
        driver.findElement(By.id("password")).sendKeys("sdasdasdsada"); // Mật khẩu sai
        driver.findElement(By.cssSelector(".btn_register")).click();
        
        Thread.sleep(2000); // Chờ xem kết quả
    }

    // --- TEST CASE 4: Đăng xuất tài khoản ---
    @Test
    public void logout() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");
        driver.manage().window().setSize(new Dimension(1552, 840));
        
        // Click nút đăng xuất bằng JS
        js.executeScript("document.querySelector(\".logout > span\").click()");
        
        Thread.sleep(2000);
    }

    // --- TEST CASE 5: Đăng ký tài khoản với các trường nhập chính xác thông tin ---
    @Test
    public void registerSuccess() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");
        driver.manage().window().setSize(new Dimension(1552, 840));
        
        js.executeScript("document.querySelector(\"#user_box .icon_register\").click()");
        
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("toiyeu4nguoi2003");
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("toiyeu4nguoi2003@gmail.com");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).sendKeys("Minhtu2003");
        driver.findElement(By.id("repassword")).click();
        driver.findElement(By.id("repassword")).sendKeys("Minhtu2003");
        driver.findElement(By.name("fullname")).click();
        driver.findElement(By.name("fullname")).sendKeys("Minh Tú");
        driver.findElement(By.id("phone")).click();
        driver.findElement(By.id("phone")).sendKeys("0385528994");
        
        // Chọn ngày tháng năm sinh
        driver.findElement(By.id("day")).click();
        driver.findElement(By.xpath("//option[. = '11']")).click();
        driver.findElement(By.id("month")).click();
        driver.findElement(By.xpath("//option[. = '08']")).click();
        driver.findElement(By.id("year")).click();
        driver.findElement(By.xpath("//option[. = '2005']")).click();
        
        // Chọn thành phố
        driver.findElement(By.id("city")).click();
        driver.findElement(By.xpath("//option[. = 'Bình Dương']")).click();
        
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("sadasdasdasd");
        driver.findElement(By.id("agree")).click();
        driver.findElement(By.cssSelector(".btn_register")).click();
        
        // Các bước sau đăng ký
        driver.findElement(By.name("username")).click();
        driver.findElement(By.cssSelector(".content")).click();
        driver.findElement(By.cssSelector(".btn_register > span")).click();
        js.executeScript("window.scrollTo(0,118.4000015258789)");
        
        // Kiểm tra URL
        vars.put("current_url", js.executeScript("return window.location.href"));
        assertEquals(vars.get("current_url").toString(), "https://dienmaycholon.com/");
        
        Thread.sleep(2000);
    }

    // --- TEST CASE 6: Đăng ký tài khoản với tài khoản sai định dạng email hoặc số điện thoại ---
    @Test
    public void registerWithInvalidEmail() throws InterruptedException {
        driver.get("https://dienmaycholon.com/");
        driver.manage().window().setSize(new Dimension(1552, 840));
        
        js.executeScript("document.querySelector(\"#user_box .icon_register\").click()");
        
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("toiyeu4nguoi2003");
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys("toiyeu4nguoi2003"); // Email sai
        driver.findElement(By.id("password")).click(); // Click ra ngoài
        
        Thread.sleep(1000);
        assertThat(driver.findElement(By.cssSelector(".text_error")).getText(), is("Email không đúng định dạng"));
        Thread.sleep(2000);
    }
}