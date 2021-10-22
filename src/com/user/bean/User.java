package com.user.bean;

import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String phone;
    private String sex;
    private String address;

    public User(String name, String phone, String sex, String address) {
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
    }

    public User(int id, String name, String phone, String sex, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
    }

    public User() {
    }
    public User(int id,String phone)
    {
        this.id = id;
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(phone, user.phone) &&
                Objects.equals(sex, user.sex) &&
                Objects.equals(address, user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, sex, address);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
