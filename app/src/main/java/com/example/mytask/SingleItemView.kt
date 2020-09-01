package com.example.mytask

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mytask.RoomDB.DatabaseClient
import com.example.mytask.model.Productfile

class SingleItemView : AppCompatActivity() {
    private var productionList: List<Productfile>? = null
    lateinit  var edt_name: TextView
    lateinit  var edt_desc:TextView
    lateinit  var edt_price:TextView
    lateinit  var edt_quanitiy:TextView
    lateinit  var edt_pid:TextView
    lateinit  var cart_view: ImageView
    lateinit  var cartMinusImg: ImageView
    lateinit  var cartPlusImg: ImageView
    lateinit  var tvQuantity: TextView
    lateinit  var add: TextView
    var totprice = 0
    var totcount = 0
    var finalcount = 0
    var finalprice = 0
    var id=0
    lateinit var name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_item_view)
        var intent = intent
        var regId = intent.getIntExtra("RegId", 0)
        edt_name = findViewById(R.id.edt_name)
        edt_pid = findViewById(R.id.edt_pid)
        edt_desc = findViewById(R.id.edt_desc)
        edt_price = findViewById(R.id.edt_price)
        edt_quanitiy = findViewById(R.id.edt_quanitiy)
        cart_view = findViewById(R.id.cart_view)
        tvQuantity=findViewById(R.id.cart_quantity_tv)
        cartPlusImg=findViewById(R.id.cart_plus_img)
        add=findViewById(R.id.Add)
        cartMinusImg=findViewById(R.id.cart_minus_img);
        viewSingleItemMethod(regId)
        add.setOnClickListener {
            tvQuantity.visibility = View.VISIBLE
           add.visibility = View.GONE
          cartMinusImg.visibility = View.VISIBLE
            cartPlusImg.visibility = View.VISIBLE
           tvQuantity.text = 1.toString()
         addcartValue("1", "Add")
        }
        cart_view.setOnClickListener {
            val intent = Intent(this@SingleItemView, ViewCart::class.java)
            startActivity(intent)
        }
        cartPlusImg.setOnClickListener {
            val total = tvQuantity.text.toString()
            var quanitiy = Integer.parseInt(total)
            if (total.toInt() != 20) {
                quanitiy = total.toInt() + 1
                tvQuantity.text = quanitiy.toString()
               addcartValue(quanitiy.toString(), "Add")
            }
        }
        cartMinusImg.setOnClickListener {
            val total = tvQuantity.text.toString()
            var quanitiy = total.toInt()
            if (total.toInt() == 1) {
                quanitiy = 0
                tvQuantity.visibility = View.GONE
                add.visibility = View.VISIBLE
                cartMinusImg.visibility = View.GONE
                cartPlusImg.visibility = View.GONE
               addcartValue(quanitiy.toString(), "minus")
            } else {
                quanitiy = total.toInt() - 1
                tvQuantity.text = quanitiy.toString()
               addcartValue(quanitiy.toString(), "minus")
            }

        }

    }
    fun addcartValue(count: String, action: String) {
        if (action.equals("add", ignoreCase = true)) {
            totcount = totcount.plus(1)
            edt_quanitiy.text = totcount.toString()
            storedinLocal(count)
        } else if (action.equals("minus", ignoreCase = true)) {
            if (count == "0") {
                totcount = 0
                storedinLocal(count)
            } else totcount = totcount.minus(1)
            edt_quanitiy.text = totcount.toString()
            storedinLocal(count)
        }
    }

    private fun storedinLocal(count: String) {
        class UpdateTask : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void?{
                DatabaseClient.getInstance(applicationContext)?.appDatabase
                        ?.taskDao()
                        ?.updateProduct(count.toInt(), name, id, count.toInt())
                return null
            }

            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
            }

             }

        val st = UpdateTask()
        st.execute()
    }


    private fun viewSingleItemMethod(regId: Int) {
        class GetTasks : AsyncTask<Void?, Void?, List<Productfile>?>() {
            override fun doInBackground(vararg params: Void?): List<Productfile>? {
                productionList = DatabaseClient
                        .getInstance(this@SingleItemView)
                        ?.appDatabase
                        ?.taskDao()
                        ?.getParticular(regId)
                return productionList
            }

            override fun onPostExecute(tasks: List<Productfile>?) {
                super.onPostExecute(tasks)
                edt_name?.setText(productionList?.get(0)?.name)
                edt_pid?.setText(productionList?.get(0)?.pid)
                edt_desc?.setText(productionList?.get(0)?.description)
                edt_price?.setText(productionList?.get(0)?.price.toString())
                edt_quanitiy?.setText(productionList?.get(0)?.quanitiy)
                id= productionList?.get(0)?.regid!!
                name= productionList?.get(0)?.name.toString()
//                totcount=productionList?.get(0)?.count!!
            }
        }


        val gt = GetTasks()
        gt.execute()


    }
}
