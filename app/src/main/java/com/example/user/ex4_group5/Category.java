package com.example.user.ex4_group5;

import org.json.JSONArray;

/**
 * Created by etiram on 17/10/2015.
 */
public class Category {

    private int rest_id;
    private String category_name;
    private JSONArray items;

    public Category(){
        this.rest_id = 0;
        this.category_name = "";
    }
    public Category(int rest_id , String category_name , JSONArray items){
        this.rest_id = rest_id;
        this.category_name = category_name;
        this.items = items;
    }

    public int getRestId(){
        return this.rest_id;
    }
    public String getCategoryName(){
        return this.category_name;
    }
    public JSONArray getItems(){
        return this.items;
    }

}
