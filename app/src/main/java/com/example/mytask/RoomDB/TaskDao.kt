package com.example.mytask.RoomDB;


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mytask.model.Productfile

@Dao
interface TaskDao {
    @Query("SELECT * FROM Productfile")
    fun getAllProducts(): List<Productfile>

    @Insert
    fun insert(task: Productfile?)


    @Query("UPDATE Productfile SET count = :Count,quantity = :quantity WHERE name =:Name AND regid=:id")
    fun updateProduct(Count: Int?, Name: String?, id: Int, quantity: Int)


    @Query("SELECT * FROM Productfile WHERE regid=:regId")
    fun getParticular(regId: Int): List<Productfile>?
}