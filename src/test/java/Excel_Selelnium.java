import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_Selelnium {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		XSSFWorkbook Workbook = new XSSFWorkbook();
		XSSFSheet Sheet = Workbook.createSheet("SheetOne");
		
		Object [] [] data = { {"Emp_No","FirstName","LastName","Exp"} ,
							  {1,"Kiran","Gundra",10},
							  {2,"Krishna","Gundra",12},
							  {3,"Murthy","Gundra",14}};
		
		int noOfrows = data.length;
		int noOfcells = data[0].length;
		
		for(int r=0; r<noOfrows; r++) {
			
			XSSFRow row = Sheet.createRow(r);
			
			for(int c=0; c<noOfcells; c++) {
			
				XSSFCell cell = row.createCell(c);
				
				Object cellValue = data[r][c];
				
				if(cellValue instanceof String) {
					cell.setCellValue((String)cellValue);
				}
				else if(cellValue instanceof Integer) {
					cell.setCellValue((Integer)cellValue);
				}
				else if(cellValue instanceof Boolean) {
					cell.setCellValue((Boolean)cellValue);
					
					
				}
			}
	}
		
		File file = new File(System.getProperty("user.dir") + "/files/Employees.xlsx");
		FileOutputStream fos = new FileOutputStream(file);
		Workbook.write(fos);
		Workbook.close();
		
		LoggerUtil.logInfo(Excel_Selelnium.class, "Task completed");
        
    }
}