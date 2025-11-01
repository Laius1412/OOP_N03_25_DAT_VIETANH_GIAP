package com.example.servingwebcontent.Component;
import java.util.ArrayList;

import com.example.servingwebcontent.model.User;

public class ListUser {
    public void printListUser(ArrayList<User> listUser){
        for(int i =0; i<listUser.size(); i++){
            System.out.println("List user:");
            System.out.println("Name: " + listUser.get(i).getName());
            System.out.println("Email: " + listUser.get(i).getEmail());
            System.out.println("Phone: " + listUser.get(i).getPhone());
            System.out.println("ID: " + listUser.get(i).getId());
            System.out.println("Created: " + listUser.get(i).getCreatedAt());
            System.out.println("---");
        }
    }
    
}
