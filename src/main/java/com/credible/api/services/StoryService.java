package com.credible.api.services;

import com.credible.api.model.Story;
import com.credible.api.model.requests.StoryCreationRequest;
import com.credible.api.repositories.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoryService {

    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserService userService;

    public Story createStory(StoryCreationRequest storyCreationRequest, Long userId) {
        Story story = new Story();
        story.setUser(userService.get(userId).orElseThrow());
        story.setText(storyCreationRequest.getStory());
        return storyRepository.save(story);
    }

    public Story getStory(Long userId) {
        return storyRepository.findByUserId(userId);
    }

    public Story updateStory(Long userId, StoryCreationRequest storyCreationRequest) {
        Story story = storyRepository.findByUserId(userId);
        story.setText(storyCreationRequest.getStory());
        return storyRepository.save(story);
    }
}
