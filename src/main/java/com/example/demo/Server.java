package com.example.demo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private ServerSocket serverSocket;
    public HashMap<String, SocketWrapper> clientMap;
    private RestaurantDatabase restaurantDatabase;
    private Map<String,String> clientCredentials=new HashMap<>();
    private Map<String,String> userCredentials =new HashMap<>();
    Server() {
        restaurantDatabase = new RestaurantDatabase();
        try{
            String absolutePath = "D:\\1-2\\cse-108\\test\\demo1\\src\\main\\java\\com\\example\\demo\\clientCredentials.txt";
            BufferedReader br = new BufferedReader(new FileReader(absolutePath));
            String line = br.readLine();
            System.out.println(line);
        while(line!=null){
            String []temp=line.split(",(?!\\s)",-1);
            clientCredentials.put(temp[0],temp[1]);
            line=br.readLine();
            //System.out.println(line);
        } br.close();
        String absolute = "D:\\1-2\\cse-108\\test\\demo1\\src\\main\\java\\com\\example\\demo\\userCredentials.txt";
        BufferedReader ur = new BufferedReader(new FileReader(absolute));
            String lline = ur.readLine();
            //System.out.println(lline);
        while(lline!=null){
            String []temp=lline.split(",(?!\\s)",-1);
            userCredentials.put(temp[0],temp[1]);
            lline=ur.readLine();
            System.out.println(lline);
        } ur.close();
        for(Map.Entry<String,String> entry:clientCredentials.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        for(Map.Entry<String,String> entry:userCredentials.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }
        catch(Exception e){
            System.out.println("Error in credentials reading");
        }
        clientMap = new HashMap<>();
        try {
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Request accepted");
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
        System.out.println("Server starts running...");

        
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        
        SocketWrapper socketWrapper = new SocketWrapper(clientSocket);
        String clientName = (String) socketWrapper.read();
        System.out.println(clientName);
        //socketWrapper.write("Hello " + clientName);
        clientMap.put(clientName, socketWrapper);
        new ReadThreadServer(clientMap, socketWrapper, restaurantDatabase,clientCredentials,userCredentials);
        System.out.println( clientName + " connected");
    }

    public static void main(String args[]) {
        new Server();
    }
}
