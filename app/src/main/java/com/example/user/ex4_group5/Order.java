package com.example.user.ex4_group5;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by etiram on 24/10/2015.
 */
public class Order {
    private static Order instance;
    private int rest_id;
    private int tbl_id;
    private List<ItemDescription> descriptionList = new ArrayList<ItemDescription>();
    private Order(Context context) {
        init(context);
    }

    private void init(Context context) {
    }

    public Order(int rest_id , int tbl_id  ){
        this.rest_id = rest_id;
        this.tbl_id = tbl_id;
    }
    public int getRestId(){
        return this.rest_id;
    }
    public int getTblID(){
        return this.tbl_id;
    }
    public List<ItemDescription> getDesc(){
        return this.descriptionList;
    }
    public void addDesc(ItemDescription desc){
        descriptionList.add(desc);
    }
    public void removeDesc(ItemDescription desc){
        descriptionList.remove(desc);
    }
    public void removeAllDesc(){
        descriptionList.removeAll(descriptionList);
    }
    public int sumItems(Order ord){
        int x = 0;
        if (ord == null)
            return x;
        else{
            for (ItemDescription item : descriptionList){
                x = x+item.getPrice();
            }
            return x;
        }
    }
    public String toString(int x){
        return Integer.toString(x);
    }


    public static Order getInstance(int rest_id , int tbl_id){
        if (instance == null) {
            instance = new Order(rest_id , tbl_id);
        }
        return instance;
    }


}
