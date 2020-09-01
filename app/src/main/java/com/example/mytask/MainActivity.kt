package com.example.mytask

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.RoomDB.DatabaseClient
import com.example.mytask.adapters.CartListAdapter
import com.example.mytask.model.Productfile
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var recyclerview: RecyclerView
    lateinit var cart_view: TextView
    lateinit var back: ImageView
    var totcount = 0
    lateinit var cartListAdapter: CartListAdapter
    var productList: List<Productfile> = ArrayList()
    private lateinit var productfileList: List<Productfile>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        cart_view = findViewById(R.id.cart_view)
        back = findViewById(R.id.back)
        recyclerview = findViewById(R.id.recyclerview)
        cart_view.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, ViewCart::class.java)
            startActivity(intent)
        })
        supportActionBar!!.hide()
        back.setOnClickListener(View.OnClickListener { finish() })

    }

    private fun productlistmethod() {
        if (productfileList != null && productfileList!!.size > 0) {
            cartListAdapter = CartListAdapter(this, productfileList)
            val linearLayoutManager = LinearLayoutManager(this)
            recyclerview!!.layoutManager = linearLayoutManager
            recyclerview!!.adapter = cartListAdapter
        }
    }

    private val productdetails: Unit
        private get() {
            class GetTasks : AsyncTask<Void?, Void?, List<Productfile>>() {
                override fun doInBackground(vararg params: Void?): List<Productfile>  {
                    productfileList = DatabaseClient
                            .getInstance(this@MainActivity)
                            ?.appDatabase
                            ?.taskDao()
                            ?.getAllProducts()!!
                    return productfileList
                }

                override fun onPostExecute(tasks: List<Productfile>?) {
                    super.onPostExecute(tasks)
                    productlistmethod()
                }

            }

            val gt = GetTasks()
            gt.execute()
        }

    fun AddcartValue(count: String, action: String, productfile: Productfile) {
        if (action.equals("add", ignoreCase = true)) {
            totcount = totcount + 1
            cart_view!!.text = "View Items($totcount)"
            StoredinLocal(productfile, count)
        } else if (action.equals("minus", ignoreCase = true)) {
            if (count == "0") {
                totcount = 0
                StoredinLocal(productfile, count)
            } else totcount = totcount - 1
            cart_view!!.text = "View Items($totcount)"
            StoredinLocal(productfile, count)
        }
    }

    private fun StoredinLocal(productfile: Productfile, count: String) {
        class UpdateTask : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                DatabaseClient.getInstance(applicationContext)?.appDatabase
                        ?.taskDao()
                        ?.updateProduct(count.toInt(), productfile.name, productfile.regid, count.toInt())
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
            }

        }

        val st = UpdateTask()
        st.execute()
    }

    override fun onResume() {
        super.onResume()
        productdetails
    }
}