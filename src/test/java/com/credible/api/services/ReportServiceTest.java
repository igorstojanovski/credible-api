package com.credible.api.services;


import com.credible.api.model.User;
import com.credible.api.model.ViewsReport;
import com.credible.api.repositories.ViewsReportRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    @Mock
    private ViewsReportRepository viewsReportRepository;
    private ReportService reportService;

    @Before
    public void before() {
        reportService = new ReportService(viewsReportRepository);
    }

    @Test
    public void shouldGetViewsReport() {
        ViewsReport viewsReport = new ViewsReport();
        viewsReport.setId(33L);

        User user = new User();
        user.setId(2L);

        viewsReport.setOwner(user);

        when(viewsReportRepository.findByOwnerId(1L)).thenReturn(viewsReport);

        ViewsReport viewsReportResult = reportService.getViewsReport(1L);
        assertThat(viewsReport).isEqualTo(viewsReportResult);
    }
}
