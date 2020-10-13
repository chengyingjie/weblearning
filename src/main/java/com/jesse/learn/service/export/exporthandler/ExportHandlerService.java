package com.jesse.learn.service.export.exporthandler;

import com.jesse.learn.dto.ExportCommand;

public interface ExportHandlerService {
    Boolean exportRecordByType(ExportCommand exportCommand);
}
