package com.example.servingwebcontent.Component;

import java.io.FileWriter;
import java.io.IOException;
//import com.example.servingwebcontent.File;
import java.util.ArrayList;

import com.example.servingwebcontent.model.User;

public class WriteToFile {
    public void ToFile(ArrayList<User> u) {
        try {
            
            int i = u.size() - 1;
            FileWriter writer = new FileWriter("./complete/File/Login.txt", true);
            writer.append("\n");

            writer.write("Name: " + u.get(i).getName() + "\n");
            writer.write("Email: " + u.get(i).getEmail() + "\n");
            writer.write("Phone: " + u.get(i).getPhone() + "\n");

            writer.close();
        } catch (IOException e) {
            System.out.println("Error at write to File!");
            e.printStackTrace();
        }

    }
}
