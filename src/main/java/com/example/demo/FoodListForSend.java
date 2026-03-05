package com.example.demo;

public class FoodListForSend implements java.io.Serializable{
     public Food food;
     public int count;
    public FoodListForSend(Food food,int count){
        this.food=food;
        this.count=count;
    }
    public void setFood(Food food){
        this.food=food;
    }
    public Food getFood(){
        return this.food;
    }
    public String getRestaurantId(){
        return this.food.getRestaurantId();
    }
    public String getCategory(){
        return this.food.getCategory();
    }
    public String getName(){
        return this.food.getName();
    }
    public String getPrice(){
        return this.food.getPrice();
    }
    public String getRestaurantName(){
        return this.food.getRestaurantName();
    }
    public void displayDetails(){
        food.displayDetails();
    }
    public void setCount(int count){
        this.count=count;
    }
    public int getCount(){
        return this.count;
    }

}
