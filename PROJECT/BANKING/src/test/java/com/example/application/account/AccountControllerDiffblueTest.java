package com.example.application.account;

import static org.mockito.Mockito.when;

import com.example.application.Login.Login;
import com.example.application.services.AuthenticationService;
import com.example.application.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {AccountController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AccountControllerDiffblueTest {
    @Autowired
    private AccountController accountController;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    /**
     * Method under test: {@link AccountController#authenticate(Login)}
     */
    @Test
    void testAuthenticate() throws Exception {
        // Arrange
        when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(jwtService.getExpirationTime()).thenReturn(1L);

        Account account = new Account();
        account.setAccountId(1L);
        account.setAddress("42 Main St");
        account.setBankBalance(10.0f);
        account.setName("Name");
        account.setPassword("iloveyou");
        account.setRoles("Roles");
        account.setUseremail("jane.doe@example.org");
        account.setUsername("janedoe");
        when(authenticationService.authenticate(Mockito.<Login>any())).thenReturn(account);

        Account account2 = new Account();
        account2.setAccountId(1L);
        account2.setAddress("42 Main St");
        account2.setBankBalance(10.0f);
        account2.setName("Name");
        account2.setPassword("iloveyou");
        account2.setRoles("Roles");
        account2.setUseremail("jane.doe@example.org");
        account2.setUsername("janedoe");
        Optional<Account> ofResult = Optional.of(account2);
        when(accountRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);

        Login login = new Login();
        login.setPassword("iloveyou");
        login.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(login);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/accounts/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"token\":\"ABC123\",\"expiresIn\":1,\"account\":{\"accountId\":1,\"name\":\"Name\",\"username\":\"janedoe\",\"password"
                                        + "\":\"iloveyou\",\"roles\":\"Roles\",\"useremail\":\"jane.doe@example.org\",\"address\":\"42 Main St\",\"bankBalance\""
                                        + ":10.0,\"enabled\":true,\"accountNonLocked\":true,\"authorities\":[{\"authority\":\"Roles\"}],\"accountNonExpired"
                                        + "\":true,\"credentialsNonExpired\":true}}"));
    }

    /**
     * Method under test: {@link AccountController#authenticate(Login)}
     */
    @Test
    void testAuthenticate2() throws Exception {
        // Arrange
        when(jwtService.generateToken(Mockito.<UserDetails>any())).thenReturn("ABC123");
        when(jwtService.getExpirationTime()).thenReturn(1L);

        Account account = new Account();
        account.setAccountId(1L);
        account.setAddress("42 Main St");
        account.setBankBalance(10.0f);
        account.setName("Name");
        account.setPassword("iloveyou");
        account.setRoles("Roles");
        account.setUseremail("jane.doe@example.org");
        account.setUsername("janedoe");
        when(authenticationService.authenticate(Mockito.<Login>any())).thenReturn(account);
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);

        Login login = new Login();
        login.setPassword("iloveyou");
        login.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(login);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/accounts/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(accountController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"token\":\"ABC123\",\"expiresIn\":1,\"account\":null}"));
    }
}
