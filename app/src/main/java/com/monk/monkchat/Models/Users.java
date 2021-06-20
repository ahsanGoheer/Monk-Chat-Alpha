package com.monk.monkchat.Models;

public class Users {

    String profilePic,userName,userEmail,userPassword,userId,lastMessage;

    //Default Constructor
    public Users(){}

    //Parameterized Constructors

    public Users(String userName,String userEmail,String userPassword)
    {
        this.userEmail=userEmail;
        this.userName=userName;
        this.userPassword=userPassword;
    }

    public Users(String profilePic,String userName, String userEmail, String userPassword, String userId, String lastMessage) {
        this.profilePic=profilePic;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
