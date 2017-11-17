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

    public static class PermissionClaimed extends PermissionState {
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

    public static class PermissionDenied extends PermissionState {
        public PermissionDenied() {
            super("DENIED");
        }
    }

    public static class PermissionGranted extends PermissionState {
        public PermissionGranted() {
            super("GRANTED");
        }
    }

    public static class PermissionRequested extends PermissionState {
        public PermissionRequested() {
            super("REQUESTED");
        }

        public void claimedBy(SystemAdmin admin, SystemPermission systemPermission) {
            systemPermission.willBeHandledBy(admin);
            systemPermission.setPermissionState(CLAIMED);
        }

    }

    public static class UnixPermissionClaimed extends PermissionState {
        public UnixPermissionClaimed() {
            super("UNIX_CLAIMED");
        }
    }

    public static class UnixPermissionRequested extends PermissionState {
        public UnixPermissionRequested() {
            super("UNIX_REQUESTED");
        }
    }
}
