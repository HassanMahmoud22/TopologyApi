package com.example.topologyapi.TopologyOperations;
import com.example.topologyapi.Connection.FileSystem;
import com.example.topologyapi.Connection.IDataBase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;

@CrossOrigin
@RequestMapping("/api/topology")
@RestController
public class TopologyOperationsController implements ITopologyOperations {
    IDataBase database;

    @Autowired
    public TopologyOperationsController(){
        database =  new FileSystem();
    }

    @RequestMapping("queryTopologies")
    @PostMapping
    @Override
    public JSONArray queryTopologies(){
        return database.queryTopologies();
    }

    @RequestMapping("readJSON")
    @PostMapping
    @Override
    public void readJSON(@RequestBody String file) throws ParseException, FileNotFoundException {
        JSONObject object = (JSONObject) new JSONParser().parse(file);
        String fileName = object.get("fileName").toString();
        database.readJSON(fileName);
    }

    @RequestMapping("writeJSON")
    @PostMapping
    @Override
    public void writeJSON(@RequestBody String topologyId) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(topologyId);
        String topologyID = object.get("topologyId").toString();
        database.writeJSON(topologyID);
    }

    @RequestMapping("deleteTopology")
    @PostMapping
    @Override
    public void deleteTopology(@RequestBody String topologyId) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(topologyId);
        String topologyID = object.get("topologyId").toString();
        database.deleteTopology(topologyID);
    }

    @RequestMapping("queryDevices")
    @PostMapping
    @Override
    public JSONArray queryDevices(@RequestBody String topologyId) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(topologyId);
        String topologyID = object.get("topologyId").toString();
        return database.queryDevices(topologyID);
    }
}
