package com.example.topologyapi.Connection;

import com.example.topologyapi.Topology.Topology;
import com.example.topologyapi.TopologyComponent.TopologyComponent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystem implements IDataBase{
    List<Topology> topologies;

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

    public ResponseEntity<JSONObject> readJSON(String fileName) {
        JSONObject message = new JSONObject();
        JSONParser parser = new JSONParser();
        Topology topology = new Topology();
        try {
            Object obj = parser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject)obj;
            String id = (String)jsonObject.get("id");
            if(exist(id))
                deleteTopology(id);
            topology.setId((String)jsonObject.get("id"));
            JSONArray components = (JSONArray)jsonObject.get("components");
            for (Object o : components) {
                JSONObject component = (JSONObject) o;
                TopologyComponent topologyComponent = new TopologyComponent();
                for (Object value : component.keySet()) {
                    String iteratorNext = (String) value;
                    switch (iteratorNext) {
                        case "id":
                            topologyComponent.setId((String) component.get("id"));
                            break;
                        case "type":
                            topologyComponent.setType((String) component.get("type"));
                            break;
                        case "netlist": {
                            JSONObject temp = (JSONObject) component.get("netlist");
                            Map<String, String> keyAndValue = new HashMap<>();
                            for (Object item : temp.keySet()) {
                                String result = (String) item;
                                keyAndValue.put(result, (String) temp.get(result));
                            }
                            topologyComponent.setNetlist(keyAndValue);
                            break;
                        }
                        default: {
                            JSONObject temp = (JSONObject) component.get(iteratorNext);
                            Map<String, String> keyAndValue = new HashMap<>();
                            for (Object item : temp.keySet()) {
                                String result = (String) item;
                                keyAndValue.put(result, temp.get(result).toString());
                            }
                            topologyComponent.setName(iteratorNext);
                            topologyComponent.setComponentDetails(keyAndValue);
                            break;
                        }
                    }
                }
                topology.setComponent(topologyComponent);
            }
            topologies.add(topology);
            message.put("message", "Topology is read Successfully");
            return new ResponseEntity<JSONObject>(message,HttpStatus.OK);
        } catch(Exception e) {
            message.put("message", "This File Doesn't Exist");
            return new ResponseEntity<JSONObject>(message,HttpStatus.NOT_FOUND);
        }
    }
    private void convertComponent(TopologyComponent component, JSONArray components){
        JSONObject jsonComponent = new JSONObject();
        JSONObject jsonNetlist = new JSONObject();
        JSONObject jsonDetails = new JSONObject();
        jsonComponent.put("type", component.getType());
        jsonComponent.put("id", component.getId());
        jsonDetails.putAll(component.getComponentDetails());
        jsonNetlist.putAll(component.getNetlist());
        jsonComponent.put(component.getName(), jsonDetails);
        jsonComponent.put("netlist", jsonNetlist);
        components.add(jsonComponent);
    }
    private void convertComponents(Topology topology, JSONArray components) {
        for(TopologyComponent component: topology.getComponentList()){
            convertComponent(component, components);
        }
    }
    private Topology getTopologyById(String topologyId){
        Topology nullTopology = new Topology();
        for(Topology topology: topologies){
            if(topology.getId().equals(topologyId))
                return topology;
        }
        return nullTopology;
    }
    public ResponseEntity<JSONObject> writeJSON(String topologyId) {
        JSONObject message = new JSONObject();
        Topology topology = getTopologyById(topologyId);
        if(topology.getId()!=null){
            String path = "E:\\TopolgyApi\\topology3.json";
            JSONObject jsonTopology = new JSONObject();
            JSONArray components = new JSONArray();
            jsonTopology.put("id", topology.getId());
            convertComponents(topology, components);
            jsonTopology.put("components", components);
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                out.write(jsonTopology.toString());
                message.put("message", "The Topology is written successfully");
                return new ResponseEntity<JSONObject>(message, HttpStatus.OK);
            } catch (Exception e) {
                message.put("message", "The File Doesn't Exist");
                return new ResponseEntity<JSONObject>(message, HttpStatus.NOT_FOUND);
            }
        }
        else{
            message.put("message", "There's no Topology stored in Memory with this Id");
            return new ResponseEntity<JSONObject>(message, HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<JSONArray> queryDevices(String topologyId){
        Topology topology = getTopologyById(topologyId);
        if(topology.getId()!=null){
            JSONArray components = new JSONArray();
            convertComponents(topology, components);
            return new ResponseEntity<JSONArray>(components, HttpStatus.OK);
        }
        return new ResponseEntity<JSONArray>(new JSONArray(), HttpStatus.NOT_FOUND);
    }

    public  ResponseEntity<JSONObject> deleteTopology(String topologyId) {
        JSONObject message = new JSONObject();
        boolean deleted = false;
        for(Topology topology: topologies){
            if(topology.getId().equals(topologyId)){
                topologies.remove(topology);
                deleted = true;
                message.put("message", "Topology Deleted Successfully");
                return new ResponseEntity<JSONObject>(message, HttpStatus.OK);
            }
        }
        if(!deleted)
            message.put("message", "There's no Topology stored in Memory with this Id");
        return new ResponseEntity<JSONObject>(message, HttpStatus.NOT_FOUND);
    }

    public JSONArray queryTopologies(){
        JSONArray jsonTopologies = new JSONArray();
        for (Topology topology : topologies) {
            JSONObject jsonTopology = new JSONObject();
            JSONArray components = new JSONArray();
            jsonTopology.put("id", topology.getId());
            convertComponents(topology, components);
            jsonTopology.put("components", components);
            jsonTopologies.add(jsonTopology);
        }
        return jsonTopologies;
    }

    public ResponseEntity<JSONArray> queryDevicesToNetlist(String topologyId, String netlistId){
        Topology topology = getTopologyById(topologyId);
        JSONArray components = new JSONArray();
        if(topology.getId()!=null){
            for(TopologyComponent component: topology.getComponentList()){
                if(component.getNetlist().get("id").equals(netlistId)){
                    convertComponent(component, components);
                }
            }
            return new ResponseEntity<JSONArray>(components, HttpStatus.OK);
        }
        return new ResponseEntity<JSONArray>(new JSONArray(), HttpStatus.NOT_FOUND);
    }

}
