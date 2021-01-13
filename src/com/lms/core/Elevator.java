package com.lms.core;

import com.lms.common.LMSConstants.DIRECTION;
import com.lms.common.LMSConstants.STATE;
import com.lms.exception.ElevatorOperationException;

import static com.lms.common.LMSConstants.MAX_FLOOR;
import static com.lms.common.LMSConstants.MIN_FLOOR;
import static com.lms.common.LMSConstants.STATE.CLOSE;
import static com.lms.common.LMSConstants.STATE.OPEN;

public class Elevator {

    private String id;
    private int currentFloor = 0;
    private int currentMaximum;
    private int currentMinimum;
    private boolean[] stops;
    private boolean chosen;
    private boolean moving = false;
    private boolean biDirectional = false;
    private STATE state = STATE.CLOSE;
    private DIRECTION direction = DIRECTION.NONE;

    public Elevator(String id) {
        this.id = id;
        this.stops = new boolean[MAX_FLOOR + 1];
    }

    public String getId() {
        return this.id;
    }

    public STATE getState() {
        return this.state;
    }

    public Integer getCurrentFloor() {
        return this.currentFloor;
    }

    public boolean isMoving() {
        return this.moving;
    }

    public boolean hasStop(int floorNo) {
        return stops[floorNo];
    }

    public DIRECTION getDirection() {
        return direction;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public boolean isBiDirectional() {
        return biDirectional;
    }

    public void setBiDirectional(boolean biDirectional) {
        this.biDirectional = biDirectional;
    }

    public int getCurrentGoalFloor() {
        if (direction == DIRECTION.UP) return this.currentMaximum;
        if (direction == DIRECTION.DOWN) return this.currentMinimum;
        return -1;
    }

    public void moveNext() {
        if (!isMoving()) {
            moving = true;
        }
        if (direction == DIRECTION.UP) {
            if (currentFloor != MAX_FLOOR) {
                currentFloor += 1;
                return;
            }
        }
        if (direction == DIRECTION.DOWN) {
            if (currentFloor != MIN_FLOOR) {
                currentFloor -= 1;
                return;
            }
        }
        return;
    }

    public void setGoalFloor(int goalFloor) {
        if ((goalFloor < MIN_FLOOR) || (goalFloor > MAX_FLOOR)) {
            throw new ElevatorOperationException("Invalid Move Request");
        }
        if (currentFloor == goalFloor) return;
        /**
         * When we are updating the destination Floor. A lift going from [0-3] and be chosen from between (1st Floor)
         * and the destination can be updated to 5th floor
         */
        if (goalFloor > currentFloor) {
            if (goalFloor > currentMaximum) {
                currentMaximum = goalFloor;
            }
            direction = (DIRECTION.UP);

        }
        if (goalFloor < currentFloor) {
            if (goalFloor < currentMinimum) {
                currentMinimum = goalFloor;
            }
            direction = DIRECTION.DOWN;
        }
    }

    public void addStop(int source, int destination) {
        if (source < MIN_FLOOR || source > MAX_FLOOR || destination < MIN_FLOOR || destination > MAX_FLOOR) {
            throw new ElevatorOperationException("Invalid Move Request");
        }
        stops[source] = true;
        stops[destination] = true;
    }

    public void reset() {
        this.chosen = false;
        this.moving = false;
        this.currentFloor = 0;
        this.currentMaximum = 0;
        this.currentMinimum = 0;
        this.state = STATE.CLOSE;
        this.biDirectional = false;
        this.direction = DIRECTION.NONE;
        this.stops = new boolean[MAX_FLOOR + 1];
    }

    public void moveToFloor(int floor) {
        if ((floor < MIN_FLOOR) || (floor > MAX_FLOOR)) {
            throw new ElevatorOperationException("Invalid Move Request");
        }
        reset();
        currentFloor = floor;
    }

    public int changeState() {
        state = state == CLOSE ? OPEN : CLOSE;
        return 1;
    }

    public void changeDirection() {
        direction = direction == DIRECTION.UP ? DIRECTION.DOWN : DIRECTION.UP;
    }

    public int goUp() {
        int time = 0;
        int i = currentMinimum;
        while (i <= currentMaximum) {
            System.out.println("T = " + time);
            if (this.hasStop(i)) {
                this.changeState();
                printElevatorStatus(false);
                if (state == CLOSE) {
                    if (currentFloor < currentMaximum) {
                        this.moveNext();
                    }
                    i++;
                }
            } else {
                printElevatorStatus(false);
                if (this.currentFloor < currentMaximum) {
                    this.moveNext();
                }
                i++;
            }
            time++;
        }
        if (!this.isBiDirectional()) {
            System.out.println(this.id + ":" + time + " SECONDS");
            System.out.println("---------------------------------");
        }
        return time;
    }

    public void comeDown(int time) {
        int i = currentMaximum;
        while (i >= currentMinimum) {
            if (i == currentMaximum) {
                moveNext();
                i--;
            } else {
                System.out.println("T = " + time);
                if (this.hasStop(i)) {
                    this.changeState();
                    printElevatorStatus(false);
                    if (state == CLOSE) {
                        this.moveNext();
                        i--;
                    }
                } else {
                    printElevatorStatus(false);
                    this.moveNext();
                    i--;
                }
                time++;
            }
        }
        System.out.println(this.id + ":" + time + " SECONDS");
    }

    public void printElevatorStatus(boolean printDebugInfo) {
        if (!printDebugInfo) {
            System.out.println(this.getId() + " --> " + this.getCurrentFloor() + "(" + this.getState().name() + ")\n");
        } else {
            System.out.println(this);
        }
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "id='" + id + '\'' +
                ", currentFloor=" + currentFloor +
                ", currentMaximum=" + currentMaximum +
                ", currentMinimum=" + currentMinimum +
                ", moving=" + moving +
                ", state=" + state +
                ", direction=" + direction +
                '}';
    }
}
