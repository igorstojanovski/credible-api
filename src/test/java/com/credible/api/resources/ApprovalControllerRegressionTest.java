package com.credible.api.resources;

import com.credible.api.model.Approval;
import com.credible.api.model.User;
import com.credible.api.model.requests.RegisterUserRequest;
import com.credible.api.model.responses.ApprovalResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.credible.api.resources.UserAcceptanceTest.registerUser;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Ignore
public class ApprovalControllerRegressionTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateApproval() throws Exception {
        RegisterUserRequest approvedUserRequest = new RegisterUserRequest();
        approvedUserRequest.setPersonalId("12345678912");
        approvedUserRequest.setEmail("igorce@gmail.com");

        RegisterUserRequest approvingUserRequest = new RegisterUserRequest();
        approvingUserRequest.setPersonalId("12345678913");
        approvingUserRequest.setEmail("igorce@outlook.com");

        User approvingUser = OBJECT_MAPPER.readValue(registerUser(mockMvc, approvingUserRequest), User.class);
        User approvedUser = OBJECT_MAPPER.readValue(registerUser(mockMvc, approvedUserRequest), User.class);

        this.mockMvc.perform(
                post("/api/user/{userId}/approval?approvingUserId=" + approvingUser.getId(), approvedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(document("approval",
                        requestParameters(
                                parameterWithName("approvingUserId")
                                        .description("ID of the user that does the approving.")
                        )))
                .andDo(document("approval",
                        pathParameters(
                                parameterWithName("userId")
                                        .description("ID of the user that is target of the approval.")
                        )))
                .andReturn();

        MvcResult getResult = this.mockMvc.perform(
                get("/api/user/{userId}/approval", approvedUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("approval", responseFields(
                        fieldWithPath("[].approvedUserId").description("ID of the user target of the approval."),
                        fieldWithPath("[].approvingUserId").description("ID of the user doing the approving.")
                )))
                .andReturn();

        ApprovalResponse[] approvals = OBJECT_MAPPER.readValue(getResult.getResponse().getContentAsString(), ApprovalResponse[].class);
        assertThat(approvals).hasSize(1);
    }
}
