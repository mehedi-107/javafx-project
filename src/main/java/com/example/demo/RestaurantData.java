package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class RestaurantData implements Serializable{
    public List<Restaurant> restaurants = new ArrayList<>();
    public Map<String,String> mp=new HashMap<>();
    public Map<String,String> getRestaurantNameFromID = new HashMap<>();
    public RestaurantData(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("D:\\1-2\\cse-108\\test\\demo1\\src\\main\\java\\com\\example\\demo\\restaurant.txt"));
            List<String> str=new ArrayList<>();
            while (true) {
                String line = br.readLine();
                if (line == null) break;
                else{
                    str.add(line);
                }
            }
            br.close();
            for(var i:str){
                String []temp=i.split(",(?!\\s)",-1);
                String id=temp[0];
                String name=temp[1];
                String score=temp[2];
                String Price=temp[3];
                String zipCode=temp[4];
                int categories_cn=temp.length-5;
                String categories[]=new String[categories_cn];
                for(int m=0;m<categories_cn;m++){
                    categories[m]=temp[5+m];
                }
                mp.put(name,id);
                getRestaurantNameFromID.put(id,name);
                Restaurant resTemp =new Restaurant(id, name, score, Price, zipCode, categories);
                restaurants.add(resTemp);
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error in RestaurantData.java");
        }
    }

    public void storeRestaurantData(){
        try{
            String path = "D:\\1-2\\cse-108\\test\\demo1\\src\\main\\java\\com\\example\\demo\\restaurant.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for(var i:restaurants){
                bw.write(i.getId()+","+i.getName()+","+i.getScore()+","+i.getPrice()+","+i.getZipCode());
                String []temp=i.getCategories();
                for(int j=0;j<temp.length;j++){
                    bw.write(","+temp[j]);
                }
                bw.write("\n");
            }
            bw.close();
        }
        catch(Exception e){
            System.out.println("Error in RestaurantData.java");
        }
    }

    public void addRestaurant(Restaurant restaurant){
        restaurants.add(restaurant);
        mp.put(restaurant.getName(),restaurant.getId());
    }

    public boolean isRestaurantIdExist(String name){
        for(var i:restaurants){
            if(i.getName().toLowerCase().contains(name.toLowerCase()))
                return true;
        }
        return false;
    }
    // public static void main(String[] args) throws Exception {
    //     RestaurantData a=new RestaurantData();
    //     for(var i:a.restaurants){
    //         i.displayDetails();
    //     }
    // }

}

