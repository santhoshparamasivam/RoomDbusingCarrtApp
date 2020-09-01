package com.example.mytask.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytask.MainActivity
import com.example.mytask.R
import com.example.mytask.SingleItemView
import com.example.mytask.model.Productfile
import java.util.*

class CartListAdapter(private val context: Context, productList: List<Productfile>?) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    private var productlist: List<Productfile>? = ArrayList()
//    val templist: List<*> = ArrayList<Any?>()
//    var lastPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.single_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productFile = productlist!![position]
        if (productFile.count == 0) {
            holder.itemName.text = productFile.name
            holder.plistText.text = productFile.description
            holder.tvQuantity.text = productFile.count.toString()
            holder.price.text = "Rs " + productFile.price.toString()
            holder.tvQuantity.visibility = View.GONE
            holder.add.visibility = View.VISIBLE
            holder.cartMinusImg.visibility = View.GONE
            holder.cartPlusImg.visibility = View.GONE
        } else {
            holder.itemName.text = productFile.name
            holder.plistText.text = productFile.description
            holder.tvQuantity.text = productFile.count.toString()
            holder.price.text = "Rs " + productFile.price.toString()
            holder.tvQuantity.visibility = View.VISIBLE
            holder.add.visibility = View.GONE
            holder.cartMinusImg.visibility = View.VISIBLE
            holder.cartPlusImg.visibility = View.VISIBLE
        }
        holder.add.setOnClickListener {
            holder.tvQuantity.visibility = View.VISIBLE
            holder.add.visibility = View.GONE
            holder.cartMinusImg.visibility = View.VISIBLE
            holder.cartPlusImg.visibility = View.VISIBLE
            holder.tvQuantity.text = 1.toString()
            (context as MainActivity).AddcartValue("1", "Add", productFile)
        }
        holder.cartPlusImg.setOnClickListener {
            var total = holder.tvQuantity.text.toString()
            var quanitiy = total.toInt()
            if (total.toInt() != 20) {
                quanitiy = total.toInt() + 1
                holder.tvQuantity.text = quanitiy.toString()
                (context as MainActivity).AddcartValue(quanitiy.toString(), "Add", productFile)
            }
        }
        holder.cartMinusImg.setOnClickListener {
            var total = holder.tvQuantity.text.toString()
            var quanitiy = total.toInt()
            if (total.toInt() == 1) {
                quanitiy = 0
                holder.tvQuantity.visibility = View.GONE
                holder.add.visibility = View.VISIBLE
                holder.cartMinusImg.visibility = View.GONE
                holder.cartPlusImg.visibility = View.GONE
                (context as MainActivity).AddcartValue(quanitiy.toString(), "minus", productFile)
            } else {
                quanitiy = total.toInt() - 1
                holder.tvQuantity.text = quanitiy.toString()
                (context as MainActivity).AddcartValue(quanitiy.toString(), "minus", productFile)
            }
        }
        holder.rlay.setOnClickListener {
            val intent = Intent(context, SingleItemView::class.java)
            intent.putExtra("RegId", productFile.regid)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return productlist?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var plistText = itemView.findViewById<TextView>(R.id.plist_text)
        var itemName = itemView.findViewById<TextView>(R.id.from_name)
        var add = itemView.findViewById<TextView>(R.id.Add)
        var tvQuantity = itemView.findViewById<TextView>(R.id.cart_quantity_tv)
        var price = itemView.findViewById<TextView>(R.id.price)
        var cartMinusImg = itemView.findViewById<ImageView>(R.id.cart_minus_img)
        var cartPlusImg = itemView.findViewById<ImageView>(R.id.cart_plus_img)
        var imgDeleteitem: ImageView? = null
        var rlay=itemView.findViewById<RelativeLayout>(R.id.rlay)

    }

    init {
        productlist = productList
    }
}