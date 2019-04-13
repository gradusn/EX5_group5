package com.example.user.ex4_group5;

import org.json.JSONArray;

/**
 * Created by User on 8/26/2015.
 */

public class Restaurant {
    private String description;
    private int rest_id;
    private String rest_name;
    private JSONArray category;
    public static int I=0;

    public Restaurant(){
        this.rest_id=0;
        this.rest_name = "";
        this.description = "";
    }

    public Restaurant(int rest_id, String rest_name,JSONArray category,String Description) {
        this.rest_id = rest_id;
        this.rest_name = rest_name;
        this.category = category;
        this.description = Description;
    }
    public int getRestId(){return this.rest_id ;}
    public String getRestName() {
        return this.rest_name;
    }
    public String getDescription() {
        return description;
    }
    public JSONArray getCategory(){return this.category;}
    @Override
    public String toString() {
        return "Restaurant [rest id=" + rest_id + ", name=" + rest_name + ",  Description=" + description + "]";
    }


}
