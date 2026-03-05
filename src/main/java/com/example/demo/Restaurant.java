package com.example.demo;

import java.io.Serializable;
public class Restaurant implements Serializable{
    private String id;
    private String name;
    private String score;
    private String price;
    private String zipCode;
    private String[] categories;
    
    Restaurant(String id,String name,String score,String price,String zipCode,String []categories){
        this.id=id;
        this.name=name;
        this.score=score;
        this.price=price;
        this.zipCode=zipCode;
        this.categories=new String[categories.length];
        for(int i=0;i<categories.length;i++){
            this.categories[i]=categories[i];
        }
    }
    Restaurant(Restaurant obj){
        this.id=obj.id;
        this.name=obj.name;
        this.score=obj.score;
        this.price=obj.price;
        this.zipCode=obj.zipCode;
        this.categories=new String[obj.categories.length];
        for(int i=0;i<obj.categories.length;i++){
            this.categories[i]=obj.categories[i];
        }
    }
    public void setID(String id){
        this.id=id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setScore(String score){
        this.score=score;
    }
    public void setPrice(String price){
        this.price=price;
    }
    public void setZipCode(String zipCode){
        this.zipCode=zipCode;
    }
    public void setCategories(String []categories){
        this.categories=new String[categories.length];
        for(int i=0;i<categories.length;i++){
            this.categories[i]=categories[i];
        }
    }
    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getScore(){
        return this.score;
    }
    public String getPrice(){
        return this.price;
    }
    public String getZipCode(){
        return this.zipCode;
    }
    public String[] getCategories(){
        return this.categories;
    }
    void displayDetails(){
        System.out.println("ID: "+this.id);
        System.out.println("Name: "+this.name);
        System.out.println("Score: "+this.score);
        System.out.println("Price: "+this.price);
        System.out.println("Zip Code: "+this.zipCode);
        System.out.print("Categories: ");
        for(int i=0;i<this.categories.length;i++){
            if(categories[i].length()==0){
                continue;
            }
            if(i==this.categories.length-1){
                System.out.print(this.categories[i]);
            }
            else{
                System.out.print(this.categories[i]);
                if(categories[i+1].length()!=0 && i!=this.categories.length-1)
                    System.out.print(", ");
            }
        }
        System.out.println();
    }
}

