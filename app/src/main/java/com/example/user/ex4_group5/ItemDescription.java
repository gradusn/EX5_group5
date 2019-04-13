package com.example.user.ex4_group5;

/**
 * Created by user on 10/24/2015.
 */
public class ItemDescription {
    String itemName,desc,category;
    int restId,price;

    public ItemDescription(String itemName, String desc, String category, int restId, int price) {
        this.itemName = itemName;
        this.desc = desc;
        this.category = category;
        this.restId = restId;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
