package com.credible.api.resources;

import com.credible.api.model.Story;
import com.credible.api.model.User;
import com.credible.api.model.requests.RegisterUserRequest;
import com.credible.api.model.requests.StoryCreationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class StoryAcceptanceTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private MockMvc mockMvc;
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    @Autowired
    private WebApplicationContext context;

    @Before
    public void beforeEach() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void shouldAddAndGetStory() throws Exception {

        String userContent = UserAcceptanceTest.registerUser(mockMvc, getUserRegisterRequest());
        User user = OBJECT_MAPPER.readValue(userContent, User.class);

        StoryCreationRequest storyRequest = new StoryCreationRequest();
        storyRequest.setStory("A very Long Story!");

        String postValue = OBJECT_MAPPER.writeValueAsString(storyRequest);
        MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/" + user.getId() + "/story")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andDo(document("story",
                        relaxedRequestFields(
                                fieldWithPath("story")
                                        .description("The text of the story.")
                        )))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Story story = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(), Story.class);

        MvcResult foundStoryResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/" + user.getId() + "/story/" + story.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Story foundStory = OBJECT_MAPPER.readValue(foundStoryResult.getResponse().getContentAsString(), Story.class);

        assertThat(story.getText()).isEqualTo(foundStory.getText());
        assertThat(story.getId()).isEqualTo(foundStory.getId());
        assertThat(story.getUser().getId()).isEqualTo(foundStory.getUser().getId());
    }

    private RegisterUserRequest getUserRegisterRequest() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("igorce@gmail.com");
        registerUserRequest.setName("Igor");
        registerUserRequest.setSurname("Stojanovski");
        registerUserRequest.setPersonalId("123698456");
        return registerUserRequest;
    }

}
