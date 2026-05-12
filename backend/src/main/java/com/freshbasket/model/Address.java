package com.freshbasket.model;

public class Address {
    private String name;
    private String houseNo;
    private String colony;
    private String nearby;
    private String state;
    private String pin;
    private String phone;

    public Address() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getHouseNo() { return houseNo; }
    public void setHouseNo(String houseNo) { this.houseNo = houseNo; }
    public String getColony() { return colony; }
    public void setColony(String colony) { this.colony = colony; }
    public String getNearby() { return nearby; }
    public void setNearby(String nearby) { this.nearby = nearby; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
