package com.wubinben.katas.refactoringtopatterns.state;

public class PermissionState {
    private final String stateName;

    public PermissionState(String stateName) {
        this.stateName = stateName;
    }
    public final static PermissionState REQUESTED = new PermissionState("REQUESTED");
    public final static PermissionState CLAIMED = new PermissionState("CLAIMED");
    public final static PermissionState GRANTED = new PermissionState("GRANTED");
    public final static PermissionState DENIED = new PermissionState("DENIED");
    public static final PermissionState UNIX_REQUESTED = new PermissionState("UNIX_REQUESTED");
    public static final PermissionState UNIX_CLAIMED = new PermissionState("UNIX_CLAIMED");

    @Override
    public String toString() {
        return "PermissionState{" +
                "stateName='" + stateName + '\'' +
                '}';
    }
}
