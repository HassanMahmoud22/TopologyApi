package com.example.topologyapi.TopologyComponent;

import java.util.HashMap;
import java.util.Map;

public class TopologyComponent {
    String id;
    String type;
    String name;
    Map<String, String> componentDetails = new HashMap<>();
    Map<String, String> netlist = new HashMap<>();

    public String getId() {
        return id;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String>  getComponentDetails() {
        return componentDetails;
    }

    public void setComponentDetails(Map<String, String>  componentDetails) {
        this.componentDetails = componentDetails;
    }

    public Map<String, String> getNetlist() {
        return netlist;
    }

    public void setNetlist( Map<String, String> netlist) {
        this.netlist = netlist;
    }

    @Override
    public String toString() {
        return "component id " + getId() + " \n component type: " + getType() + " \n componentdetails: " + getComponentDetails()
                + " \n componentNetlist: " + getNetlist();
    }

}
