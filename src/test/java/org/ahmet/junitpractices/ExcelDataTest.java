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
}