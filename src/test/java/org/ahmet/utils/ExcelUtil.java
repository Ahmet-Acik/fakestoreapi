package org.ahmet.utils;

     import org.apache.poi.ss.usermodel.*;

     import java.io.FileInputStream;
     import java.io.FileOutputStream;
     import java.io.IOException;
     import java.util.ArrayList;
     import java.util.HashMap;
     import java.util.List;
     import java.util.Map;

     /**
      * Utility class for reading and writing Excel files.
      * Supports both `.xls` and `.xlsx` formats.
      */
     public class ExcelUtil {

         private final Sheet workSheet;
         private final Workbook workBook;
         private final String filePath;

         /**
          * Constructor to initialize ExcelUtil with the file path and sheet name.
          *
          * @param filePath  Path to the Excel file.
          * @param sheetName Name of the sheet to work with.
          */
         public ExcelUtil(String filePath, String sheetName) {
             this.filePath = filePath;
             try (FileInputStream excelFile = new FileInputStream(filePath)) {
                 this.workBook = WorkbookFactory.create(excelFile);
                 this.workSheet = workBook.getSheet(sheetName);

                 if (this.workSheet == null) {
                     throw new IllegalArgumentException("Sheet \"" + sheetName + "\" does not exist in the file.");
                 }
             } catch (IOException e) {
                 throw new RuntimeException("Failed to load Excel file: " + filePath, e);
             }
         }

         /**
          * Retrieves the data from a specific cell.
          *
          * @param rowNum Row number (0-based index).
          * @param colNum Column number (0-based index).
          * @return Cell data as a String.
          */
         public String getCellData(int rowNum, int colNum) {
             try {
                 Row row = workSheet.getRow(rowNum);
                 if (row == null) {
                     return "";
                 }
                 Cell cell = row.getCell(colNum);
                 return cell != null ? cell.toString() : "";
             } catch (Exception e) {
                 throw new RuntimeException("Failed to get cell data at row " + rowNum + ", column " + colNum, e);
             }
         }

         /**
          * Retrieves all data from the sheet as a 2D array.
          *
          * @return 2D array of sheet data.
          */
         public String[][] getDataArray() {
             int rows = rowCount();
             int cols = columnCount();
             String[][] data = new String[rows][cols];

             for (int i = 0; i < rows; i++) {
                 for (int j = 0; j < cols; j++) {
                     data[i][j] = getCellData(i, j);
                 }
             }
             return data;
         }

         /**
          * Retrieves all data from the sheet as a 2D array, excluding the first row.
          *
          * @return 2D array of sheet data without the first row.
          */
         public String[][] getDataArrayWithoutFirstRow() {
             int rows = rowCount() - 1;
             int cols = columnCount();
             String[][] data = new String[rows][cols];

             for (int i = 1; i < rowCount(); i++) {
                 for (int j = 0; j < cols; j++) {
                     data[i - 1][j] = getCellData(i, j);
                 }
             }
             return data;
         }

         /**
          * Retrieves all data from the sheet as a list of maps.
          * Each map represents a row, with column names as keys.
          *
          * @return List of maps containing sheet data.
          */
         public List<Map<String, String>> getDataList() {
             List<String> columns = getColumnNames();
             List<Map<String, String>> data = new ArrayList<>();

             for (int i = 1; i < rowCount(); i++) {
                 Row row = workSheet.getRow(i);
                 if (row == null) {
                     continue;
                 }
                 Map<String, String> rowMap = new HashMap<>();

                 for (Cell cell : row) {
                     int columnIndex = cell.getColumnIndex();
                     rowMap.put(columns.get(columnIndex), cell.toString());
                 }
                 data.add(rowMap);
             }
             return data;
         }

         /**
          * Retrieves the column names from the first row of the sheet.
          *
          * @return List of column names.
          */
         public List<String> getColumnNames() {
             List<String> columns = new ArrayList<>();
             Row headerRow = workSheet.getRow(0);

             for (Cell cell : headerRow) {
                 columns.add(cell.toString());
             }
             return columns;
         }

         /**
          * Sets the value of a specific cell.
          *
          * @param value  Value to set.
          * @param rowNum Row number (0-based index).
          * @param colNum Column number (0-based index).
          */
         public void setCellData(String value, int rowNum, int colNum) {
             try {
                 Row row = workSheet.getRow(rowNum);
                 if (row == null) {
                     row = workSheet.createRow(rowNum);
                 }

                 Cell cell = row.getCell(colNum);
                 if (cell == null) {
                     cell = row.createCell(colNum);
                 }
                 cell.setCellValue(value);

                 try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                     workBook.write(fileOut);
                 }
             } catch (IOException e) {
                 throw new RuntimeException("Failed to set cell data at row " + rowNum + ", column " + colNum, e);
             }
         }

         /**
          * Sets the value of a specific cell by column name.
          *
          * @param value      Value to set.
          * @param columnName Column name.
          * @param rowNum     Row number (0-based index).
          */
         public void setCellData(String value, String columnName, int rowNum) {
             int colNum = getColumnNames().indexOf(columnName);
             if (colNum == -1) {
                 throw new IllegalArgumentException("Column \"" + columnName + "\" does not exist.");
             }
             setCellData(value, rowNum, colNum);
         }

         /**
          * Retrieves the total number of columns in the sheet.
          *
          * @return Number of columns.
          */
         public int columnCount() {
             return workSheet.getRow(0).getLastCellNum();
         }

         /**
          * Retrieves the total number of rows in the sheet.
          *
          * @return Number of rows.
          */
         public int rowCount() {
             return workSheet.getLastRowNum() + 1;
         }
     }