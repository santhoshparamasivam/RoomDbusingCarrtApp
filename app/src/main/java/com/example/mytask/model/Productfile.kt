package com.example.mytask.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Productfile {
    @PrimaryKey(autoGenerate = true)
    var regid = 0

    @ColumnInfo(name = "Pid")
    var pid: String? = null

    @ColumnInfo(name = "name")
    var name: String? = null

    @ColumnInfo(name = "description")
    var description: String? = null

    @ColumnInfo(name = "quantity")
    var quanitiy: String? = null

    @ColumnInfo(name = "count")
    var count: Int? = null

    @ColumnInfo(name = "Price")
    var price: Int? = null
}