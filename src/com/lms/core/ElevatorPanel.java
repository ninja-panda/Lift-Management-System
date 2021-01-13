package com.lms.core;

import com.lms.common.Response;

import java.util.Set;
import java.util.TreeSet;

import static com.lms.common.LMSConstants.*;

public class ElevatorPanel {

    private ElevatorController elevatorController = null;

    public ElevatorPanel() {
        elevatorController = new ElevatorController(MIN_FLOOR, MAX_FLOOR, ELEVATORS);
    }

    public void processRequest(int[][] requests) {
        Set<String> elevators = new TreeSet<>();
        for (int[] request : requests) {
            int source = request[0];
            int destination = request[1];

            if (source < destination) { // {0, 4}, {2, 6}
                DIRECTION direction = DIRECTION.UP;
                String elevatorId = elevatorController.chooseElevator(source, destination, direction, false);
                elevators.add(elevatorId);
            } else if (source > destination && destination == 0) { // 3 0
                int temp_source = 0;
                int temp_destination = source;
                DIRECTION direction = DIRECTION.UP;
                String elevatorId = elevatorController.chooseElevator(temp_source, temp_destination, direction, true);
                elevators.add(elevatorId);
            }else if(source > destination){
                DIRECTION direction = DIRECTION.DOWN;
                String elevatorId = elevatorController.chooseElevator(source, destination, direction, false);
                elevators.add(elevatorId);
            }
        }

        Response response = new Response();
        for (String elevatorId : elevators) {
            Elevator elevator = elevatorController.getElevator(elevatorId);
            int time = elevator.goUp(response);
            if (elevator.isBiDirectional()) {
                elevatorController.changeDirection(elevator);
                elevator.comeDown(time, response);
            }
            elevator.reset();
        }
        response.print();
    }
}
