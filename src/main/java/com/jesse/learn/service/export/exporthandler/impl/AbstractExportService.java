package com.jesse.learn.service.export.exporthandler.impl;

import com.jesse.learn.dto.ExportCommand;
import com.jesse.learn.enums.ExportTypeEnum;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.util.CollectionUtils;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractExportService {

    protected Boolean exportDataToExcel(ExportCommand exportCommand, ExportTypeEnum exportTypeEnum) {
        List<String> excelTitles = this.getExcelTitles();
        if (CollectionUtils.isEmpty(excelTitles)) {
            return false;
        } else {
            HSSFWorkbook workbook = new HSSFWorkbook();

            Boolean var6;
            try {
                HSSFSheet sheet = workbook.createSheet(exportTypeEnum.getDesc());
                HSSFRow firstRow = sheet.getRow(0);
                if (null == firstRow) {
                    firstRow = sheet.createRow(0);

                    for(int i = 0; i < excelTitles.size(); ++i) {
                        firstRow.createCell(i).setCellValue((String)excelTitles.get(i));
                    }
                }

                this.writeCellContentToExcel(sheet, exportCommand, 0, excelTitles);
                FileOutputStream outputStream = new FileOutputStream("/Users/chengyingjie/excelexport/" + exportTypeEnum.getName() + System.currentTimeMillis() + ".xls");
                workbook.write(outputStream);
                outputStream.flush();
                outputStream.close();
                this.exportExcelTemplate(excelTitles);
                return true;
            } catch (Exception var16) {
                System.out.println("export exception");
                var6 = false;
            } finally {
                try {
                    workbook.close();
                } catch (Exception var15) {
                    System.out.println("关闭HSSFWorkbook失败");
                }

            }

            return var6;
        }
    }

    private void exportExcelTemplate(List<String> excelTitles) {
        HSSFWorkbook workbook = new HSSFWorkbook();

        try {
            HSSFSheet sheet = workbook.createSheet(ExportTypeEnum.TAX_DATA.getDesc());
            HSSFRow firstRow = sheet.getRow(0);
            if (null == firstRow) {
                firstRow = sheet.createRow(0);

                for(int i = 0; i < excelTitles.size(); ++i) {
                    firstRow.createCell(i).setCellValue((String)excelTitles.get(i));
                }
            }

            String[] list = new String[]{"aaa", "bbb", "ccc", "ddd", "eee", "fff"};
            CellRangeAddressList regions = new CellRangeAddressList(0, 49, 0, 0);
            DVConstraint constraint = DVConstraint.createExplicitListConstraint(list);
            HSSFDataValidation data_validation = new HSSFDataValidation(regions, constraint);
            sheet.addValidationData(data_validation);
            FileOutputStream outputStream = null;
            outputStream = new FileOutputStream("/Users/chengyingjie/excelexport/template" + System.currentTimeMillis() + ".xls");
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception var10) {
            var10.printStackTrace();
        }

    }

    protected List<String> getExcelTitles() {
        return new ArrayList();
    }

    protected int writeCellContentToExcel(HSSFSheet sheet, ExportCommand exportCommand, int curRowNum, List<String> excelTitles) {
        return 0;
    }
}
