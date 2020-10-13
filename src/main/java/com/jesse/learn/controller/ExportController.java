package com.jesse.learn.controller;

import com.jesse.learn.dto.ExportCommand;
import com.jesse.learn.service.export.ExportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ExportController {
    @Resource
    private ExportService exportService;

    public ExportController() {
    }

    @GetMapping({"/data/export/get"})
    public String exportData(ExportCommand exportCommand) {
        this.exportService.createExportRecord(exportCommand);
        return "success";
    }
}
