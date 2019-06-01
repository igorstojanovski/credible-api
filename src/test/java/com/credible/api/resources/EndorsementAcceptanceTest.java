package com.credible.api.resources;

import com.credible.api.model.User;
import com.credible.api.model.requests.EndorsementRequest;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class EndorsementAcceptanceTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private User user = null;
    private User endorser = null;

    @Before
    public void beforeEach() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        String userContent = UserAcceptanceTest.registerUser(mockMvc, getUserRegisterRequest());
        String endorserContent = UserAcceptanceTest.registerUser(mockMvc, getEndorserRegisterRequest());
        user = OBJECT_MAPPER.readValue(userContent, User.class);
        endorser = OBJECT_MAPPER.readValue(endorserContent, User.class);
    }

    @Test
    public void shouldCreateVoucherForUser() throws Exception {

        EndorsementRequest endorsementRequest = new EndorsementRequest();
        endorsementRequest.setEndorserId(endorser.getId());
        endorsementRequest.setUserId(user.getId());
        endorsementRequest.setText("This is the endorsement.");

        String postValue = OBJECT_MAPPER.writeValueAsString(endorsementRequest);
        MvcResult storyResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/" + user.getId() + "/endorsement")
                .contentType(MediaType.APPLICATION_JSON)
                .content(postValue))
                .andExpect(status().isCreated())
                .andDo(document("endorsement",
                        responseFields(
                                fieldWithPath("id")
                                        .description("ID of the newly created endorsement."),
                                fieldWithPath("endorserId")
                                        .description("The user ID of the user that makes the endorsement."),
                                fieldWithPath("userId")
                                        .description("The user ID of the user who the endorsement is for."),
                                fieldWithPath("text")
                                        .description("The text of the endorsement.")
                        )))
                .andDo(print())
                .andReturn();
    }

    private RegisterUserRequest getUserRegisterRequest() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("igorski@gmail.com");
        registerUserRequest.setName("Igor");
        registerUserRequest.setSurname("Stojanovski");
        registerUserRequest.setPersonalId("12987458456");
        return registerUserRequest;
    }

    private RegisterUserRequest getEndorserRegisterRequest() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setEmail("endorser@gmail.com");
        registerUserRequest.setName("John");
        registerUserRequest.setSurname("Doe");
        registerUserRequest.setPersonalId("1298658456");
        return registerUserRequest;
    }
}
