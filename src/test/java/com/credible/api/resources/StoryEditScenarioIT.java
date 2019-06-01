package com.credible.api.resources;


import com.credible.SecureApplication;
import com.credible.api.model.Story;
import com.credible.api.model.User;
import com.credible.api.model.requests.StoryCreationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecureApplication.class)
@AutoConfigureMockMvc
public class StoryEditScenarioIT {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetExistingUserOnLogin() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n" +
                "\t\"bankId\":\"198403216370\"\n" +
                "}"))
            .andDo(print())
            .andReturn();
        String refId = result.getResponse().getHeader("refId");

        String token = mockMvc.perform(MockMvcRequestBuilders
            .post("/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\n" +
                "\t\"refId\":\"" + refId + "\"\n" +
                "}"))
            .andDo(print())
            .andReturn().getResponse().getHeader("Authorization");

        String userContent = mockMvc.perform(MockMvcRequestBuilders
            .get("/api/user")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        User user = OBJECT_MAPPER.readValue(userContent, User.class);
        System.out.println(user.getId());

        StoryCreationRequest storyCreationRequest = new StoryCreationRequest();
        storyCreationRequest.setStory("My real life story.");

        String storyCreationResponse = mockMvc.perform(MockMvcRequestBuilders
            .post("/api/user/" + user.getId() + "/story")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(OBJECT_MAPPER.writeValueAsString(storyCreationRequest)))
            .andDo(print())
            .andReturn().getResponse().getContentAsString();

        Story story = OBJECT_MAPPER.readValue(storyCreationResponse, Story.class);

        assertThat(story.getText()).isEqualTo("My real life story.");
        assertThat(story.getId()).isNotNull();

        storyCreationRequest.setStory("My real life story. Edited.");
        String storyEditResponse = mockMvc.perform(MockMvcRequestBuilders
            .put("/api/user/" + user.getId() + "/story")
            .header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON)
            .content(OBJECT_MAPPER.writeValueAsString(storyCreationRequest)))
            .andDo(print())
            .andReturn().getResponse().getContentAsString();

        story = OBJECT_MAPPER.readValue(storyEditResponse, Story.class);
        assertThat(story.getText()).isEqualTo("My real life story. Edited.");
        assertThat(story.getId()).isNotNull();
    }
}
