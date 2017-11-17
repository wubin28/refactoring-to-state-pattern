package com.wubinben.katas.refactoringtopatterns.state;

public class PermissionRequested extends PermissionState {
    public PermissionRequested() {
        super("REQUESTED");
    }

    public void claimBy(SystemAdmin admin, SystemPermission systemPermission) {
        systemPermission.willBeHandledBy(admin);
        systemPermission.setPermissionState(CLAIMED);
    }

}
