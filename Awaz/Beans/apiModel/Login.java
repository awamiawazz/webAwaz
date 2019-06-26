package com.Beans.apiModel;

/**
 * Created by Asad on 3/27/2019.
 */
public class Login {

    private String user_Name;
    private String password;

    public Login() {
    }

    public Login(String user_Name, String password) {
        this.user_Name = user_Name;
        this.password = password;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
