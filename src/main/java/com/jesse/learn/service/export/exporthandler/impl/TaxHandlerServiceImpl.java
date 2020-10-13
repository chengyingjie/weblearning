package com.jesse.learn.service.export.exporthandler.impl;

import com.jesse.learn.dto.CellData;
import com.jesse.learn.dto.ExportCommand;
import com.jesse.learn.enums.ExportTypeEnum;
import com.jesse.learn.service.export.exporthandler.ExportHandlerService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("tax_data")
public class TaxHandlerServiceImpl extends AbstractExportService implements ExportHandlerService {
    public TaxHandlerServiceImpl() {
    }

    public Boolean exportRecordByType(ExportCommand exportCommand) {
        Boolean exptRes = this.exportDataToExcel(exportCommand, ExportTypeEnum.TAX_DATA);
        return exptRes ? true : false;
    }

    protected List<String> getExcelTitles() {
        return Arrays.asList("税务号", "税务公司名", "税务公司成立时间", "税务公司法人");
    }

    protected int writeCellContentToExcel(HSSFSheet sheet, ExportCommand exportCommand, int curRowNum, List<String> excelTitles) {
        List<List<CellData>> itemList = this.getTaxCellDatas(excelTitles);
        Iterator var6 = itemList.iterator();

        while(var6.hasNext()) {
            List<CellData> items = (List)var6.next();
            ++curRowNum;
            HSSFRow currentRow = sheet.createRow(curRowNum);
            Map<String, String> itemPropertyMap = (Map)items.stream().collect(Collectors.toMap(CellData::getName, CellData::getValue));

            for(int i = 0; i < excelTitles.size(); ++i) {
                if (itemPropertyMap.containsKey(excelTitles.get(i))) {
                    try {
                        String cellValue = (String)itemPropertyMap.get(excelTitles.get(i));
                        currentRow.createCell(i).setCellValue(cellValue);
                    } catch (Exception var12) {
                        System.out.println("写单元格值到excel失败, item:" + (String)itemPropertyMap.get(excelTitles.get(i)));
                    }
                }
            }
        }

        return curRowNum;
    }

    private List<List<CellData>> getTaxCellDatas(List<String> excelTitles) {
        List<List<CellData>> result = new ArrayList();

        for(int i = 0; i < 10; ++i) {
            List<CellData> rowDatas = new ArrayList();

            for(int j = 0; j < excelTitles.size(); ++j) {
                CellData cell = new CellData();
                cell.setName((String)excelTitles.get(j));
                cell.setValue(String.valueOf(Math.round(Math.random() * 50.0D)));
                rowDatas.add(cell);
            }

            result.add(rowDatas);
        }

        return result;
    }
}
