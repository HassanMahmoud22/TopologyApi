package com.example.topologyapi.Connection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileNotFoundException;

public interface IDataBase {
    ResponseEntity<JSONObject> readJSON(@RequestBody String file) throws ParseException, FileNotFoundException;
    ResponseEntity<JSONObject> writeJSON(@RequestBody String topologyId) throws ParseException;
    ResponseEntity<JSONArray> queryDevices(String topologyId);
    ResponseEntity<JSONObject> deleteTopology(String topologyId) throws ParseException;
    JSONArray queryTopologies();
    ResponseEntity<JSONArray> queryDevicesToNetlist(String topologyId, String netlistId);
}
