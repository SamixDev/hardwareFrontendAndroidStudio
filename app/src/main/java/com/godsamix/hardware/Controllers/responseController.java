package com.godsamix.hardware.Controllers;

import com.google.gson.annotations.SerializedName;

public class responseController {
    @SerializedName("status")
    private  String status;
    @SerializedName("message")
    private  String message;

    public responseController(String status, String message) {
        this.status = status;
        this.status = status;
    }

    public responseController() {

    }


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


}

