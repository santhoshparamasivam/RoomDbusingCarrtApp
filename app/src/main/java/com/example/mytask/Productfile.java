package com.example.mytask;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Productfile {



    @PrimaryKey(autoGenerate = true)
    private int regid;

    @ColumnInfo(name = "Pid")
    private String pid;

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "quantity")
    private String quanitiy;
    @ColumnInfo(name = "count")
    private Integer count;



    @ColumnInfo(name = "Price")
    private Integer Price;
    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuanitiy() {
        return quanitiy;
    }

    public void setQuanitiy(String quanitiy) {
        this.quanitiy = quanitiy;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public int getRegid() {
        return regid;
    }

    public void setRegid(int regid) {
        this.regid = regid;
    }
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }






}
