package Utils;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataLayer {

	public Object[][] realExcel(String file,String sheetName) throws IOException {

		XSSFWorkbook excel = new XSSFWorkbook(file);

		XSSFSheet sheet = excel.getSheet(sheetName);

		int rowSize = sheet.getLastRowNum();
		
		int columnSize = sheet.getRow(0).getPhysicalNumberOfCells();

		System.out.println("Row count : " + rowSize);

		System.out.println("Column count : " + columnSize);

		String xslData1;

		double xslNumericData;

		boolean xslbooleanData = false;

		XSSFCell checkData;

		Object[][] data = new Object[rowSize][columnSize];

		for (int i = 1; i <= rowSize; i++) {

			for (int j = 0; j <= columnSize; j++) {

				checkData = sheet.getRow(i).getCell(j);

				if (checkData != null) {

					CellType type = sheet.getRow(i).getCell(j).getCellTypeEnum();

					if (type == CellType.STRING) {

						xslData1 = sheet.getRow(i).getCell(j).getStringCellValue();
						data[i - 1][j] = xslData1;


					} else if (type == CellType.NUMERIC) {

						xslNumericData = sheet.getRow(i).getCell(j).getNumericCellValue();

						data[i - 1][j] = xslNumericData;
					} 
					else {
					}
				}
			}
		}
		return data;


	}

}
