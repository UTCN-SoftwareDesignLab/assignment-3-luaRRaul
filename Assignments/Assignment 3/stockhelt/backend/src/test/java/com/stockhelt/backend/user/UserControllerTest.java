package com.stockhelt.backend.user;

import com.stockhelt.backend.BaseControllerTest;
import com.stockhelt.backend.TestCreationFactory;
import com.stockhelt.backend.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.stockhelt.backend.TestCreationFactory.*;
import static com.stockhelt.backend.UrlMapping.ENTITY;
import static com.stockhelt.backend.UrlMapping.USERS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserDTO> userListDTOs = TestCreationFactory.listOf(UserDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        Set<String> roles = new HashSet<>();
        UserDTO reqUser = UserDTO.builder()
                .id(id)
                .username(randomString())
                .email(randomEmail())
                .build();

        when(userService.edit(id, reqUser)).thenReturn(reqUser);

        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS+ENTITY, reqUser, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void getUser() throws Exception {
        long id  = randomLong();
        Set<String> roles = new HashSet<>();
        UserDTO reqUser = UserDTO.builder()
                .id(id)
                .username(randomString())
                .email(randomEmail())
                .build();
        when(userService.get(id)).thenReturn(reqUser);

        ResultActions result = performGetWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void delete() throws Exception {
        long id  = randomLong();
        doNothing().when(userService).delete(id);

        ResultActions result = performDeleteWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk());
    }
}