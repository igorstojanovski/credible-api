package com.credible.api.resources;

import com.credible.NonSecureApplication;
import com.credible.api.model.User;
import com.credible.api.services.UserService;
import com.credible.configuration.model.BankIdUser;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NonSecureApplication.class)
@AutoConfigureMockMvc
public class UserIT {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    @Autowired
    UserService userService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGetUser() throws Exception {

        BankIdUser bankIdUser = new BankIdUser();
        bankIdUser.setGivenName("Igor");
        bankIdUser.setSurname("Stojanovski");
        bankIdUser.setName("Igor Stojanovski");
        bankIdUser.setPersonalNumber("123456789");

        User user = (User) userService.onBoard(1L, bankIdUser);

        MvcResult personResult = mockMvc.perform(MockMvcRequestBuilders
            .get("/api/user/" + user.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();
    }
}
