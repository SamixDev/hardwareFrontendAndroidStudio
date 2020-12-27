package com.godsamix.hardware.Controllers;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class responseController {
    @SerializedName("status")
    private  String status;
    @SerializedName("message")
    private List<HardListController> message;

    public responseController(String status, String message) {
        this.status = status;
        this.status = status;
    }

    public responseController() {

    }


    public String getStatus() {
        return status;
    }

    public List<HardListController> getMessage() {
        return message;
    }


}

