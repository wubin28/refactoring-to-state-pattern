package com.wubinben.katas.refactoringtopatterns.state;

import static com.wubinben.katas.refactoringtopatterns.state.PermissionState.*;

public class SystemPermission {
    private SystemProfile profile;
    private SystemUser requestor;
    private SystemAdmin admin;
    private boolean isGranted;
    private boolean isUnixPermissionGranted;
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
        if (!getState().equals(REQUESTED) && !getState().equals(UNIX_REQUESTED))
            return;
        willBeHandledBy(admin);
        if (getState().equals(REQUESTED))
            setPermissionState(CLAIMED);
        else if (getState().equals(UNIX_REQUESTED))
            setPermissionState(UNIX_CLAIMED);
    }

    private void willBeHandledBy(SystemAdmin admin) {
        this.admin = admin;
    }

    public void deniedBy(SystemAdmin admin) {
        if (!getState().equals(CLAIMED) && !getState().equals(UNIX_CLAIMED))
            return;
        if (!this.admin.equals(admin))
            return;
        isGranted = false;
        isUnixPermissionGranted = false;
        setPermissionState(DENIED);
        notifyUserOfPermissionRequestResult();
    }

    private void notifyUserOfPermissionRequestResult() {

    }

    public void grantedBy(SystemAdmin admin) {
        if (!getState().equals(CLAIMED) && !getState().equals(UNIX_CLAIMED))
            return;
        if (!this.admin.equals(admin))
            return;

        if (profile.isUnixPermissionRequired() && getState().equals(UNIX_CLAIMED))
            isUnixPermissionGranted = true;
        else if (profile.isUnixPermissionRequired() && !profile.isUnixPermissionGranted()) {
            setPermissionState(UNIX_REQUESTED);
            notifyUnixAdminsOfPermissionRequest();
            return;
        }
        setPermissionState(GRANTED);
        isGranted = true;
        notifyUserOfPermissionRequestResult();
    }

    private void notifyUnixAdminsOfPermissionRequest() {

    }

    public void setPermissionState(PermissionState permissionState) {
        this.permissionState = permissionState;
    }

    public PermissionState getState() {
        return this.permissionState;
    }

    public boolean isGranted() {
        return this.isGranted;
    }
}
