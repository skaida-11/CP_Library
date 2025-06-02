package com.example.cp_library;

public class Verification {

    public static boolean isbnCorrect(String number){
        boolean allNumbers = true;
        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))){
                allNumbers = false;
                break;
            }
        }
        if (number.length() == 13 && allNumbers == true){
            return true;
        }
        return false;
    }

    public static boolean isbnUnique(String number){
        for (Book b : Storage.allBooks){
            if (number.equals(b.getIsbn())){
                return false;
            }
        }
        return true;
    }

    public static boolean strongPassword(String password){
        boolean length = password.length() >= 8;
        boolean upperCase = false;
        boolean lowercase = false;
        boolean digit = false;

        for (char c : password.toCharArray()){
            if (Character.isUpperCase(c)) {
                upperCase = true;
            } else if (Character.isLowerCase(c)) {
                lowercase = true;
            } else if (Character.isDigit(c)) {
                digit = true;
            }

            if (length && upperCase && lowercase && digit){
                return true;
            }
        }
        return false;
    }

    public static boolean correctPassword(String password1, String password2){
        if (password1.equals(password2)){
            return true;
        } else {
            return false;
        }
    }

    public static boolean emailFormat(String mail){
        String regex = "^(?=.{3,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,3})$";
        return mail.matches(regex);
    }

    public static boolean emailUnique(String mail){
        for (User u : Storage.allUsers){
            if (u.getEmail().equals(mail)){
                return false;
            }
        }
        return true;
    }
}
