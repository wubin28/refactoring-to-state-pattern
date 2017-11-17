package com.wubinben.katas.refactoringtopatterns.state;

import org.junit.Test;

import static org.junit.Assert.*;

public class SystemPermissionTest {
    @Test
    public void should() throws Exception {
        SystemPermission permission = new SystemPermission(new SystemUser(), new SystemProfile());
        final SystemAdmin admin = new SystemAdmin();
        permission.grantedBy(admin);

        assertEquals("requested", PermissionState.REQUESTED, permission.getState());
        assertEquals("not granted", false, permission.isGranted());

        permission.claimedBy(admin);
        permission.grantedBy(admin);

        assertEquals("granted", PermissionState.GRANTED, permission.getState());
        assertEquals("granted", true, permission.isGranted());
    }
}