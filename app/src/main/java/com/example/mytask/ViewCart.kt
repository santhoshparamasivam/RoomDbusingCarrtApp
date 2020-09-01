package com.example.mytask

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.RoomDB.DatabaseClient
import com.example.mytask.adapters.ViewListAdapter
import com.example.mytask.model.Productfile
import java.util.*

class ViewCart : AppCompatActivity() {
    var recyclerview: RecyclerView? = null
    var cart_view: TextView? = null
    var totprice = 0
    var totcount = 0
    var finalcount = 0
    var finalprice = 0
    var cartListAdapter: ViewListAdapter? = null
    var productList: MutableList<Productfile> = ArrayList()
    private var productionList: List<Productfile>? = null
    var drivein: RadioButton? = null
    var Away: RadioButton? = null
    var back: ImageView? = null
    var cost: TextView? = null
    var nodata: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_cart)
        supportActionBar!!.hide()
        cart_view = findViewById(R.id.cart_view)
        recyclerview = findViewById(R.id.recyclerview)
//        Away = findViewById(R.id.away)
//        drivein = findViewById(R.id.drivein)
        cost = findViewById(R.id.cost)
        back = findViewById(R.id.back)
        nodata = findViewById(R.id.nodata)
        supportActionBar!!.hide()
        back?.setOnClickListener { finish() }
//        Away?.setOnClickListener {
//            Away!!.isChecked = true
//            drivein?.isChecked = false
//        }
//        drivein?.setOnClickListener {
//            drivein!!.isChecked = true
//            Away?.isChecked = false
//        }

    }

    private fun adaptermethod(productList: List<Productfile>) {
        totprice = 0
        finalprice = 0

        val listofproducts: MutableList<Productfile> = ArrayList()
        for (i in productList.indices) {
            if (productList[i].count != 0) {
                    val product = Productfile()
                    product.pid = productList[i].pid
                    product.count = productList[i].count
                    product.description = productList[i].description
                    product.name = productList[i].name
                    product.regid = productList[i].regid
                    product.quanitiy = productList[i].quanitiy
                    product.price = productList[i].price
                    listofproducts.add(product)
                    val price = productList[i].quanitiy?.toInt()?.let { productList[i].price?.times(it) }
                finalcount += product.count!!
                finalprice = price?.let { finalprice.plus(it) }!!
                    totprice = finalprice
                    Log.e("final price", "$finalprice ")
            }
        }
        cost!!.text = "Rs $finalprice"
        cartListAdapter = ViewListAdapter(this, listofproducts)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerview!!.layoutManager = linearLayoutManager
        recyclerview!!.adapter = cartListAdapter
    }

    private val productDetails: Unit
        private get() {
            class GetTasks : AsyncTask<Void?, Void?, List<Productfile>?>() {
                override fun doInBackground(vararg params: Void?): List<Productfile>? {
                    productionList = DatabaseClient
                            .getInstance(this@ViewCart)
                            ?.appDatabase
                            ?.taskDao()
                            ?.getAllProducts()
                    return productionList
                }

                override fun onPostExecute(tasks: List<Productfile>?) {
                    super.onPostExecute(tasks)
                    Log.e("productfilelist", productionList!!.size.toString() + " ")
                    if (productionList != null && productionList!!.isNotEmpty()) {
                        for (i in productionList!!.indices) {
                            if (productionList!![i].count != 0) {
                                val product = Productfile()
                                product.pid = productionList!![i].pid
                                product.count = productionList!![i].count
                                product.description = productionList!![i].description
                                product.name = productionList!![i].name
                                product.regid = productionList!![i].regid
                                product.quanitiy = productionList!![i].quanitiy
                                product.price = productionList!![i].price
                                productList.add(product)
                            }
                        }
                        adaptermethod(productList)
                    }
                }

            }

            val gt = GetTasks()
            gt.execute()
        }

    fun addcartValue(count: String, action: String, productfile: Productfile) {
        if (action.equals("add", ignoreCase = true)) {
            val price = "1".toInt() * productfile.price!!
            totprice += price
            cost!!.text = "Rs $totprice"
            storedinLocal( productfile, count)
        } else if (action.equals("minus", ignoreCase = true)) {
            if (count == "0") {
                totcount = 0
                val price = "1".toInt() * productfile.price!!
                totprice -= price
                cost!!.text = "Rs $totprice"
                storedinLocal( productfile,  count)
            } else {
                val price = "1".toInt() * productfile.price!!
                totprice -= price
                cost!!.text = "Rs $totprice"
                storedinLocal( productfile,  count)
            }
        }
    }

    private fun storedinLocal( productfile: Productfile,  count: String) {
        class UpdateTask : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void?  {
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
        productDetails
    }
}