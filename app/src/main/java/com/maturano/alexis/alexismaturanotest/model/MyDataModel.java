package com.maturano.alexis.alexismaturanotest.model;

/**
 * Created by alexismaturano on 5/26/17.
 */

public class MyDataModel {
    private String id;
    private String id_estate;
    private String id_country;
    private String latitude;
    private String longitude;
    private String name_estate;
    private String name_city;
    private String name_country;

    public String getId() {
        return id;
    }

    public String getId_estate() {
        return id_estate;
    }

    public String getId_country() {
        return id_country;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getName_estate() {
        return name_estate;
    }

    public String getName_city() {
        return name_city;
    }

    public String getName_country() {
        return name_country;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setId_country(String id_country) {
        this.id_country = id_country;
    }

    public void setId_estate(String id_estate) {
        this.id_estate = id_estate;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setName_city(String name_city) {
        this.name_city = name_city;
    }

    public void setName_country(String name_country) {
        this.name_country = name_country;
    }

    public void setName_estate(String name_estate) {
        this.name_estate = name_estate;
    }
}
