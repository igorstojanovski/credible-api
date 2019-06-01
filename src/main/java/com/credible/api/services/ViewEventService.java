package com.credible.api.services;

import com.credible.api.model.User;
import com.credible.api.model.ViewEvent;
import com.credible.api.repositories.ViewEventRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ViewEventService {

    private ViewEventRepository repository;
    private UserService userService;
    private ViewsReportService viewsReportService;

    ViewEventService(ViewEventRepository repository, UserService userService,
                     ViewsReportService viewsReportService) {
        this.repository = repository;
        this.userService = userService;
        this.viewsReportService = viewsReportService;
    }

    public ViewEvent createEvent(String userCode, Long visitorId) {
        User user = userService.getUserByCode(userCode)
            .orElseThrow(() -> new DataException("No user with the given code."));
        User visitor = userService.get(visitorId)
            .orElseThrow(() -> new DataException("No visitor user with the given ID."));

        ViewEvent viewEvent = new ViewEvent();
        viewEvent.setOwner(user);
        viewEvent.setVisitor(visitor);
        viewEvent.setTimestamp(new Date());

        ViewEvent save = repository.save(viewEvent);
        viewsReportService.updateViewsReport(user);
        return save;
    }

}
