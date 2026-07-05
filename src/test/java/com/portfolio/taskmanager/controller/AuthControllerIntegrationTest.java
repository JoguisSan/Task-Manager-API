package com.portfolio.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.taskmanager.dto.auth.LoginRequest;
import com.portfolio.taskmanager.dto.auth.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveRegistrarUsuarioComSucesso() throws Exception {
        RegisterRequest request = new RegisterRequest("maria_teste", "maria_teste@email.com", "senha123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("maria_teste"))
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void deveRetornarConflitoAoRegistrarUsernameDuplicado() throws Exception {
        RegisterRequest request = new RegisterRequest("duplicado", "duplicado1@email.com", "senha123");
        RegisterRequest requestDuplicado = new RegisterRequest("duplicado", "duplicado2@email.com", "senha123");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDuplicado)))
                .andExpect(status().isConflict());
    }

    @Test
    void deveRetornarErroDeValidacaoComDadosInvalidos() throws Exception {
        RegisterRequest invalidRequest = new RegisterRequest("ab", "email-invalido", "123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveFazerLoginComSucessoAposRegistro() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("carlos_teste", "carlos_teste@email.com", "senha123");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)));

        LoginRequest loginRequest = new LoginRequest("carlos_teste", "senha123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}
