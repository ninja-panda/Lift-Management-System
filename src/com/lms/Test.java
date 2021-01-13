package com.lms;

import com.lms.core.ElevatorService;

import static com.lms.common.LMSConstants.*;

public class Test {

    public static void main(String[] args) {
        initEnvironmentVariables();
        ElevatorService elevatorService = new ElevatorService();
        //int[][] requests = {{3, 0}};
        int[][] requests = {{0, 4}, {2, 6}, {3, 0}};

        //int[][] requests = {{0, 4}, {2, 6}};
        //int[][] requests = {{3, 0}};
        elevatorService.processRequest(requests);

        //int[][] requests1 = {{0, 4}, {2, 6}};
        //elevatorService.processRequest(requests1);
    }

    private static void initEnvironmentVariables() {
        ELEVATORS = 2;
        MIN_FLOOR = 0;
        MAX_FLOOR = 7;
    }
}
