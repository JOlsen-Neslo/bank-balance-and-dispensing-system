package za.co.neslotech.banking.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRetrieveAccounts() throws Exception {
        this.mockMvc.perform(get("/clients/1/accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId", is("1")))
                .andExpect(jsonPath("$.accounts", hasSize(3)));
    }

    @Test
    public void testRetrieveAccountsEmpty() throws Exception {
        this.mockMvc.perform(get("/clients/45/accounts"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("No accounts to display.")));
    }

}
