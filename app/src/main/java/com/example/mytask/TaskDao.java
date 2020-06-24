package com.example.mytask;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TaskDao {

    //Firstdtable
    @Query("SELECT * FROM Productfile")
    List<Productfile> getAllProducts();

    @Insert
    void insert(Productfile task);


    @Query("UPDATE Productfile SET count = :Count,quantity = :quantity WHERE name =:Name AND Pid=:id")
    void updateProduct(Integer Count, String Name, String id,int quantity);





}