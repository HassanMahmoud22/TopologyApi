package com.example.topologyapi.Connection;

import com.example.topologyapi.Topology.Topology;
import com.example.topologyapi.TopologyComponent.TopologyComponent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

    public void readJSON(String fileName) {
        JSONParser parser = new JSONParser();
        Topology topology = new Topology();
        try {
            Object obj = parser.parse(new FileReader(fileName));
            JSONObject jsonObject = (JSONObject)obj;
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
            System.out.println("Topology is read Successfully");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void convertComponents(Topology topology, JSONArray components) {
        for(TopologyComponent component: topology.getComponentList()){
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
    }
    private Topology getTopologyById(String topologyId){
        Topology nullTopology = new Topology();
        for(Topology topology: topologies){
            if(topology.getId().equals(topologyId))
                return topology;
        }
        return nullTopology;
    }
    public void writeJSON(String topologyId) {
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
                System.out.println("The Topology is written successfully");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
            System.out.println("There's no Topology stored in Memory with this Id");
    }
    public JSONArray queryDevices(String topologyId){
        Topology topology = getTopologyById(topologyId);
        if(topology.getId()!=null){
            JSONArray components = new JSONArray();
            convertComponents(topology, components);
            return components;
        }
        return new JSONArray();
    }
    public void deleteTopology(String topologyId) {
        boolean deleted = false;
        for(Topology topology: topologies){
            if(topology.getId().equals(topologyId)){
                topologies.remove(topology);
                deleted = true;
                System.out.println("Topology Deleted Successfully");
                break;
            }
        }
        if(!deleted)
            System.out.println("There's no Topology stored in Memory with this Id");
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
}
