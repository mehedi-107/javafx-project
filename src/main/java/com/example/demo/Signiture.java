package com.example.demo;
import java.io.Serializable;

public class Signiture implements Serializable {
    private String name;
    private String idOrUserName;
    public Signiture() {
    }

    public Signiture(String name, String idOrUserName) {
        this.name = name;
        this.idOrUserName = idOrUserName;
    }

    public String getUserName() {
        return this.idOrUserName;
    }

    public String getName() {
        return this.name;
    }

    public void setUserName(String userName) {
        this.idOrUserName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }
}
