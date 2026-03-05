package com.example.demo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RestaurantDatabase implements Serializable{
    private RestaurantData restaurantData;
    private FoodData foodData;
    public Map<String,List<Food>> restaurantIdAndFoodList=new HashMap<>();

    RestaurantDatabase(){
        restaurantData=new RestaurantData();
        foodData=new FoodData();
        for(int i=0;i<restaurantData.restaurants.size();i++){
            restaurantIdAndFoodList.put(restaurantData.restaurants.get(i).getId(),new ArrayList<>());
        }
        for(int i=0;i<foodData.foods.size();i++){
            if(restaurantIdAndFoodList.containsKey(foodData.foods.get(i).getRestaurantId())){
                restaurantIdAndFoodList.get(foodData.foods.get(i).getRestaurantId()).add(foodData.foods.get(i));
            }
            else{
                List<Food>temp=new ArrayList<>();
                temp.add(foodData.foods.get(i));
                restaurantIdAndFoodList.put(foodData.foods.get(i).getRestaurantId(),temp);
            }
        }
            for(int i=0;i<foodData.foods.size();i++){
                foodData.foods.get(i).setRestaurantName(restaurantData.getRestaurantNameFromID.get(foodData.foods.get(i).getRestaurantId()));
            }
        }
    
    public List<Restaurant>  searchRestaurantByName(String name){
        List<Restaurant> temp=new ArrayList<>();
        for(var i:restaurantData.restaurants){
            if(i.getName().toLowerCase().contains(name.toLowerCase()))
            {
                temp.add(i);
            }
        }
        return temp;
    }
    public List<Restaurant> searchRestaurantByScore(double l_limit,double u_limit){
        List<Restaurant> temp = new ArrayList<>();
        System.out.println(l_limit+" "+u_limit);
        for(var i:restaurantData.restaurants){
            if(Double.parseDouble(i.getScore())<=u_limit && Double.parseDouble(i.getScore())>=l_limit)
            {
                temp.add(i);
            }
        }
        return temp;
    }

    public List<Restaurant> searchRestaurantByCategory(String category){
        List<Restaurant> temp=new ArrayList<>();
        String []ss=new String[3];
        for(var i:restaurantData.restaurants){
            ss=i.getCategories();
            for(int j=0;j<3;j++)
                if(ss[j].toLowerCase().contains(category.toLowerCase()))
                {
                    temp.add(i);
                    break;
                }
        }
        return temp;
    }

    public List<Restaurant> searchRestaurantByPrice(String price){
        List<Restaurant> temp= new ArrayList<>();
        for(var i:restaurantData.restaurants){
            if(i.getPrice().equalsIgnoreCase(price))
            {
                temp.add(i);
            }
        }
        return temp;
    }

    public List<Restaurant> searchRestaurantByZipCode(String zipCode){
        List<Restaurant> temp= new ArrayList<>();
        for(var i:restaurantData.restaurants){
            if(i.getZipCode().equalsIgnoreCase(zipCode))
            {
                temp.add(i);
            }
        }
        return temp;
    }

    public Map<String,List<Restaurant>> displayRestaurantCategoryWise(){
        Map<String,List<Restaurant>> mp= new HashMap<>();
        for(var i:restaurantData.restaurants){
            String []ss=i.getCategories();
            for(int j=0;j<3;j++){
                if(ss[j].length()==0)
                    continue;
                if(mp.containsKey(ss[j])){
                    mp.get(ss[j]).add(i);
                }
                else{
                    List<Restaurant>tempList=new ArrayList<>();
                    tempList.add(i);
                    mp.put(ss[j],tempList);
                }
            }
        }
        return mp;
    }



    //*******************************option 2 start ***************************/
    public List<Food> searchFoodByName(String name){
        List<Food> temp=new ArrayList<>();
        Map<String,Food>mp=new HashMap<>();// here I am using map to avoid duplicate food item with same name, same category and same restaurant id
        for(var i:foodData.foods){
            if(i.getName().toLowerCase().contains(name.toLowerCase())){
                String ss=new String();
                ss=i.getRestaurantId()+","+i.getName()+","+i.getCategory();// merging id name and category of food item which is already included in my final arraylist
                if(mp.containsKey(ss))
                {
                    continue;
                }
                else{
                    mp.put(ss,i);
                    temp.add(i);
                }
            }
        }
        return temp;
    }

    public List<Food> searchFoodInAGivenRestaurant(String foodName,String restaurantName){
        Map<String,Integer>mp=new HashMap<>();
        for(var i:restaurantData.restaurants){
            if(i.getName().toLowerCase().contains(restaurantName.toLowerCase()))
                mp.put(i.getId(),1);
        }
        List<Food> temp=new ArrayList<>();
        for(var i:foodData.foods){
            if(i.getName().toLowerCase().contains(foodName.toLowerCase()) && mp.containsKey(i.getRestaurantId())){
                temp.add(i);
            }
        }
        return temp;
    }

    public List<Food> searchFoodByCategory(String category){
        List<Food> temp= new ArrayList<>();
        for(var i:foodData.foods){
            if(i.getName().toLowerCase().contains(category.toLowerCase()))
            {
                temp.add(i);
            }
        }
        return temp;
    }

    public List<Food> searchCategoryInAGivenRestaurant(String category,String restaurantName){
        Map<String,Integer>mp=new HashMap<>();
        for(var i:restaurantData.restaurants){
            if(i.getName().toLowerCase().contains(restaurantName.toLowerCase()))
                mp.put(i.getId(),1);
        }
        List<Food> temp=new ArrayList<>();
        for(var i:foodData.foods){
            if(i.getCategory().toLowerCase().contains(category.toLowerCase()) && mp.containsKey(i.getRestaurantId())){
                temp.add(i);
            }
        }
        return temp;
    }

    public List<Food> searchFoodByPriceRange(double l_limit,double u_limit){
        List<Food>temp=new ArrayList<>();
        for(var i:foodData.foods){
            if(Double.parseDouble(i.getPrice())<=u_limit && Double.parseDouble(i.getPrice())>=l_limit)
            {
                temp.add(i);
            }
        }
        return temp;
    }

    public List<Food> searchPriceRangeInAGivenRestaurant(double l_limit,double u_limit,String restaurantName){
        Map<String,Integer>mp=new HashMap<>();
        for(var i:restaurantData.restaurants){
            if(i.getName().toLowerCase().contains(restaurantName.toLowerCase()))
                mp.put(i.getId(),1);
        }
        List<Food>temp=new ArrayList<>();
        for(var i:foodData.foods){
            if(mp.containsKey(i.getRestaurantId()) && Double.parseDouble(i.getPrice())>=l_limit  && Double.parseDouble(i.getPrice())<=u_limit)
            {
                temp.add(i);
            }
        }
        return temp;
    }
    public List<Food> displayCostliestFoodItem(String restaurantName){
        Map<String,Integer>mp=new HashMap<>();
        for(var i:restaurantData.restaurants){
            if(i.getName().toLowerCase().contains(restaurantName.toLowerCase()))
                mp.put(i.getId(),1);
        }
        List<Food> temp= new ArrayList<>();

        double max=-1;
        for(var i:foodData.foods){
            if(mp.containsKey(i.getRestaurantId()) && Double.parseDouble(i.getPrice())>max)
                max=Double.parseDouble(i.getPrice());
        }
        if(max==-1)
            return null;
        for(var i:foodData.foods){
            if(mp.containsKey(i.getRestaurantId()) && Double.parseDouble(i.getPrice())==max)
                temp.add(i);
        }
        return temp;
    }

    public Map<String,Integer> displayListOfRestaurantsAndTotalItem(){
        Map<String,Integer>mp=new HashMap<>();
        for(var i:restaurantData.restaurants){
            mp.put(i.getId(),0);// initiallizing all id with 0. Means there is no food item in that restaurant
        }
        for(var i:foodData.foods){
            mp.put(i.getRestaurantId(),mp.get(i.getRestaurantId())+1);// increment of food item is occuring corresponding to the restaurant id
        }
        Map<String,String> storeIdAndCorresspondingName = new HashMap<>();
        for(var i:restaurantData.restaurants){
            storeIdAndCorresspondingName.put(i.getId(),i.getName());// storing id and corresponding name
        }
        Map<String,Integer> temp= new HashMap<>();
        for(Map.Entry<String,Integer> it: mp.entrySet()){
            temp.put(storeIdAndCorresspondingName.get(it.getKey()),it.getValue());// storing name and corresponding number of food item
        }
        return temp;
    }

    // /************************************ Helper functions*************************************/
    public boolean idCheckForAddARestaurant(String id){
        for(var i:restaurantData.restaurants){
            if(i.getId().equalsIgnoreCase(id))
                return false;
        }
        return true;
    }
    public boolean isRestaurantNameExist(String name){
        for(var i:restaurantData.restaurants){
            if(i.getName().equalsIgnoreCase(name))
                return true;
        }
        return false;
    }


    public void addARestaurant(String id,String name,String score,String price,String zipCode,String[]categories){
        Restaurant temp=new Restaurant(id,name,score,price,zipCode,categories);
        restaurantData.addRestaurant(temp);
        restaurantIdAndFoodList.put(id,new ArrayList<>());
    }
    public void addARestaurant(Restaurant temp){
        restaurantData.addRestaurant(temp);
        restaurantIdAndFoodList.put(temp.getId(),new ArrayList<>());
    }
    public void addAFoodItem(String restaurantId,String category,String name,String price){
        Food temp=new Food(restaurantId,category,name,price);
        foodData.addFood(temp);
    }
    public void addAFoodItem(Food temp){
        foodData.addFood(temp);
        restaurantIdAndFoodList.get(temp.getRestaurantId()).add(temp);
    }
    public void storeAllData(){
        restaurantData.storeRestaurantData();
        foodData.storeFoodData();
    }

    public String getIdFromName(String name){
        return restaurantData.mp.get(name);
    }
    public Restaurant getRestaurantFromId(String id){
        for(var i:restaurantData.restaurants){
            if(i.getId().equalsIgnoreCase(id))
                return i;
        }
        return null;
    }
    public String getIdFromNameSingle(String id){
        for(var i:restaurantData.restaurants){
            if(i.getName().equalsIgnoreCase(id))
                return i.getId();
        }
        return null;
    }
    public List<Food> getFoodListFromRestaurantName(String restaurantName){
        List<Restaurant> temp= searchRestaurantByName(restaurantName);
        List<Food> tempFood=new ArrayList<>();
        for(var i:temp){
            tempFood.addAll(restaurantIdAndFoodList.get(i.getId()));
        }
        return tempFood;

    }
    public static void main(String[] args) {
        RestaurantDatabase restaurantDatabase= new RestaurantDatabase();
        for(int i=0;i<restaurantDatabase.restaurantData.restaurants.size();i++){
            restaurantDatabase.restaurantData.restaurants.get(i).displayDetails();
        }
    }
}





