package com.example.springboothello.controller;

import com.example.springboothello.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void shouldGetAllUsers() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andDo(document("users-get-all",
                        responseFields(
                                fieldWithPath("[].id").description("User's ID"),
                                fieldWithPath("[].name").description("User's name"),
                                fieldWithPath("[].email").description("User's email"),
                                fieldWithPath("[].createdAt").description("User creation timestamp")
                        )));
    }

    @Test
    public void shouldGetUserById() throws Exception {
        this.mockMvc.perform(get("/user/{id}", 1))
                .andExpect(status().isOk())
                .andDo(document("user-get-by-id",
                        responseFields(
                                fieldWithPath("id").description("User's ID"),
                                fieldWithPath("name").description("User's name"),
                                fieldWithPath("email").description("User's email"),
                                fieldWithPath("createdAt").description("User creation timestamp")
                        )));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");

        this.mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(document("user-create",
                        requestFields(
                                fieldWithPath("name").description("User's name"),
                                fieldWithPath("email").description("User's email")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Created user's ID"),
                                fieldWithPath("name").description("Created user's name"),
                                fieldWithPath("email").description("Created user's email"),
                                fieldWithPath("createdAt").description("User creation timestamp")
                        )));
    }
}