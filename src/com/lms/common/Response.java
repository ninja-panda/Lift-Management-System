package com.lms.common;

import sun.reflect.generics.tree.Tree;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Response {

    Set<String> timeUnits;
    Map<String, Set<String>> elevatorState;

    public Response() {
        this.timeUnits = new TreeSet<>();
        this.elevatorState = new LinkedHashMap<>();
    }

    public void addElevatorStatus(String key, String status) {
        Set<String> statusSet = elevatorState.get(key);
        if (statusSet == null) {
            statusSet = new TreeSet<>();
            elevatorState.put(key, statusSet);
        }
        statusSet.add(status);
    }

    public void addTimeUnit(String time) {
        timeUnits.add(time);
    }

    public void print() {
        for (String key : elevatorState.keySet()) {
            Set<String> curStatus = elevatorState.get(key);
            System.out.println(key + " :");
            System.out.println(curStatus);
        }
        for (String time : timeUnits) {
            System.out.println(time);
        }
    }
}
