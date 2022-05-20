package com.example.topologyoperations;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.FileNotFoundException;

@Service
public interface ITopologyOperations {
    JSONArray queryTopologies();
    ResponseEntity<JSONObject> readJSON(@RequestBody String file) throws ParseException, FileNotFoundException;
    ResponseEntity<JSONObject> writeJSON(@RequestBody String topologyId) throws ParseException;
    ResponseEntity<JSONObject> deleteTopology(@RequestBody String topologyId) throws ParseException;
    ResponseEntity<JSONArray> queryDevices(@RequestBody String topologyId) throws ParseException;
    ResponseEntity<JSONArray> queryDevicesToNetlist(@RequestBody String ids) throws ParseException;
}
