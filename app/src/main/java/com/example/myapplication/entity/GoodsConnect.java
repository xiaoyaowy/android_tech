package com.example.myapplication.entity;

public class GoodsConnect {


    private String wuliao;

    private String description;

    public GoodsConnect(String wuliao, String description) {
        this.wuliao = wuliao;
        this.description = description;
    }

    public String getWuliao() {
        return wuliao;
    }

    public void setWuliao(String wuliao) {
        this.wuliao = wuliao;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "GoodsConnect{" +
                "wuliao='" + wuliao + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
