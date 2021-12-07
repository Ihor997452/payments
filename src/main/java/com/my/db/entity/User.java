package com.my.db.entity;

import com.my.db.constants.Roles;
import com.my.db.constants.Status;

public class User extends Entity {
    private String email;
    private String name;
    private String surname;
    private String password;
    private String salt;
    private Roles roleId;
    private Status statusId;

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getRoleId() {
        return this.roleId.getValue();
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }

    public int getStatusId() {
        return this.statusId.getValue();
    }

    public void setStatusId(Status statusId) {
        this.statusId = statusId;
    }

    public String toString() {
        return "User{id=" + this.id + ", email='" + this.email + '\'' + ", name='" + this.name + '\'' + ", surname='" + this.surname + '\'' + ", password='" + this.password + '\'' + ", salt='" + this.salt + '\'' + ", roleId=" + this.roleId + ", statusId=" + this.statusId + '}';
    }
}


