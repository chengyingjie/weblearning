package com.jesse.learn.service.export;

import com.jesse.learn.dto.ExportCommand;

public interface ExportService {
    Boolean createExportRecord(ExportCommand exportCommand);
}
