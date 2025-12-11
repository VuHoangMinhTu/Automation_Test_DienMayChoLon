import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import static org.junit.jupiter.api.Assertions.*;

public class LeThanhTam_21130526_Lab8 {
    private static WebDriver driver;
    private static JavascriptExecutor js;
    private boolean acceptNextAlert = true;

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "");
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
    }

    @AfterClass
    public static void tearDownClass() {
        driver.quit();
    }
    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    @Test
    public void testTC01AOCorrectInformation() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("LeTana");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Tam123@");
        driver.findElement(By.xpath("//form[@id='f_login']/div[4]/button/span")).click();
        driver.get("https://dienmaycholon.com/thong-tin-tai-khoan");
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
        driver.findElement(By.linkText("THÊM ĐỊA CHỈ MỚI")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Le Tien Quan");
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("0912312312");
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("06 Lê Thị Hồng Gâm");
        driver.findElement(By.id("cid_city")).click();
        new Select(driver.findElement(By.id("cid_city"))).selectByVisibleText("Cần Thơ (TP)");
        driver.findElement(By.name("is_default")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.linkText("Quản lý tài khoản")).click();
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
    }

    @Test
    public void testTC02AOChooseDefaultAddress() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap#dia-chi-cua-ban");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("LeTana");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Tam123@");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("https://dienmaycholon.com/thong-tin-tai-khoan");
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
        driver.findElement(By.linkText("THÊM ĐỊA CHỈ MỚI")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Le Tien Quan");
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("0384774118");
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("06 Lê Thị Hồng Gâm");
        driver.findElement(By.name("is_default")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.linkText("Quản lý tài khoản")).click();
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
    }
    @Test
    public void testTC03AONullName() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("LeTana");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Tam123@");
        driver.findElement(By.xpath("//form[@id='f_login']/div[4]/button/span")).click();
        driver.get("https://dienmaycholon.com/thong-tin-tai-khoan");
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
        driver.findElement(By.linkText("THÊM ĐỊA CHỈ MỚI")).click();
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("0384774118");
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("aaa");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//form[@id='f_address_user']/table/tbody/tr/td/div")).click();
    }
    @Test
    public void testTC04AONameOnlyNumber() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("LeTana");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Tam123@");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("https://dienmaycholon.com/thong-tin-tai-khoan");
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
        driver.findElement(By.linkText("THÊM ĐỊA CHỈ MỚI")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("0909342342345345");
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("4534534534");
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("09 Le Minh");
        driver.findElement(By.id("cid_city")).click();
        new Select(driver.findElement(By.id("cid_city"))).selectByVisibleText("Đắk Nông");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.linkText("Quản lý tài khoản")).click();
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
    }
    @Test
    public void testTC05AONameTooLong() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("LeTana");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Tam123@");
        driver.findElement(By.xpath("//form[@id='f_login']/div[4]/button/span")).click();
        driver.get("https://dienmaycholon.com/thong-tin-tai-khoan");
        acceptNextAlert = true;
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
        driver.findElement(By.xpath("//div[@id='user_info']/section/div/div/div[2]/div/div/ul/li/div[3]/a[2]/i")).click();
        assertEquals("Bạn muốn xoá địa chỉ này", closeAlertAndGetItsText());
        driver.findElement(By.linkText("THÊM ĐỊA CHỈ MỚI")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("0384774118");
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("06 Lê Thị Hồng Gâm");
        driver.findElement(By.id("cid_city")).click();
        new Select(driver.findElement(By.id("cid_city"))).selectByVisibleText("Đắk Lắk");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.linkText("Quản lý tài khoản")).click();
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
        driver.findElement(By.xpath("//div[@id='user_info']/section/div/div/div[2]/div/div/ul/li[3]/div[2]")).click();
    }
    @Test
    public void testTC06AOPhoneNumberIsNull() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("LeTana");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Tam123@");
        driver.findElement(By.xpath("//form[@id='f_login']/div[4]/button/span")).click();
        driver.get("https://dienmaycholon.com/thong-tin-tai-khoan");
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
        driver.findElement(By.linkText("THÊM ĐỊA CHỈ MỚI")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Le Tien Quan");
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("06 Lê Thị Hồng Gâm");
        driver.findElement(By.id("cid_city")).click();
        new Select(driver.findElement(By.id("cid_city"))).selectByVisibleText("Đồng Tháp");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.xpath("//form[@id='f_address_user']/table/tbody/tr/td[2]/div")).click();
    }
    @Test
    public void testTC07AOPhoneTooLongOrShort() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("LeTana");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Tam123@");
        driver.findElement(By.xpath("//form[@id='f_login']/div[4]/button/span")).click();
        driver.get("https://dienmaycholon.com/thong-tin-tai-khoan");
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
        driver.findElement(By.linkText("THÊM ĐỊA CHỈ MỚI")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Le Tien Quan");
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("0384774118");
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("03847");
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("06 Lê Thị Hồng Gâm");
        driver.findElement(By.id("cid_city")).click();
        new Select(driver.findElement(By.id("cid_city"))).selectByVisibleText("Điện Biên");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Le Tien Quan");
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("0438478932758923573845");
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("06 Lê Thị Hồng Gâm");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.findElement(By.linkText("Quản lý tài khoản")).click();
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
    }
    @Test
    public void testTC08AOPhoneNumberHaveWords() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("LeTana");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Tam123@");
        driver.findElement(By.xpath("//form[@id='f_login']/div[4]/button/span")).click();
        driver.get("https://dienmaycholon.com/thong-tin-tai-khoan");
        acceptNextAlert = true;
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
        driver.findElement(By.xpath("//div[@id='user_info']/section/div/div/div[2]/div/div/ul/li/div[3]/a[2]/i")).click();
        Assert.assertEquals("Bạn muốn xoá địa chỉ này", closeAlertAndGetItsText());
        driver.findElement(By.linkText("THÊM ĐỊA CHỈ MỚI")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Le Tien Quan");
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("áda123124345");
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("06 Lê Thị Hồng Gâm");
        driver.findElement(By.id("cid_city")).click();
        new Select(driver.findElement(By.id("cid_city"))).selectByVisibleText("Đắk Lắk");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }
    @Test
    public void testTC09AONotChooseCityName() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap");
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).clear();
        driver.findElement(By.name("username")).sendKeys("LeTana");
        driver.findElement(By.id("password")).click();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys("Tam123@");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        driver.get("https://dienmaycholon.com/thong-tin-tai-khoan");
        driver.findElement(By.linkText("Địa chỉ của bạn")).click();
        driver.findElement(By.xpath("//div[@id='user_info']/section/div/div/div[2]/div/div[2]/a/i")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("Le Tien Quan");
        driver.findElement(By.name("phone")).click();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("phone")).sendKeys("0384774118");
        driver.findElement(By.name("address")).click();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("address")).sendKeys("06 Lê Thị Hồng Gâm");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }
    @Test
    public void testTC10FPCorrectEmail() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap");
        driver.findElement(By.linkText("Bạn quên mật khẩu?")).click();
        driver.get("https://dienmaycholon.com/quen-mat-khau");
        driver.findElement(By.id("user")).click();
        driver.findElement(By.id("user")).clear();
        driver.findElement(By.id("user")).sendKeys("tamle7723@gmail.com");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }
    @Test
    public void testTC11FPIncorectEmail() throws Exception {
        driver.get("https://dienmaycholon.com/dang-nhap");
        driver.findElement(By.linkText("Bạn quên mật khẩu?")).click();
        driver.get("https://dienmaycholon.com/quen-mat-khau");
        driver.findElement(By.id("user")).click();
        driver.findElement(By.id("user")).clear();
        driver.findElement(By.id("user")).sendKeys("21130526@st.hcmuaf.edu.vn");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }
    @Test
    public void testTC12FPEmailNotValidate() throws Exception {
        driver.get("https://dienmaycholon.com/quen-mat-khau");
        driver.findElement(By.id("user")).click();
        driver.findElement(By.id("user")).clear();
        driver.findElement(By.id("user")).sendKeys("tamle7723@gmail.com");
        driver.findElement(By.id("user")).click();
        driver.findElement(By.id("user")).clear();
        driver.findElement(By.id("user")).sendKeys("tamle7723@com");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

}
