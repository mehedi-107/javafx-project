package com.example.demo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FoodData implements Serializable {
    public List<Food> foods = new ArrayList<>();
    public FoodData(){
        try{
            String absolutePath = "D:\\1-2\\cse-108\\test\\demo1\\src\\main\\java\\com\\example\\demo\\menu.txt";
            BufferedReader br = new BufferedReader(new FileReader(absolutePath));
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
                String []temp= i.split(",(?!\\s)",-1);
                String restaurantId=temp[0];
                String category=temp[1];
                String name=temp[2];
                String price=temp[3];
                Food foodTemp=new Food(restaurantId,category,name, price);
                foods.add(foodTemp);
            }
        }
        catch(Exception e){
            e.printStackTrace();

            System.out.println("Error in FoodData.java");
        }
    }

    public void addFood(Food food){
        foods.add(food);
    }
    public void storeFoodData(){
        try{
            String path = "D:\\1-2\\cse-108\\test\\demo1\\src\\main\\java\\com\\example\\demo\\menu.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for(var i:foods){
                bw.write(i.getRestaurantId()+","+i.getCategory()+","+i.getName()+","+i.getPrice()+"\n");
            }
            bw.close();
        }
        catch(Exception e){
            System.out.println("Error in FoodData.java");
        }
    }
    // public static void main(String[] args) throws Exception {
    //     FoodData a=new FoodData();
    //     for(var i:a.foods){
    //         System.out.println(i.getRestaurantId());
    //         System.out.println(i.getCategory());
    //         System.out.println(i.getName());
    //         System.out.println(i.getPrice());
    //     }
    // }
}
