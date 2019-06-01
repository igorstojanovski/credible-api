package com.credible.api.resources;

import com.credible.api.model.ViewsReport;
import com.credible.api.services.ReportService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportsControllerTest {

    @Mock
    private ReportService reportService;
    private ReportsController reportsController;

    @Before
    public void before() {
        reportsController = new ReportsController(reportService);
    }

    @Test
    public void shouldReturnNumberOfViews() {
        ViewsReport viewsReport = new ViewsReport();
        viewsReport.setViewsCount(10);
        viewsReport.setId(33L);
        when(reportService.getViewsReport(1L)).thenReturn(viewsReport);

        ResponseEntity<ViewsReport> response = reportsController.getViewsReport(1L);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getViewsCount()).isEqualTo(10);
    }
}
