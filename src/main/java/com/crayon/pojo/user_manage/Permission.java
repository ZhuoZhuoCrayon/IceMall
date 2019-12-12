package com.crayon.pojo.user_manage;

import java.io.Serializable;

public class Permission implements Serializable {
    private Integer id;
    private String permission;
    private String perms;
    public Permission(){}
    public Permission(Integer id,String permission,String perms){
        this.id = id;
        this.permission = permission;
        this.perms = perms;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getId() {
        return id;
    }

    public String getPermission() {
        return permission;
    }

    public String getPerms() {
        return perms;
    }
}
