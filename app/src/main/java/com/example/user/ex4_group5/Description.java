package com.example.user.ex4_group5;

/**
 * Created by etiram on 17/10/2015.
 */
public class Description {

    private int rest_id;
    private String item;
    private int price;
    private String desc;

    public Description(){
        this.rest_id = 0;
        this.item = "";
        this.price = 0;
        this.desc = "";
    }
    public Description(int rest_id , String item , int price , String desc){
        this.rest_id = rest_id;
        this.item = item;
        this.price = price;
        this.desc = desc;
    }
    public int getRestId(){
        return this.rest_id;
    }
    public String getItem(){
        return this.item;
    }
    public int getPrice(){
        return this.price;
    }
    public String getDesc(){
        return this.desc;
    }
    
}
