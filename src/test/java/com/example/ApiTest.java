package com.example;

import com.example.topologyoperations.TopologyOperationsController;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class ApiTest {
    private  final TopologyOperationsController topologyOperationsController = new TopologyOperationsController();

    @Test
    @DisplayName("[Test1] Read a Topology from Json File and Store it in Memory")
    public void readFromJsonTest1() throws FileNotFoundException, ParseException {
        Assertions.assertNotNull(topologyOperationsController.readJSON("topology.json"));

    }
/*
    @Test
    @DisplayName("[Test2] Read a Topology (top2) from Json File & Store it in Memory")
    public void readJsonTest2() {
        Topology topology = api.readJSON("top2");
        Assertions.assertNotNull(topology);
    }

    @Test
    @DisplayName("[Test_3] Read a Topology (newGenJsonFileFrom_2) from Json File & Store it in Memory")
    public void readJsonTest3() {
        Topology topology = api.readJSON("newGenJsonFileFrom_2");
        Assertions.assertNull(topology); // with the same id so can't read
    }

    @Test
    @DisplayName("[Test_4] Read a Topology with Json file name not found")
    public void readJsonTest4() {
        Topology topology = api.readJSON("JsonFileNotFound");
        Assertions.assertNull(topology);
    }

    @Test
    @DisplayName("[Test_5] Read a Topology with Empty File Name")
    public void readJsonTest5() {
        Topology topology = api.readJSON("");
        Assertions.assertNull(topology);
    }




    @Test
    @DisplayName("[Test_1] Write a Topology (top1) from the Memory to the Json File name (topTest)")
    public void writeJsonTest1() {
        boolean jsonIsSaved = api.writeJSON("top1", "topTest");
        Assertions.assertTrue(jsonIsSaved);
    }

    @Test
    @DisplayName("[Test_2] Write a Topology (top2) from the Memory to the Json File name (topTest)")
    public void writeJsonTest2() {
        boolean jsonIsSaved = api.writeJSON("top2", "topTest");
        Assertions.assertTrue(jsonIsSaved);
    }

    @Test
    @DisplayName("[Test_3] Write a Topology that not found in the memory")
    public void writeJsonTest3() {
        boolean jsonIsSaved = api.writeJSON("topologyIDNotFoundInMemory", "topTest");
        Assertions.assertFalse(jsonIsSaved);
    }

    @Test
    @DisplayName("[Test_4] Write a Topology with Empty Topology ID")
    public void writeJsonTest4() {
        boolean jsonIsSaved = api.writeJSON("", "topTest");
        Assertions.assertFalse(jsonIsSaved);
    }

    @Test
    @DisplayName("[Test_5] Write a Topology with Empty file name")
    public void writeJsonTest5() {
        boolean jsonIsSaved = api.writeJSON("top1", "");
        Assertions.assertTrue(jsonIsSaved); // will save fle with name (.Json)
    }




    @Test
    @DisplayName("[Test_1] Queries Topologies in memory")
    public void queryTopologiesTest1() {
        API.currentTopologies.clear();
        Topology topology = api.readJSON("top1");
        ArrayList<Topology> currentTopologies = api.queryTopologies();
        Assertions.assertTrue(currentTopologies.size() > 0);
    }

    @Test
    @DisplayName("[Test_2] Queries Topologies in memory")
    public void queryTopologiesTest2() {
        ArrayList<Topology> currentTopologies = api.queryTopologies();
        Assertions.assertNotEquals(0, currentTopologies.size());
    }




    @Test
    @DisplayName("[Test_1] Delete Topology (top1) From The Memory")
    public void deleteTopologyTest1() {
        boolean topologyDeleted = api.deleteTopology("top1");
        Assertions.assertTrue(topologyDeleted);
    }

    @Test
    @DisplayName("[Test_2] Delete Topology (top1) From The Empty Memory")
    public void deleteTopologyTest2() {
        boolean topologyDeleted = api.deleteTopology("top1");
        Assertions.assertFalse(topologyDeleted);
    }

    @Test
    @DisplayName("[Test_3] Delete Topology (top2) From The Memory")
    public void deleteTopologyTest3() {
        Topology topology = api.readJSON("top2");
        boolean topologyDeleted = api.deleteTopology("top2");
        Assertions.assertTrue(topologyDeleted);
    }

    @Test
    @DisplayName("[Test_4] Delete Topology (top2) From The Empty Memory")
    public void deleteTopologyTest4() {
        boolean topologyDeleted = api.deleteTopology("top2");
        Assertions.assertFalse(topologyDeleted);
    }




    @Test
    @DisplayName("[Test_1] Query Devices in Topology (top1) From The Memory")
    public void queryDevicesTest1() {
        Topology topology = api.readJSON("top1");
        ArrayList<Component> selectedComponents = api.queryDevices("top1");
        Assertions.assertTrue(selectedComponents.size() > 0);
    }

    @Test
    @DisplayName("[Test_2] Query Devices in Topology (top2) From The Memory")
    public void queryDevicesTest2() {
        Topology topology = api.readJSON("top2");
        ArrayList<Component> selectedComponents = api.queryDevices("top2");
        Assertions.assertTrue(selectedComponents.size() > 0);
    }

    @Test
    @DisplayName("[Test_3] Query Devices in WRONG Topology ID From The Memory")
    public void queryDevicesTest3() {
        ArrayList<Component> selectedComponents = api.queryDevices("top3");
        Assertions.assertNull(selectedComponents);
    }

    @Test
    @DisplayName("[Test_4] Query Devices in Empty Topology ID From The Memory")
    public void queryDevicesTest4() {
        ArrayList<Component> selectedComponents = api.queryDevices("");
        Assertions.assertNull(selectedComponents);
    }




    @Test
    @DisplayName("[Test_1] Query Devices in top1 with netlist ID drain in From The Memory")
    public void queryDevicesWithNetListNodeTest1() {
        ArrayList<Component> selectedComponents = api.queryDevicesWithNetListNode("top1", "drain");
        Assertions.assertTrue(selectedComponents.size() > 0);
    }

    @Test
    @DisplayName("[Test_2] Query Devices in top2 with netlist ID gate in From The Memory")
    public void queryDevicesWithNetListNodeTest2() {
        ArrayList<Component> selectedComponents = api.queryDevicesWithNetListNode("top2", "gate");
        Assertions.assertFalse(selectedComponents.size() > 0);
    }

    @Test
    @DisplayName("[Test_3] Query Devices in topologyIDNotFound with netlist ID drain in From The Memory")
    public void queryDevicesWithNetListNodeTest3() {
        ArrayList<Component> selectedComponents = api.queryDevicesWithNetListNode("topologyIDNotFound"
                , "drain");
        Assertions.assertNull(selectedComponents);
    }

    @Test
    @DisplayName("[Test_4] Query Devices in topologyIDNotFound with netlist ID gate in From The Memory")
    public void queryDevicesWithNetListNodeTest4() {
        ArrayList<Component> selectedComponents = api.queryDevicesWithNetListNode("topologyIDNotFound"
                , "gate");
        Assertions.assertNull(selectedComponents);
    }

    @Test
    @DisplayName("[Test_5] Query Devices with Empty Topology ID with netlist ID gate in From The Memory")
    public void queryDevicesWithNetListNodeTest5() {
        ArrayList<Component> selectedComponents = api.queryDevicesWithNetListNode("", "gate");
        Assertions.assertNull(selectedComponents);
    }
*/
}