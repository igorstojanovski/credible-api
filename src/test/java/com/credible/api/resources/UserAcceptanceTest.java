package com.credible.api.resources;


import com.credible.api.model.Person;
import com.credible.api.model.User;
import com.credible.api.model.requests.RegisterUserRequest;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class UserAcceptanceTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    public static String registerUser(MockMvc mockMvc, RegisterUserRequest registerUserRequest) throws Exception {

        String postValue = OBJECT_MAPPER.writeValueAsString(registerUserRequest);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andDo(print())
                .andDo(document("user",
                        requestFields(
                                fieldWithPath("personalId")
                                        .description("Unique id of the person."),
                                fieldWithPath("email")
                                        .description("Email of the user."),
                                fieldWithPath("name")
                                        .description("Name of the user."),
                                fieldWithPath("surname")
                                        .description("Surname of the user.")
                        )))
                .andExpect(status().isCreated())
                .andReturn();

        return result.getResponse().getContentAsString();
    }

    @Before
    public void beforeEach() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void shouldCreateNewUserResource() throws Exception {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("igor@credible.com");
        registerUserRequest.setName("Igor");
        registerUserRequest.setSurname("Stojanovski");
        registerUserRequest.setPersonalId("6698746663");

        User createdUser = getUserObjectFromResponse(registerUser(this.mockMvc, registerUserRequest));

        assertThat(createdUser.getId()).isNotNull();
        assertThat(createdUser.getEmail()).isEqualTo("igor@credible.com");
        assertThat(createdUser.getName()).isEqualTo("Igor");
        assertThat(createdUser.getSurname()).isEqualTo("Stojanovski");
    }

    @Test
    public void shouldGetExistingUserOnLogin() throws Exception {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("igor@outlook.com");
        registerUserRequest.setName("Igor");
        registerUserRequest.setSurname("Stojanovski");
        registerUserRequest.setPersonalId("2222456789");

        User registeredUser = getUserObjectFromResponse(registerUser(this.mockMvc, registerUserRequest));

        MvcResult personResult = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/person?pid=2222456789")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = personResult.getResponse().getContentAsString();
        Person person = OBJECT_MAPPER.readValue(contentAsString, Person.class);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/" + person.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        User user = getUserObjectFromResponse(result.getResponse().getContentAsString());

        assertThat(registeredUser.getId()).isEqualTo(user.getId());
        assertThat(registeredUser.getName()).isEqualTo(user.getName());
        assertThat(registeredUser.getSurname()).isEqualTo(user.getSurname());
        assertThat(registeredUser.getEmail()).isEqualTo(user.getEmail());
    }

    private User getUserObjectFromResponse(String registerResponse) throws java.io.IOException {
        return OBJECT_MAPPER.readValue(registerResponse, User.class);
    }
}
