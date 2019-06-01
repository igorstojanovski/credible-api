package com.credible.api.services;

import com.credible.api.model.ViewsReport;
import com.credible.api.repositories.ViewsReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ViewsReportRepository viewsReportRepository;

    public ReportService(ViewsReportRepository viewsReportRepository) {
        this.viewsReportRepository = viewsReportRepository;
    }

    public ViewsReport getViewsReport(Long userId) {
        return viewsReportRepository.findByOwnerId(userId);
    }
}
