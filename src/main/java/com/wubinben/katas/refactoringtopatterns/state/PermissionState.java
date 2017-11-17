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

    public void claimBy(SystemAdmin admin, SystemPermission systemPermission) {
        if (!systemPermission.getState().equals(REQUESTED) && !systemPermission.getState().equals(UNIX_REQUESTED))
            return;
        systemPermission.willBeHandledBy(admin);
        if (systemPermission.getState().equals(REQUESTED))
            systemPermission.setPermissionState(CLAIMED);
        else if (systemPermission.getState().equals(UNIX_REQUESTED))
            systemPermission.setPermissionState(UNIX_CLAIMED);
    }

    public void deniedBy(SystemAdmin admin, SystemPermission systemPermission) {
        if (!systemPermission.getState().equals(CLAIMED) && !systemPermission.getState().equals(UNIX_CLAIMED))
            return;
        if (!systemPermission.admin.equals(admin))
            return;
        systemPermission.isGranted = false;
        systemPermission.isUnixPermissionGranted = false;
        systemPermission.setPermissionState(DENIED);
        systemPermission.notifyUserOfPermissionRequestResult();
    }

    public void grantedBy(SystemAdmin admin, SystemPermission systemPermission) {
        if (!systemPermission.getState().equals(CLAIMED) && !systemPermission.getState().equals(UNIX_CLAIMED))
            return;
        if (!systemPermission.admin.equals(admin))
            return;

        if (systemPermission.profile.isUnixPermissionRequired() && systemPermission.getState().equals(UNIX_CLAIMED))
            systemPermission.isUnixPermissionGranted = true;
        else if (systemPermission.profile.isUnixPermissionRequired() && !systemPermission.profile.isUnixPermissionGranted()) {
            systemPermission.setPermissionState(UNIX_REQUESTED);
            systemPermission.notifyUnixAdminsOfPermissionRequest();
            return;
        }
        systemPermission.setPermissionState(GRANTED);
        systemPermission.isGranted = true;
        systemPermission.notifyUserOfPermissionRequestResult();
    }
}
