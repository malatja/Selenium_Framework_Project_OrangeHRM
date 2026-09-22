package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    private static final String TEST_DATA_ROOT =
            "src/test/resources/testdata";

    public static String[][] getExcelData(
            String workbookName,
            String sheetName)
            throws IOException {

        String filePath = Paths.get(
                TEST_DATA_ROOT,
                workbookName
        ).toString();

        try (FileInputStream file = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(file)) {

            XSSFSheet sheet = workbook.getSheet(sheetName.trim().toLowerCase());

            if (sheet == null) {

                System.out.println("Available sheets:");

                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    System.out.println("- [" + workbook.getSheetName(i) + "]");
                }

                throw new IllegalArgumentException(
                    "Sheet '" + sheetName.trim() +
                    "' not found in " + workbookName);
            }

            int rowCount = sheet.getPhysicalNumberOfRows();

            if (rowCount < 2) {
                return new String[0][0];
            }

            int colCount = sheet.getRow(0).getLastCellNum();

            String[][] data = new String[rowCount -1][colCount];

            DataFormatter formatter = new DataFormatter();

            for (int i = 1; i < rowCount; i++) {

                Row row = sheet.getRow(i);

                if (row == null) {

                    for (int j = 0; j < colCount; j++) {
                        data[i - 1][j] = "";
                    }

                    continue;
                }

                for (int j = 0; j < colCount; j++) {

                    Cell cell = row.getCell(
                            j,
                            Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                    data[i - 1][j] =
                            formatter.formatCellValue(cell).trim();
                }
            }

            return data;
        }
    }
}