package com.wubinben.katas.refactoringtopatterns.state;

public class PermissionClaimed extends PermissionState {
    public PermissionClaimed() {
        super("CLAIMED");
    }

    public void deniedBy(SystemAdmin admin, SystemPermission systemPermission) {
        if (!systemPermission.admin.equals(admin))
            return;
        systemPermission.isGranted = false;
        systemPermission.isUnixPermissionGranted = false;
        systemPermission.setPermissionState(DENIED);
        systemPermission.notifyUserOfPermissionRequestResult();
    }

    public void grantedBy(SystemAdmin admin, SystemPermission systemPermission) {
        if (!systemPermission.admin.equals(admin))
            return;

        if (systemPermission.profile.isUnixPermissionRequired() && !systemPermission.profile.isUnixPermissionGranted()) {
            systemPermission.setPermissionState(UNIX_REQUESTED);
            systemPermission.notifyUnixAdminsOfPermissionRequest();
            return;
        }
        systemPermission.setPermissionState(GRANTED);
        systemPermission.isGranted = true;
        systemPermission.notifyUserOfPermissionRequestResult();
    }
}
