package com.example.topologyapi.TopologyOperations;
import com.example.topologyapi.Connection.FileSystem;
import com.example.topologyapi.Connection.IDataBase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<JSONObject> readJSON(@RequestBody String file) throws ParseException, FileNotFoundException {
        JSONObject message = new JSONObject();
        JSONObject object = (JSONObject) new JSONParser().parse(file);
        if(object.get("fileName")==null){
            message.put("message", "Invalid Input");
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
        String fileName = object.get("fileName").toString();
        return database.readJSON(fileName);
    }

    @RequestMapping("writeJSON")
    @PostMapping
    @Override
    public ResponseEntity<JSONObject> writeJSON(@RequestBody String topologyId) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(topologyId);
        JSONObject message = new JSONObject();
        if(object.get("topologyId")==null){
            message.put("message", "Invalid Input");
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
        String topologyID = object.get("topologyId").toString();
        return database.writeJSON(topologyID);
    }

    @RequestMapping("deleteTopology")
    @PostMapping
    @Override
    public ResponseEntity<JSONObject> deleteTopology(@RequestBody String topologyId) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(topologyId);
        JSONObject message = new JSONObject();
        if(object.get("topologyId") == null){
            message.put("message", "Invalid Input");
            return new ResponseEntity<JSONObject>(message, HttpStatus.BAD_REQUEST);
        }
        String topologyID = object.get("topologyId").toString();
        return database.deleteTopology(topologyID);
    }

    @RequestMapping("queryDevices")
    @PostMapping
    @Override
    public ResponseEntity<JSONArray> queryDevices(@RequestBody String topologyId) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(topologyId);
        if(object.get("topologyId") == null){
            return new ResponseEntity<JSONArray>(new JSONArray(), HttpStatus.BAD_REQUEST);
        }
        String topologyID = object.get("topologyId").toString();
        return database.queryDevices(topologyID);
    }

    @RequestMapping("queryDevicesToNetlist")
    @PostMapping
    @Override
    public ResponseEntity<JSONArray> queryDevicesToNetlist(@RequestBody String ids) throws ParseException {
        JSONObject object = (JSONObject) new JSONParser().parse(ids);
        if(object.get("topologyId") == null || object.get("netlistId") == null){
            return new ResponseEntity<JSONArray>(new JSONArray(), HttpStatus.BAD_REQUEST);
        }
        String topologyID = object.get("topologyId").toString();
        String netlistId = object.get("netlistId").toString();
        return database.queryDevicesToNetlist(topologyID, netlistId);
    }
}
