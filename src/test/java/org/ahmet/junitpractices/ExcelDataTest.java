package org.ahmet.junitpractices;

import org.ahmet.utils.ExcelUtil;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class ExcelDataTest {

    @Test
    public void testExcelData() {
        ExcelUtil excelUtil = new ExcelUtil("src/test/resources/Library.xlsx", "Library1");

        for (Map<String, String> row : excelUtil.getDataList()) {
            System.out.println("Row: " + row);
        }
    }

    @Test
    public void testExcelData_Meaningful_Student_List() {
        ExcelUtil excelUtil = new ExcelUtil("src/test/resources/Meaningful_Student_List.xlsx", "MeaningfulStudentList");

        for (Map<String, String> row : excelUtil.getDataList()) {
            System.out.println("Row: " + row);
        }
    }
}