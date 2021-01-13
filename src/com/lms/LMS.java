package com.lms;

import com.lms.core.ElevatorPanel;

import static com.lms.common.LMSConstants.*;

public class LMS {

    public static void main(String[] args) {
        initEnvironmentVariables();
        ElevatorPanel elevatorPanel = new ElevatorPanel();
        //int[][] requests = {{0, 4}, {2, 6}};
        //int[][] requests = {{3, 0}};
        //int[][] requests = {{0, 4}, {2, 6}, {3, 0}};
        int[][] requests = {{0, 7}, {4, 6}, {3, 0}};
        //int[][] requests = {{0, 7}, {4, 6}, {3, 0}, {5, 0}};

        elevatorPanel.processRequest(requests);
    }

    private static void initEnvironmentVariables() {
        ELEVATORS = 2;
        MIN_FLOOR = 0;
        MAX_FLOOR = 8;
    }
}
