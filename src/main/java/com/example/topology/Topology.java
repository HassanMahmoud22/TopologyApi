package com.example.topology;

import com.example.topologycomponent.TopologyComponent;

import java.util.ArrayList;
import java.util.List;

public class Topology {
    String id;
    List<TopologyComponent>componentList = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<TopologyComponent> getComponentList() {
        return componentList;
    }

    public void setComponent(TopologyComponent topologyComponent) {
        this.componentList.add(topologyComponent);
    }

    @Override
    public String toString() {
        return "topology id " + getId() + " \n topologyComponents: " + componentList.get(0) + "\n" + componentList.get(1) + "\n";
    }
}
