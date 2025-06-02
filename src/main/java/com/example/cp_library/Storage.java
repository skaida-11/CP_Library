package com.example.cp_library;

import java.io.*;
import java.util.ArrayList;

public class Storage {
    public static ArrayList<User> allUsers;
    public static ArrayList<Book> allBooks;
    public static User selectedUser;
    public static Book selectedBook;
    public static String newEmail;
    public static String newPassword;
    public static String last;


    public static void InitUsers(){
        File f = new File("userList.txt");
        if (f.exists()) {
            readFromFile("userList");
        } else {
            var list = new ArrayList<User>();
            list.add(new User("admin", "1234", "Admin", "admin@library.com"));
            writeToFile("userList", list);
        }}

    public static void InitBooks(){
        File f = new File("bookList.txt");
        if (f.exists()) {
            readFromFile("bookList");
        } else {
            var list = new ArrayList<Book>();
            writeToFile("bookList", list);
        }}


    public static <T> void writeToFile(String filepath, ArrayList<T> list){
        File f = new File(filepath + ".txt");
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
            fos.close();

        } catch (Exception e) {
            //
        }
    }

    public static <T> ArrayList<T> readFromFile(String filepath){
        try {
            FileInputStream fis = new FileInputStream(filepath + ".txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<T> list = (ArrayList<T>) ois.readObject();
            ois.close();
            fis.close();
            return list;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
