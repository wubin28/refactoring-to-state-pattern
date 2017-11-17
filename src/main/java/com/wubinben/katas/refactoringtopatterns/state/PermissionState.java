package com.wubinben.katas.refactoringtopatterns.state;

public abstract class PermissionState {
    private final String stateName;

    public PermissionState(String stateName) {
        this.stateName = stateName;
    }
    public final static PermissionState REQUESTED = new PermissionRequested();
    public final static PermissionState CLAIMED = new PermissionClaimed();
    public final static PermissionState GRANTED = new PermissionGranted();
    public final static PermissionState DENIED = new PermissionDenied();
    public static final PermissionState UNIX_REQUESTED = new UnixPermissionRequested();
    public static final PermissionState UNIX_CLAIMED = new UnixPermissionClaimed();

    @Override
    public String toString() {
        return "PermissionState{" +
                "stateName='" + stateName + '\'' +
                '}';
    }

    public void claimedBy(SystemAdmin admin, SystemPermission systemPermission) {}

    public void deniedBy(SystemAdmin admin, SystemPermission systemPermission) {}

    public void grantedBy(SystemAdmin admin, SystemPermission systemPermission) {}
}
