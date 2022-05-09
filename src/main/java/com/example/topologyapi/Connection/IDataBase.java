package com.example.topologyapi.Connection;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.FileNotFoundException;

public interface IDataBase {
    void readJSON(@RequestBody String file) throws ParseException, FileNotFoundException;
    void writeJSON(@RequestBody String topologyId) throws ParseException;
    JSONArray queryDevices(String topologyId);
    void deleteTopology(String topologyId) throws ParseException;
    JSONArray queryTopologies();
}
