import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ReadingDataFromExcel {
    
    public static void main(String[] args) {
        String Excelpath = System.getProperty("user.dir") + "\\files\\Frnds.xlsx";
        System.out.println("ExcelPath is " + Excelpath);

        File file = new File(Excelpath);
        FileInputStream fis = null;
        XSSFWorkbook workbook = null;

        try {
            // Load file and workbook
            fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet("Sheet1");

            // Check if sheet exists
            if (sheet == null) {
                System.out.println("Error: Sheet not found! Please check the sheet name.");
                return;
            }

            // Get the number of rows
            int NoOfRows = sheet.getPhysicalNumberOfRows();
            if (NoOfRows == 0) {
                System.out.println("Error: The sheet is empty.");
                return;
            }

            // Get the number of columns (handle null rows)
            XSSFRow firstRow = sheet.getRow(0);
            if (firstRow == null) {
                System.out.println("Error: The first row is empty.");
                return;
            }
            int NoOfCells = firstRow.getLastCellNum(); // This can be -1

            // Read Excel file
            for (int r = 0; r < NoOfRows; r++) {
                XSSFRow row = sheet.getRow(r);
                if (row == null) continue; // Skip null rows

                for (int c = 0; c < NoOfCells; c++) {
                    XSSFCell cell = row.getCell(c);
                    if (cell == null) continue; // Skip null cells

                    CellType cellType = cell.getCellType();

                    switch (cellType) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                            System.out.print("UNKNOWN\t");
                    }
                }
                System.out.println(); // Move to the next line after each row
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found! Please check the file path.");
        } catch (IOException e) {
            System.out.println("Error: Unable to read the Excel file.");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        
        try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
