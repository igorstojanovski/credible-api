package com.credible.api.services;

import com.credible.api.model.User;
import com.credible.api.model.ViewsReport;
import com.credible.api.repositories.ViewsReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ViewsReportService {

    @Autowired
    private ViewsReportRepository reportRepository;

    void updateViewsReport(User user) {
        ViewsReport report = reportRepository.findByOwnerId(user.getId());

        if (report == null) {
            report = new ViewsReport();
            report.setOwner(user);
            report.setViewsCount(1);
        } else {
            report.setViewsCount(report.getViewsCount() + 1);
        }

        reportRepository.save(report);
    }
}
