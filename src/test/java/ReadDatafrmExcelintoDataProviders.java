import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;


import io.github.bonigarcia.wdm.WebDriverManager;

public class ReadDatafrmExcelintoDataProviders {

    @Test(dataProvider = "supplier")
    public void EmpDetails(String Username, String Password) {
        WebDriverManager.edgedriver().setup();
        WebDriver driver = new EdgeDriver();

        try {
        	WebDriverManager.edgedriver().clearDriverCache().setup();

            driver.get("https://tutorialsninja.com/demo/");
            driver.findElement(By.xpath("//span[text()='My Account']")).click();
            driver.findElement(By.xpath("(//a[text()='Login'])[1]")).click();
            driver.findElement(By.xpath("//input[@id='input-email']")).sendKeys(Username);
            driver.findElement(By.xpath("//input[@id='input-password']")).sendKeys(Password);
            driver.findElement(By.xpath("//input[@class='btn btn-primary']")).click();

            Assert.assertTrue(driver.findElement(By.xpath("//li//a[text()='Edit your account information']")).isDisplayed());
        } finally {
            driver.quit(); // Ensures the browser closes even if an assertion fails
        }
    }

    @DataProvider(name = "supplier")
    public Object[][] dataSupplier() {
        String ExcelPath = System.getProperty("user.dir") + "\\files\\Emp_Details.xlsx";
        LoggerUtil.logInfo(ReadDatafrmExcelintoDataProviders.class ,"Attempting to read Excel file: " + ExcelPath);

        File file = new File(ExcelPath);
        if (!file.exists()) {
            LoggerUtil.logInfo(ReadDatafrmExcelintoDataProviders.class, "Error: File does not exist at " + ExcelPath);
            return new Object[0][0]; // Prevents crashing if file is missing
        }

        FileInputStream fis = null;
        XSSFWorkbook workbook = null;
        Object[][] data = null;

        try {
            fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);

            // Debug: Print available sheets
            LoggerUtil.logInfo(ReadDatafrmExcelintoDataProviders.class, "Available sheets in the Excel file:");
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                LoggerUtil.logInfo(ReadDatafrmExcelintoDataProviders.class, workbook.getSheetName(i));
                
            }

            XSSFSheet sheet = workbook.getSheet("Login");
            if (sheet == null) {
                LoggerUtil.logInfo(ReadDatafrmExcelintoDataProviders.class, "Error: Sheet 'Login' not found.");
                return new Object[0][0]; // Prevent crashing if sheet is missing
            }

            int noOfRows = sheet.getPhysicalNumberOfRows();
            int noOfCells = sheet.getRow(0).getLastCellNum();

            data = new Object[noOfRows - 1][noOfCells];

            for (int r = 1; r < noOfRows; r++) {
                XSSFRow row = sheet.getRow(r);
                if (row == null) continue; // Skip null rows

                for (int c = 0; c < noOfCells; c++) {
                    XSSFCell cell = row.getCell(c, XSSFRow.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    switch (cell.getCellType()) {
                        case STRING:
                            data[r - 1][c] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            data[r - 1][c] = String.valueOf(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            data[r - 1][c] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        default:
                            data[r - 1][c] = "";
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Excel file not found.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error: Unable to read the Excel file.");
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null) workbook.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
                System.out.println("Error closing resources.");
            }
        }

        return data;
    }
}
