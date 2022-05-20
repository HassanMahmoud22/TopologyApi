package com.example.topologycomponent;

import java.util.HashMap;
import java.util.Map;

public class TopologyComponent {
    String id;
    String type;
    String name;
    Map<String, String> componentDetails = new HashMap<>();
    Map<String, String> netList = new HashMap<>();

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

    public Map<String, String> getNetList() {
        return netList;
    }

    public void setNetList( Map<String, String> netList) {
        this.netList = netList;
    }

    @Override
    public String toString() {
        return "component id " + getId() + " \n component type: " + getType() + " \n componentDetails: " + getComponentDetails()
                + " \n componentNetList: " + getNetList();
    }

}
