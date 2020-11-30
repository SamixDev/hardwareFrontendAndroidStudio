package com.godsamix.hardware.Controllers;

import com.google.gson.annotations.SerializedName;

public class HardListController {
    @SerializedName("Code")
    private  String Code;
    @SerializedName("Name")
    private  String name;
    @SerializedName("Manufacturer")
    private  String Manufacturer;
    @SerializedName("ModelNb")
    private  String ModelNb;
    @SerializedName("Image")
    private  String image;

    public HardListController(String Code, String name, String Manufacturer, String ModelNb) {
        this.Code = Code;
        this.name = name;
        this.Manufacturer = Manufacturer;
        this.ModelNb = ModelNb;
    }

    public HardListController() {

    }

//    public void setCode(String Code) {
//        this.Code = Code;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setManufacturer(String Manufacturer) {
//        this.Manufacturer = Manufacturer;
//    }
//
//    public void setModelNb(String ModelNb) {
//        this.ModelNb = ModelNb;
//    }
    public String getCode() {
        return Code;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return Manufacturer;
    }

    public String getModelNb() {
        return ModelNb;
    }

    public String getImage() {
        return image;
    }

}
