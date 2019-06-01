package com.credible.api.services;

import com.credible.api.model.User;
import com.credible.api.model.ViewEvent;
import com.credible.api.repositories.ViewEventRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ViewEventServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private ViewEventRepository repository;
    @Mock
    private UserService userService;
    @Mock
    private ViewsReportService viewsReportService;
    private ViewEventService viewEventService;
    private User owner;
    private User visitor;

    private static ViewEvent getViewEvent(Long id, User owner, User visitor) {
        ViewEvent viewEvent = new ViewEvent();
        viewEvent.setVisitor(visitor);
        viewEvent.setOwner(owner);
        viewEvent.setId(id);
        return viewEvent;
    }

    @Before
    public void before() {
        viewEventService = new ViewEventService(repository, userService, viewsReportService);

        owner = new User();
        owner.setId(1L);
        visitor = new User();
        visitor.setId(2L);
    }

    @Test
    public void shouldCreateEvent() {
        when(userService.getUserByCode("12345")).thenReturn(Optional.of(owner));
        when(userService.get(2L)).thenReturn(Optional.of(visitor));
        when(repository.save(any(ViewEvent.class))).thenReturn(getViewEvent(1L, owner, visitor));

        ViewEvent viewEvent = viewEventService.createEvent("12345", 2L);

        assertThat(viewEvent.getId()).isEqualTo(1L);
        assertThat(viewEvent.getOwner()).isEqualTo(owner);
    }

    @Test
    public void shouldThrowWhenCodeMissing() {
        expectedException.expect(DataException.class);
        viewEventService.createEvent("12345", 2L);
    }

    @Test
    public void shouldThrowWhenVisitorIdMissing() {
        expectedException.expect(DataException.class);
        when(userService.getUserByCode("12345")).thenReturn(Optional.of(owner));
        viewEventService.createEvent("12345", 2L);
    }
}
