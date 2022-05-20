package com.example.topologyoperations;
import com.example.connection.FileSystem;
import com.example.connection.IDataBase;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.FileNotFoundException;
@SuppressWarnings("unchecked")
@CrossOrigin
@RequestMapping("/api/topology")
@RestController
public class TopologyOperationsController implements ITopologyOperations {
    private final IDataBase database = new FileSystem();
    private final JSONObject message = (JSONObject) new JSONObject().put("message", "Invalid Input");
    static final String TOPOLOGYID = "topologyId";
    static final String NETLISTID = "netlistId";
    static final String FILENAME = "fileName";

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
        JSONObject fileObject = (JSONObject) new JSONParser().parse(file);
        if(fileObject.get(FILENAME)==null){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        String fileName = fileObject.get(FILENAME).toString();
        return database.readJSON(fileName);
    }

    @RequestMapping("writeJSON")
    @PostMapping
    @Override
    public ResponseEntity<JSONObject> writeJSON(@RequestBody String topologyId) throws ParseException {
        JSONObject topologyIdObject = (JSONObject) new JSONParser().parse(topologyId);
        if(topologyIdObject.get(TOPOLOGYID)==null){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        String topologyID = topologyIdObject.get(TOPOLOGYID).toString();
        return database.writeJSON(topologyID);
    }

    @RequestMapping("deleteTopology")
    @PostMapping
    @Override
    public ResponseEntity<JSONObject> deleteTopology(@RequestBody String topologyId) throws ParseException {
        JSONObject topologyIdObject = (JSONObject) new JSONParser().parse(topologyId);
        if(topologyIdObject.get(TOPOLOGYID) == null){
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        String topologyID = topologyIdObject.get(TOPOLOGYID).toString();
        return database.deleteTopology(topologyID);
    }

    @RequestMapping("queryDevices")
    @PostMapping
    @Override
    public ResponseEntity<JSONArray> queryDevices(@RequestBody String topologyId) throws ParseException {
        JSONObject topologyIdObject = (JSONObject) new JSONParser().parse(topologyId);
        if(topologyIdObject.get(TOPOLOGYID) == null){
            return new ResponseEntity<>(new JSONArray(), HttpStatus.BAD_REQUEST);
        }
        String topologyID = topologyIdObject.get(TOPOLOGYID).toString();
        return database.queryDevices(topologyID);
    }

    @RequestMapping("queryDevicesToNetlist")
    @PostMapping
    @Override
    public ResponseEntity<JSONArray> queryDevicesToNetlist(@RequestBody String ids) throws ParseException {
        JSONObject idsObject = (JSONObject) new JSONParser().parse(ids);
        if(idsObject.get(TOPOLOGYID) == null || idsObject.get(NETLISTID) == null){
            return new ResponseEntity<>(new JSONArray(), HttpStatus.BAD_REQUEST);
        }
        String topologyID = idsObject.get(TOPOLOGYID).toString();
        String netlistId = idsObject.get(NETLISTID).toString();
        return database.queryDevicesToNetList(topologyID, netlistId);
    }
}
