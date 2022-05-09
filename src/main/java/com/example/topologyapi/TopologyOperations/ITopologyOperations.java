package com.example.topologyapi.TopologyOperations;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.FileNotFoundException;

@Service
public interface ITopologyOperations {
    JSONArray queryTopologies();
    void readJSON(@RequestBody String fileName) throws ParseException, FileNotFoundException;
    void writeJSON(@RequestBody String topologyId) throws ParseException;
    void deleteTopology(@RequestBody String topologyId) throws ParseException;
    JSONArray queryDevices(@RequestBody String topologyId) throws ParseException;
}
