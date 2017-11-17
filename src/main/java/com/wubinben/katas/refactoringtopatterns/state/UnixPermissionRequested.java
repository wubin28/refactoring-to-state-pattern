package com.wubinben.katas.refactoringtopatterns.state;

public class UnixPermissionRequested extends PermissionState {
    public UnixPermissionRequested() {
        super("UNIX_REQUESTED");
    }
}
