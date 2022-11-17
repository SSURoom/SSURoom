package com.userinterface.ssuroom;

public class ReviewItem {
    String tradeType;
    long rentCost;
    long depositCost;
    long area;
    long floor;
    String address;
    double star;

    public ReviewItem(String tradeType, long rentCost, long depositCost, long area, long floor, String address, double star) {
        this.tradeType = tradeType;
        this.rentCost = rentCost;
        this.depositCost = depositCost;
        this.area = area;
        this.floor = floor;
        this.address = address;
        this.star = star;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public long getRentCost() {
        return rentCost;
    }

    public void setRentCost(long rentCost) {
        this.rentCost = rentCost;
    }

    public long getDepositCost() {
        return depositCost;
    }

    public void setDepositCost(long depositCost) {
        this.depositCost = depositCost;
    }

    public long getArea() {
        return area;
    }

    public void setArea(long area) {
        this.area = area;
    }

    public long getFloor() {
        return floor;
    }

    public void setFloor(long floor) {
        this.floor = floor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }
}
