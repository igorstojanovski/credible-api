package com.credible.api.resources;

import com.credible.api.model.Story;
import com.credible.api.model.requests.StoryCreationRequest;
import com.credible.api.services.StoryService;
import com.credible.api.services.ViewEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user/{userId}/story")
public class StoryController {

    @Autowired
    private StoryService storyService;
    @Autowired
    private ViewEventService viewEventService;

    @PostMapping
    @PreAuthorize("@securityService.isOwner(#userId)")
    public ResponseEntity<Story> createStory(@PathVariable Long userId, @RequestBody StoryCreationRequest storyCreationRequest) {
        Story createdStory = storyService.createStory(storyCreationRequest, userId);
        return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("@securityService.isOwner(#userId)")
    public ResponseEntity<Story> updateStory(@PathVariable Long userId, @RequestBody StoryCreationRequest storyCreationRequest) {
        Story createdStory = storyService.updateStory(userId, storyCreationRequest);
        return new ResponseEntity<>(createdStory, HttpStatus.OK);
    }

    @GetMapping
    @PreAuthorize("@securityService.isCodeValid(#userId)")
    public ResponseEntity<Story> getStory(@PathVariable Long userId, HttpServletRequest request) {

        Story story = storyService.getStory(userId);
        if (story != null) {
            addViewEvent(userId, request);
        }
        return new ResponseEntity<>(story, HttpStatus.OK);
    }

    private void addViewEvent(@PathVariable Long userId, HttpServletRequest request) {
        String code = request.getHeader("code");
        if (code != null) {
            viewEventService.createEvent(code, userId);
        }
    }
}
