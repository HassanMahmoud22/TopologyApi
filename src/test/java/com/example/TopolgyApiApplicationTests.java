package com.example;

import com.example.topologyoperations.TopologyOperationsController;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TopologyOperationsController.class)
class TopolgyApiApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    public JSONObject message = new JSONObject();

    @MockBean
    private TopologyOperationsController service = new TopologyOperationsController();

    @Test
     void readJsonTest1() throws Exception {
        message.put("message", "Topology Read Successfully");
        when(service.readJSON("topology.json")).thenReturn(new ResponseEntity<>(message, HttpStatus.OK));
        this.mockMvc.perform(get("/readJSON")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Topology Read Successfully")));
    }

    @Test
    void readJsonTest2() throws Exception {
        message.put("message", "This File Doesn't Exist");
        when(service.readJSON("hassan.json")).thenReturn(new ResponseEntity<>(message, HttpStatus.NOT_FOUND));
        this.mockMvc.perform(get("/readJSON")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(containsString("This File Doesn't Exist")));
    }

    @Test
    void writeJSONTest1() throws Exception {
        message.put("message", "The Topology is written successfully");
        when(service.writeJSON("top1")).thenReturn(new ResponseEntity<>(message, HttpStatus.OK));
        this.mockMvc.perform(get("/writeJSON")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("The Topology is written successfully")));
    }

    @Test
    void writeJSONTest2() throws Exception {
        message.put("message", "There's no Topology stored in Memory with this Id");
        when(service.writeJSON("top5")).thenReturn(new ResponseEntity<>(message, HttpStatus.NOT_FOUND));
        this.mockMvc.perform(get("/writeJSON")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(containsString("There's no Topology stored in Memory with this Id")));
    }

    @Test
    void deleteTopologyTest1() throws Exception {
        message.put("message", "The Topology is Deleted Successfully");
        when(service.writeJSON("top5")).thenReturn(new ResponseEntity<>(message, HttpStatus.OK));
        this.mockMvc.perform(get("/deleteTopology")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("The Topology is Deleted Successfully")));
    }

    @Test
    void deleteTopologyTest2() throws Exception {
        message.put("message", "There's no Topology stored in Memory with this Id");
        when(service.writeJSON("top5")).thenReturn(new ResponseEntity<>(message, HttpStatus.NOT_FOUND));
        this.mockMvc.perform(get("/deleteTopology")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(containsString("There's no Topology stored in Memory with this Id")));
    }
}
