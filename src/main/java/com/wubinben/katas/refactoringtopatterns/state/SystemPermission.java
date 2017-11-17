package com.wubinben.katas.refactoringtopatterns.state;

import static com.wubinben.katas.refactoringtopatterns.state.PermissionState.*;

public class SystemPermission {
    SystemProfile profile;
    private SystemUser requestor;
    SystemAdmin admin;
    boolean isGranted;
    boolean isUnixPermissionGranted;
    private PermissionState permissionState;


    public SystemPermission(SystemUser requestor, SystemProfile profile) {
        this.requestor = requestor;
        this.profile = profile;
        setPermissionState(REQUESTED);
        isGranted = false;
        notifyAdminOfPermissionRequest();
    }

    private void notifyAdminOfPermissionRequest() {

    }

    public void claimedBy(SystemAdmin admin) {
        permissionState.claimedBy(admin, this);
    }

    void willBeHandledBy(SystemAdmin admin) {
        this.admin = admin;
    }

    public void deniedBy(SystemAdmin admin) {
        permissionState.deniedBy(admin, this);
    }

    void notifyUserOfPermissionRequestResult() {

    }

    public void grantedBy(SystemAdmin admin) {
        permissionState.grantedBy(admin, this);
    }

    void notifyUnixAdminsOfPermissionRequest() {

    }

    void setPermissionState(PermissionState permissionState) {
        this.permissionState = permissionState;
    }

    public PermissionState getState() {
        return this.permissionState;
    }

    public boolean isGranted() {
        return this.isGranted;
    }
}
