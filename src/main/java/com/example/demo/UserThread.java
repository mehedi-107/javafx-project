package com.example.demo;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class UserThread implements Runnable{
    private Thread thr;
    UserThread(Socket socket, Signiture temp, ObjectOutputStream outputStream, ObjectInputStream inputStream, RestaurantDatabase restaurantDatabase){
        thr=new Thread(this,"UserThread"+temp.getUserName());
        thr.start();
    }
    @Override
    public void run() {
        
    }
    
}
