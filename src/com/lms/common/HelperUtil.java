package com.lms.common;

public class HelperUtil {

    private static final String elName = "LIFT ";

    public static String getElevatorId(int seqNo) {
        StringBuilder nameBuilder = new StringBuilder(elName);
        nameBuilder.append(seqNo + 1);
        return nameBuilder.toString();
    }
}
