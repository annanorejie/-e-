package com.courier.bean;

import java.util.Objects;

public class Courier {
    private int id;
    private String name;
    private String express;
    private String sex;
    private String phone;

    public Courier() {
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", express='" + express + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return id == courier.id &&
                Objects.equals(name, courier.name) &&
                Objects.equals(express, courier.express) &&
                Objects.equals(sex, courier.sex) &&
                Objects.equals(phone, courier.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, express, sex, phone);
    }

    public Courier(int id, String name, String express, String sex, String phone) {
        this.id = id;
        this.name = name;
        this.express = express;
        this.sex = sex;
        this.phone = phone;
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

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
