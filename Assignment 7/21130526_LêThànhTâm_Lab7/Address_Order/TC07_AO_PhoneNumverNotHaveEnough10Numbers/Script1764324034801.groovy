import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

WebUI.openBrowser('')

WebUI.navigateToUrl('https://dienmaycholon.com/dang-nhap')

WebUI.setText(findTestObject('Object Repository/Page_/input_Email hoc Tn ng nhp_username'), 'LeTana')

WebUI.setEncryptedText(findTestObject('Object Repository/Page_/input_Mt khu_password'), 'kGeVyl2U2R8=')

WebUI.click(findTestObject('Object Repository/Page_/button_Bn qun mt khu_btn_register'))

WebUI.click(findTestObject('Object Repository/Page_/a'))

WebUI.click(findTestObject('Object Repository/Page_/i_THM A CH MI_fas fa-plus'))

WebUI.setText(findTestObject('Object Repository/Page_/input_()_name'), 'Phạm Tiến Dũng')

WebUI.setText(findTestObject('Object Repository/Page_/input_()_phone'), '09123')

WebUI.click(findTestObject('Object Repository/Page_/input_()_phone'))

WebUI.setText(findTestObject('Object Repository/Page_/input_()_address'), '09 Nguyễn Bỉnh Khiêm')

WebUI.selectOptionByValue(findTestObject('Object Repository/Page_/select_()_cid_city'), '3', true)

WebUI.click(findTestObject('Object Repository/Page_/button_submit'))

WebUI.click(findTestObject('Object Repository/Page_/div_()_text-error'))

WebUI.click(findTestObject('Object Repository/Page_/a_1'))

WebUI.click(findTestObject('Object Repository/Page_/a'))

