package com.bing.info;

import com.bing.image.Permission;

public final class PermissionInfo{
    private int id;
    private Permission permission;
    private static PermissionInfo permissionInfo = new PermissionInfo();

    private PermissionInfo(){}

    public static PermissionInfo getInstance(int id, Permission permission){
        permissionInfo.setId(id);
        permissionInfo.setPermission(permission);
        return permissionInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPermission() {
        return permission.getCode();
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "PermissionInfo{" +
                "id=" + id +
                ", permission=" + permission.getCode() +
                '}';
    }
}
