package com.example.demo;

import java.io.Serializable;

public class Food implements Serializable{
    private String restaurantId;
    private String category;
    private String name;
    private String price;
    private String restaurantName;
    public Food(String restaurantId,String category,String name,String price){
        this.restaurantId=restaurantId;
        this.category=category;
        this.name=name;
        this.price=price;
    }

    public void setRestaurantId(String restaurantId){
        this.restaurantId=restaurantId;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPrice(String price){
        this.price=price;
    }
    public String getRestaurantId(){
        return this.restaurantId;
    }
    public String getCategory(){
        return this.category;
    }
    public String getName(){
        return this.name;
    }
    public String getPrice(){
        return this.price;
    }
    public void setRestaurantName(String restaurantName){
        this.restaurantName=restaurantName;
    }
    public String getRestaurantName(){
        return this.restaurantName;
    }
    public void displayDetails(){
        System.out.println("Restaurant ID: "+this.restaurantId);
        System.out.println("Category: "+this.category);
        System.out.println("Name: "+this.name);
        System.out.println("Price: "+this.price);
    }
}

