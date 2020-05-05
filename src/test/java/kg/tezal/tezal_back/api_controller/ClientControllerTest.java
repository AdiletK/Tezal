package kg.tezal.tezal_back.api_controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import kg.tezal.tezal_back.entity.Client;
import kg.tezal.tezal_back.enums.ClientSex;
import kg.tezal.tezal_back.model.ClientShortModel;
import kg.tezal.tezal_back.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientServiceImpl clientService;

    private List<ClientShortModel> clientList;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        this.clientList = new ArrayList<>();
        ClientShortModel client = new ClientShortModel();
        client.setFirstName("Adilet");
        client.setClientSex(ClientSex.MALE);
        ClientShortModel client1 = new ClientShortModel();
        client1.setFirstName("Esen");
        client1.setClientSex(ClientSex.MALE);
        clientList.add(client);
        clientList.add(client1);
    }

    @Test
    public void getAll() throws Exception {
        given(clientService.getAllClients()).willReturn(clientList);
        this.mockMvc.perform(get("/api/client/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(clientList.size())));
    }

    @Test
    void shouldFetchOneById() throws Exception {
        final Long id = 1L;
        final Client client = new Client();
        client.setId(id);
        client.setClientSex(ClientSex.MALE);
        client.setFirstName("Adilet");
        client.setLastName("Kadyrbekov");
        given(clientService.findById(id)).willReturn((client));

        this.mockMvc.perform(get("/api/client/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(client.getLastName())));
    }

    @Test
    void shouldReturn404WhenFindClientById() throws Exception {
        given(clientService.findById(1L)).willReturn(null);

        ResultActions resultActions =  this.mockMvc.perform(get("/api/client/{id}", 3L))
                .andExpect(status().isOk());
        assertTrue(resultActions.andReturn().getResponse().getContentAsString().isEmpty());
    }

    @Test
    void shouldCreateNewClient() throws Exception {
        given(clientService.create(any(Client.class))).willAnswer((invocation) -> invocation.getArgument(0));
        final Client client = new Client();
        client.setId(1L);
        client.setClientSex(ClientSex.MALE);
        client.setFirstName("Adilet");
        client.setLastName("Kadyrbekov");


        this.mockMvc.perform(post("/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(client.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(client.getLastName())));

    }
    @Test
    void shouldDeleteClient() throws Exception {
        final Long id = 1L;
        final Client client = new Client();
        client.setId(id);
        client.setClientSex(ClientSex.MALE);
        client.setFirstName("Adilet");
        client.setLastName("Kadyrbekov");
        given(clientService.deleteById(id)).willReturn("Client number 1 has been deleted!");

        ResultActions resultActions = this.mockMvc.perform(delete("/api/client/{id}", client.getId()))
                .andExpect(status().isOk());

        assertEquals("Client number 1 has been deleted!", resultActions.andReturn().getResponse().getContentAsString());
    }

}
