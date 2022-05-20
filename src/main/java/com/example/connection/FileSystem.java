package com.example.connection;

import com.example.topology.Topology;
import com.example.topologycomponent.TopologyComponent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SuppressWarnings("unchecked")
public class FileSystem implements IDataBase{
    List<Topology> topologies;
    static final String COMPONENTS = "components";
    static final String NETLIST = "netlist";
    static final String ID = "id";
    static final String TYPE = "type";
    static final String MESSAGE = "message";
    static final String FILE = "E:\\topology.json";
    public FileSystem(){
        topologies =  new ArrayList<>();
    }

    public boolean exist(String id){
        for (Topology topology : topologies) {
            if (topology.getId().equals(id))
                return true;
        }
        return false;
    }

    private Map<String, String> convertJsonToMap(JSONObject jsonObject){
        Map<String, String> keyAndValue = new HashMap<>();
        for (Object keyObject : jsonObject.keySet()) {
            String key = (String) keyObject;
            keyAndValue.put(key, jsonObject.get(key).toString());
        }
        return keyAndValue;
    }

    public ResponseEntity<JSONObject> readJSON(String fileName) {
        JSONObject message = new JSONObject();
        JSONParser parser = new JSONParser();
        Topology topology = new Topology();
        try {
            Object fileParser = parser.parse(new FileReader(fileName));
            JSONObject fileData = (JSONObject)fileParser;
            String id = (String)fileData.get(ID);
            if(exist(id))
                deleteTopology(id);
            topology.setId(id);
            JSONArray components = (JSONArray)fileData.get(COMPONENTS);
            for (Object component : components) {
                JSONObject jsonComponent = (JSONObject) component;
                TopologyComponent topologyComponent = new TopologyComponent();
                for (Object keyObject : jsonComponent.keySet()) {
                    String key = (String) keyObject;
                    switch (key) {
                        case ID:
                            topologyComponent.setId((String) jsonComponent.get(ID));
                            break;
                        case TYPE:
                            topologyComponent.setType((String) jsonComponent.get(TYPE));
                            break;
                        case NETLIST: {
                            JSONObject netlistObject = (JSONObject) jsonComponent.get(NETLIST);
                            topologyComponent.setNetList(convertJsonToMap(netlistObject));
                            break;
                        }
                        default: {
                            JSONObject componentObject = (JSONObject) jsonComponent.get(key);
                            topologyComponent.setName(key);
                            topologyComponent.setComponentDetails(convertJsonToMap(componentObject));
                            break;
                        }
                    }
                }
                topology.setComponent(topologyComponent);
            }
            topologies.add(topology);
            message.put(MESSAGE, "Topology is read Successfully");
            return new ResponseEntity<>(message,HttpStatus.OK);
        } catch(Exception e) {
            message.put(MESSAGE, "This File Doesn't Exist");
            return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
        }
    }
    private void convertComponent(TopologyComponent component, JSONArray components){
        JSONObject jsonComponent = new JSONObject();
        JSONObject jsonNetList = new JSONObject();
        JSONObject jsonDetails = new JSONObject();
        jsonComponent.put(TYPE, component.getType());
        jsonComponent.put(ID, component.getId());
        jsonDetails.putAll(component.getComponentDetails());
        jsonNetList.putAll(component.getNetList());
        jsonComponent.put(component.getName(), jsonDetails);
        jsonComponent.put(NETLIST, jsonNetList);
        components.add(jsonComponent);
    }
    private void convertComponents(Topology topology, JSONArray components) {
        for(TopologyComponent component: topology.getComponentList()){
            convertComponent(component, components);
        }
    }
    private Topology getTopologyById(String topologyId){
        for(Topology topology: topologies){
            if(topology.getId().equals(topologyId))
                return topology;
        }
        return new Topology();
    }
    public ResponseEntity<JSONObject> writeJSON(String topologyId) {
        JSONObject message = new JSONObject();
        Topology topology = getTopologyById(topologyId);
        if(topology.getId()!=null){
            JSONObject jsonTopology = new JSONObject();
            JSONArray components = new JSONArray();
            jsonTopology.put(ID, topology.getId());
            convertComponents(topology, components);
            jsonTopology.put(COMPONENTS, components);
            File file = new File(FILE);
            try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
                out.write(jsonTopology.toString());
                message.put(MESSAGE, "The Topology is written successfully");
                return new ResponseEntity<>(message, HttpStatus.OK);
            } catch (Exception e) {
                message.put(MESSAGE, "The File Doesn't Exist");
                return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
            }
        }
        else{
            message.put(MESSAGE, "There's no Topology stored in Memory with this Id");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<JSONArray> queryDevices(String topologyId){
        Topology topology = getTopologyById(topologyId);
        if(topology.getId()!=null){
            JSONArray components = new JSONArray();
            convertComponents(topology, components);
            return new ResponseEntity<>(components, HttpStatus.OK);
        }
        return new ResponseEntity<>(new JSONArray(), HttpStatus.NOT_FOUND);
    }

    public  ResponseEntity<JSONObject> deleteTopology(String topologyId) {
        JSONObject message = new JSONObject();
        for(Topology topology: topologies){
            if(topology.getId().equals(topologyId)){
                topologies.remove(topology);
                message.put(MESSAGE, "Topology Deleted Successfully");
                return new ResponseEntity<>(message, HttpStatus.OK);
            }
        }
        message.put(MESSAGE, "There's no Topology stored in Memory with this Id");
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    public JSONArray queryTopologies(){
        JSONArray jsonTopologies = new JSONArray();
        for (Topology topology : topologies) {
            JSONObject jsonTopology = new JSONObject();
            JSONArray components = new JSONArray();
            jsonTopology.put(ID, topology.getId());
            convertComponents(topology, components);
            jsonTopology.put(COMPONENTS, components);
            jsonTopologies.add(jsonTopology);
        }
        return jsonTopologies;
    }

    public ResponseEntity<JSONArray> queryDevicesToNetList(String topologyId, String netListId){
        Topology topology = getTopologyById(topologyId);
        JSONArray components = new JSONArray();
        if(topology.getId()!=null){
            for(TopologyComponent component: topology.getComponentList()){
                if(component.getNetList().get(ID).equals(netListId)){
                    convertComponent(component, components);
                }
            }
            return new ResponseEntity<>(components, HttpStatus.OK);
        }
        return new ResponseEntity<>(new JSONArray(), HttpStatus.NOT_FOUND);
    }
}
