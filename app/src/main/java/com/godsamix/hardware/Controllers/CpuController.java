package com.godsamix.hardware.Controllers;

import com.google.gson.annotations.SerializedName;

public class CpuController{
    @SerializedName("id")
    private  String id;
    @SerializedName("name")
    private  String name;
    @SerializedName("email")
    private  String email;
    @SerializedName("gender")
    private  String gender;

    public CpuController(String id, String name, String email, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
    }

    public CpuController() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGender() {
        return gender;
    }
}
