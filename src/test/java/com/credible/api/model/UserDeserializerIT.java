package com.credible.api.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

public class UserDeserializerIT {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldDeserializeStory() throws IOException {

        User user = new User();
        user.setId(1L);
        user.setName("Igor");

        Story story = new Story();
        story.setText("text");
        story.setUser(user);
        story.setId(33L);

        String serializedValue = objectMapper.writeValueAsString(story);
        System.out.println(serializedValue);

        Story deserialized = objectMapper.readValue(serializedValue, Story.class);
    }
}
