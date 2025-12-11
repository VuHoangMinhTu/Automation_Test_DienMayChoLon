from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains
import unittest, time

# ==============================================================================
# PHẦN 1: CÁC TEST CASE ĐỔI MẬT KHẨU (HÀM ĐỘC LẬP)
# ==============================================================================

# Hàm login dùng chung cho tất cả Test Case
def login(driver, wait):
    driver.get("https://dienmaycholon.com/dang-nhap")
    driver.maximize_window()

    wait.until(EC.visibility_of_element_located((By.NAME, "username"))).send_keys(
        "phamhoangtien832003@gmail.com"
    )
    driver.find_element(By.ID, "password").send_keys("12345678")
    driver.find_element(By.CSS_SELECTOR, ".btn_register").click()

    # Chuyển đến trang Đổi mật khẩu
    try:
        # Cần đảm bảo element 'Đổi mật khẩu' có thể click được sau khi đăng nhập
        wait.until(EC.element_to_be_clickable((By.LINK_TEXT, "Đổi mật khẩu"))).click()
    except:
        print("Không tìm thấy link/button 'Đổi mật khẩu' sau khi đăng nhập. Bỏ qua TC Đổi Mật Khẩu.")
        raise

# ============ TC1 ============
def TC1_change_password_success():
    driver = webdriver.Chrome()
    wait = WebDriverWait(driver, 15)

    print("\n=== TC1: Đổi mật khẩu thành công ===")
    try:
        login(driver, wait)

        wait.until(EC.visibility_of_element_located((By.NAME, "oldpassword"))).send_keys("12345678")
        driver.find_element(By.NAME, "password").send_keys("12345678")
        driver.find_element(By.NAME, "repassword").send_keys("12345678")
        driver.find_element(By.CSS_SELECTOR, ".btn_confirm").click()

        time.sleep(2)
        print("✅ TC1 Passed!")

    except Exception as e:
        print("❌ TC1 Failed:", e)

    finally:
        driver.quit()


# ============ TC2 ============
def TC2_wrong_new_password():
    driver = webdriver.Chrome()
    wait = WebDriverWait(driver, 15)

    print("\n=== TC2: Sai mật khẩu mới ===")
    try:
        login(driver, wait)

        driver.find_element(By.NAME, "oldpassword").send_keys("12345678")
        driver.find_element(By.NAME, "password").send_keys("123")
        driver.find_element(By.NAME, "repassword").send_keys("123")
        driver.find_element(By.CSS_SELECTOR, ".btn_confirm").click()

        time.sleep(2)
        print("⚠️ TC2 chạy xong – Kiểm tra thông báo lỗi!")

    except Exception as e:
        print("❌ TC2 Failed:", e)

    finally:
        driver.quit()


# ============ TC3 ============
def TC3_wrong_old_password():
    driver = webdriver.Chrome()
    wait = WebDriverWait(driver, 15)

    print("\n=== TC3: Sai mật khẩu cũ ===")
    try:
        login(driver, wait)

        driver.find_element(By.NAME, "oldpassword").send_keys("1111111111")
        driver.find_element(By.NAME, "password").send_keys("123456")
        driver.find_element(By.NAME, "repassword").send_keys("123456")
        driver.find_element(By.CSS_SELECTOR, ".btn_confirm").click()

        time.sleep(2)
        print("⚠️ TC3 chạy xong – Mật khẩu cũ sai!")

    except Exception as e:
        print("❌ TC3 Failed:", e)

    finally:
        driver.quit()


# ============ TC4 ============
def TC4_mismatch_repassword():
    driver = webdriver.Chrome()
    wait = WebDriverWait(driver, 15)

    print("\n=== TC4: Nhập lại mật khẩu không khớp ===")
    try:
        login(driver, wait)

        driver.find_element(By.NAME, "oldpassword").send_keys("12345678")
        driver.find_element(By.NAME, "password").send_keys("123456")
        driver.find_element(By.NAME, "repassword").send_keys("111111")
        driver.find_element(By.CSS_SELECTOR, ".btn_confirm").click()

        time.sleep(2)
        print("⚠️ TC4 chạy xong – Mật khẩu nhập lại không khớp!")

    except Exception as e:
        print("❌ TC4 Failed:", e)

    finally:
        driver.quit()


# ============ TC5 ============
def TC5_short_password():
    driver = webdriver.Chrome()
    wait = WebDriverWait(driver, 15)

    print("\n=== TC5: Mật khẩu mới quá ngắn ===")
    try:
        login(driver, wait)

        driver.find_element(By.NAME, "oldpassword").send_keys("12345678")
        driver.find_element(By.NAME, "password").send_keys("12")
        driver.find_element(By.NAME, "repassword").send_keys("12")
        driver.find_element(By.CSS_SELECTOR, ".btn_confirm").click()

        time.sleep(2)
        print("⚠️ TC5 chạy xong – Mật khẩu mới quá ngắn!")

    except Exception as e:
        print("❌ TC5 Failed:", e)

    finally:
        driver.quit()

# ==============================================================================
# PHẦN 2: CÁC TEST CASE LỌC SẢN PHẨM (SỬ DỤNG unittest)
# ==============================================================================

class FilterTests(unittest.TestCase):

    def setUp(self):
        service = Service()
        self.driver = webdriver.Chrome(service=service)
        self.driver.maximize_window()
        self.driver.implicitly_wait(10)
        self.wait = WebDriverWait(self.driver, 20)
        self.actions = ActionChains(self.driver)

    # Mở trang + mở bộ lọc
    def open_filter(self):
        driver = self.driver
        driver.get("https://dienmaycholon.com/tu-khoa/tivi")

        # mở bộ lọc nếu ẩn
        try:
            filter_btn = self.wait.until(
                EC.element_to_be_clickable((By.XPATH, "//div[contains(text(),'Bộ lọc')]"))
            )
            filter_btn.click()
            time.sleep(1)
        except:
            pass  # nếu đã mở sẵn

    # Hàm scroll xuống khu vực nhập giá
    def scroll_to_price_section(self):
        elm_start = self.wait.until(EC.presence_of_element_located((By.ID, "elm_start")))
        self.driver.execute_script("arguments[0].scrollIntoView({block:'center'});", elm_start)
        time.sleep(1)

    # Click "Xem kết quả"
    def click_view_result(self):
        view_btn = self.wait.until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, "a.btn-filter-readmore"))
        )
        view_btn.click()
        time.sleep(2)

    # TC 1 – giá đề xuất 5–7 triệu
    def test_filter_1_price_suggested(self):
        self.open_filter()
        price_btn = self.wait.until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, "a.tu-5-7-trieu"))
        )
        price_btn.click()
        self.click_view_result()
        print("✓ TC_Filter_1 OK")

    # TC 2 – nhập giá 5–10 triệu
    def test_filter_2_price_manual(self):
        self.open_filter()
        self.scroll_to_price_section()

        self.driver.find_element(By.ID, "elm_start").clear()
        self.driver.find_element(By.ID, "elm_start").send_keys("5000000")

        self.driver.find_element(By.ID, "elm_end").clear()
        self.driver.find_element(By.ID, "elm_end").send_keys("10000000")

        search_btn = self.wait.until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, "a.price_desire-button"))
        )
        search_btn.click()
        time.sleep(1)

        self.click_view_result()
        print("✓ TC_Filter_2 OK")

    # TC 3 – nhập giá không hợp lệ
    def test_filter_3_price_invalid(self):
        self.open_filter()
        self.scroll_to_price_section()

        self.driver.find_element(By.ID, "elm_start").clear()
        self.driver.find_element(By.ID, "elm_start").send_keys("15000000")

        self.driver.find_element(By.ID, "elm_end").clear()
        self.driver.find_element(By.ID, "elm_end").send_keys("10000000")

        search_btn = self.wait.until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, "a.price_desire-button"))
        )
        search_btn.click()
        time.sleep(1)

        self.click_view_result()
        print("✓ TC_Filter_3 OK")

    # TC 4 – lọc Samsung
    def test_filter_4_brand(self):
        self.open_filter()
        samsung_btn = self.wait.until(
            EC.element_to_be_clickable((By.XPATH, "//a[@title='Samsung']"))
        )
        samsung_btn.click()
        self.click_view_result()
        print("✓ TC_Filter_4 OK")

    # TC 5 – Samsung + LG
    def test_filter_5_multi_brand(self):
        """TC_Filter_5: Lọc đa thương hiệu (Samsung + LG)"""
        self.open_filter()

        # Click Samsung
        samsung = self.wait.until(
            EC.element_to_be_clickable((By.XPATH, "//a[@title='Samsung']"))
        )
        self.driver.execute_script("arguments[0].scrollIntoView({block: 'center'});", samsung)
        time.sleep(0.5)
        samsung.click()
        time.sleep(1)  # DOM có thể reload

        # Sau khi click Samsung, phải find lại LG (tránh stale element)
        lg = self.wait.until(
            EC.element_to_be_clickable((By.XPATH, "//a[@title='LG']"))
        )
        self.driver.execute_script("arguments[0].scrollIntoView({block: 'center'});", lg)
        time.sleep(0.5)
        lg.click()
        time.sleep(1)

        self.click_view_result()
        print("✓ TC_Filter_5 OK")

    # TC 6 – giá + thương hiệu
    def test_filter_6_price_and_brand(self):
        self.open_filter()
        
        # 1. Click chọn giá. Hành động này GÂY CẬP NHẬT DOM.
        price_btn = self.wait.until(
            EC.element_to_be_clickable((By.CSS_SELECTOR, "a.tu-5-7-trieu"))
        )
        price_btn.click()
        time.sleep(1) # Thêm thời gian chờ ngắn để DOM ổn định
        
        # 2. TÌM LẠI và click Samsung (vì DOM đã bị reload)
        samsung_btn = self.wait.until(
            EC.element_to_be_clickable((By.XPATH, "//a[@title='Samsung']"))
        )
        samsung_btn.click()
        
        self.click_view_result()
        print("✓ TC_Filter_6 OK")

   # TC 7 – Reset lọc (phải chọn thương hiệu + giá trước)
    def test_filter_7_reset(self):
        self.open_filter()

        # scroll xuống chọn giá
        self.scroll_to_price_section()
        self.driver.find_element(By.ID, "elm_start").clear()
        self.driver.find_element(By.ID, "elm_start").send_keys("3000000")
        self.driver.find_element(By.ID, "elm_end").clear()
        self.driver.find_element(By.ID, "elm_end").send_keys("10000000")
        
        # Click nút áp dụng giá, hành động này GÂY CẬP NHẬT DOM
        self.driver.find_element(By.CSS_SELECTOR, "a.price_desire-button").click()
        
        # Thêm thời gian chờ TĨNH để DOM cập nhật sau khi áp dụng giá
        time.sleep(2) 

        # TÌM LẠI và chọn Samsung
        # Phải TÌM LẠI phần tử vì DOM đã thay đổi do thao tác áp dụng giá
        samsung_btn = self.wait.until(
            EC.element_to_be_clickable((By.XPATH, "//a[@title='Samsung']"))
        )
        samsung_btn.click()

        # nút Xóa tất cả chỉ hiện sau khi có filter
        reset_btn = self.wait.until(
            EC.element_to_be_clickable((By.XPATH, "//span[text()='Xóa tất cả']"))
        )
        reset_btn.click()

        self.click_view_result()
        print("✓ TC_Filter_7 OK")

    def tearDown(self):
        self.driver.quit()

# ==============================================================================
# PHẦN 3: THỰC THI CHƯƠNG TRÌNH
# ==============================================================================

if __name__ == "__main__":
    
    # 1. Chạy các Test Case Đổi Mật Khẩu (hàm độc lập)
    print("===============================================")
    print("🔑 BẮT ĐẦU CHẠY TEST CASE ĐỔI MẬT KHẨU (hàm độc lập)")
    print("===============================================")
    try:
        TC1_change_password_success()
        TC2_wrong_new_password()
        TC3_wrong_old_password()
        TC4_mismatch_repassword()
        TC5_short_password()
    except Exception as e:
        print(f"Lỗi khi chạy TC Đổi Mật Khẩu: {e}")
    
    # 2. Chạy các Test Case Lọc Sản Phẩm (unittest)
    print("\n==============================================")
    print("🚀 BẮT ĐẦU CHẠY TEST CASE LỌC SẢN PHẨM (unittest)")
    print("==============================================")
    # Sử dụng argv và exit=False để ngăn unittest.main thoát chương trình
    unittest.main(argv=['first-arg-is-ignored'], exit=False)
    
    print("\n=== 🎉 Tất cả test case đã chạy xong! ===")