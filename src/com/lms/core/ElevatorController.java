package com.lms.core;

import com.lms.common.LMSConstants.DIRECTION;

import java.util.HashMap;
import java.util.Map;

import static com.lms.common.HelperUtil.getElevatorId;

public class ElevatorController {

    private int minFloor;
    private int maxFloor;
    private int numberOfElevators;
    private Map<String, Elevator> elevatorMap;

    private void initElevators() {
        elevatorMap = new HashMap<>(this.numberOfElevators);
        for (int i = 0; i < this.numberOfElevators; i++) {
            String elevatorId = getElevatorId(i);
            Elevator elevator = new Elevator(elevatorId);
            elevatorMap.put(elevatorId, elevator);
        }
    }

    public ElevatorController(int minFloor, int maxFloor, int numberOfElevators) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.numberOfElevators = numberOfElevators;
        initElevators();
    }

    public Elevator getElevator(String elevatorId) {
        return elevatorMap.get(elevatorId);
    }

    public String chooseElevator(int source, int destination, DIRECTION direction, boolean biDirectional) {
        int minDistance = maxFloor;
        Elevator closestElevator = null;
        int cur_distance = 0;
        for (Elevator elevator : elevatorMap.values()) {
            // For go up and come down elevators
            if (biDirectional) {
                if (elevator.isChosen()) {
                    continue;
                }
                if (elevator.getCurrentFloor() == 0 && !elevator.isMoving()) {
                    closestElevator = elevator;
                    closestElevator.setBiDirectional(true);
                    break;
                }
            } else if (((direction == DIRECTION.UP) && (elevator.getDirection() == DIRECTION.UP) && (source >= elevator.getCurrentFloor())) ||
                    ((direction == DIRECTION.DOWN) && (elevator.getDirection() == DIRECTION.DOWN) && (source <= elevator.getCurrentFloor()))) {
                cur_distance = calculateRoute(source, elevator.getCurrentFloor());
            } else {
                cur_distance = calculateRoute(source, elevator.getCurrentFloor(), elevator.getCurrentGoalFloor());
            }
            if (cur_distance < minDistance) {
                minDistance = cur_distance;
                closestElevator = elevator;
            }
        }
        closestElevator.setChosen(true);
        closestElevator.setGoalFloor(destination);
        closestElevator.addStop(source, destination);
        return closestElevator.getId();
    }

    public void changeDirection(Elevator elevator) {
        elevator.changeDirection();
    }

    private int calculateRoute(int aFloor, int bFloor) {
        return Math.abs(aFloor - bFloor);
    }

    private int calculateRoute(int source, int currentFloor, int currentGoal) {
        return calculateRoute(source, currentGoal) + calculateRoute(currentFloor, currentGoal);
    }
}
