package com.Beans;

/**
 * Created by Hp on 11/22/2018.
 */
public class User {

    private int user_id,org_id;
    private String user_name;
    private String address;
    private String phone_no;
    private String full_name;
    private String gender;
    private String org_name;
    private String cnic,emp_id;
    private String password,profilePicture;
    private String email;
    private String citizenship;
    private Role role;private byte[] imagee;
    private boolean block_status,approved_status;

    public byte[] getImagee() {
        return imagee;
    }

    public void setImagee(byte[] imagee) {
        this.imagee = imagee;
    }

    public boolean isApproved_status() {
        return approved_status;
    }

    public void setApproved_status(boolean approved_status) {
        this.approved_status = approved_status;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public int getOrg_id() {
        return org_id;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public boolean isBlock_status() {
        return block_status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setBlock_status(boolean block_status) {
        this.block_status = block_status;
    }

    public User() {
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
