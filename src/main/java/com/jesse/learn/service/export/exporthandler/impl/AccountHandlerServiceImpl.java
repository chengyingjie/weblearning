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

@Service("account_data")
public class AccountHandlerServiceImpl extends AbstractExportService implements ExportHandlerService {
    public AccountHandlerServiceImpl() {
    }

    public Boolean exportRecordByType(ExportCommand exportCommand) {
        Boolean exptRes = this.exportDataToExcel(exportCommand, ExportTypeEnum.ACCOUNT_DATA);
        return exptRes ? true : false;
    }

    protected List<String> getExcelTitles() {
        return Arrays.asList("开户号", "开户名", "开户时间", "开户法人");
    }

    protected int writeCellContentToExcel(HSSFSheet sheet, ExportCommand exportCommand, int curRowNum, List<String> excelTitles) {
        List<List<CellData>> itemList = this.getAccountCellDatas(excelTitles);
        Iterator var6 = itemList.iterator();

        while (var6.hasNext()) {
            List<CellData> items = (List) var6.next();
            ++curRowNum;
            HSSFRow curRow = sheet.createRow(curRowNum);
            Map<String, String> itemPropertyMap = (Map) items.stream().collect(Collectors.toMap(CellData::getName, CellData::getValue));

            for (int i = 0; i < excelTitles.size(); ++i) {
                if (itemPropertyMap.containsKey(excelTitles.get(i))) {
                    try {
                        curRow.createCell(i).setCellValue((String) itemPropertyMap.get(excelTitles.get(i)));
                    } catch (Exception var12) {
                        System.out.println("写单元格值到excel失败, item:" + (String) itemPropertyMap.get(excelTitles.get(i)));
                    }
                }
            }
        }

        return curRowNum;
    }

    private List<List<CellData>> getAccountCellDatas(List<String> excelTitles) {
        List<List<CellData>> result = new ArrayList();

        for (int i = 0; i < 10; ++i) {
            List<CellData> rowDatas = new ArrayList();

            for (int j = 0; j < excelTitles.size(); ++j) {
                CellData cell = new CellData();
                cell.setName((String) excelTitles.get(j));
                cell.setValue(String.valueOf(Math.round(Math.random() * 100.0D)));
                rowDatas.add(cell);
            }

            result.add(rowDatas);
        }

        return result;
    }
}