package com.jesse.learn.service.export;

import com.jesse.learn.annotation.LogNotion;
import com.jesse.learn.dto.ExportCommand;
import com.jesse.learn.enums.ExportTypeEnum;
import com.jesse.learn.service.export.exporthandler.ExportHandlerService;
import com.jesse.learn.utils.ApplicationContextHelper;
import org.springframework.stereotype.Service;

@Service
public class ExportServiceImpl implements ExportService {
    public ExportServiceImpl() {
    }

    @LogNotion
    public Boolean createExportRecord(ExportCommand exportCommand) {
        String beanKey = ExportTypeEnum.parseByValue(exportCommand.getExptType()).getName();
        ExportHandlerService exportHandlerService = (ExportHandlerService) ApplicationContextHelper.getBean(beanKey, ExportHandlerService.class);
        return exportHandlerService.exportRecordByType(exportCommand);
    }
}
