package com.credible.api.resources;

import com.credible.api.model.ViewsReport;
import com.credible.api.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    private ReportService reportService;

    @Autowired
    ReportsController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/views-report")
    @PreAuthorize("@securityService.isOwner(#userId)")
    public ResponseEntity<ViewsReport> getViewsReport(@RequestParam Long userId) {
        ViewsReport report = reportService.getViewsReport(userId);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

}
