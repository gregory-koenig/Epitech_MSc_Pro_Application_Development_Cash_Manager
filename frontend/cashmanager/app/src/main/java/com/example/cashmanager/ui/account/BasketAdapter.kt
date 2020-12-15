package com.example.cashmanager.ui.account

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.cashmanager.R
import com.example.cashmanager.data.model.Basket
import com.example.cashmanager.data.model.Product
import kotlinx.android.synthetic.main.basket_list_item.view.*
import kotlinx.android.synthetic.main.product_list_item.view.*
import java.text.SimpleDateFormat

class BasketAdapter(val data: MutableLiveData<List<Basket>>, owner: LifecycleOwner) :
    RecyclerView.Adapter<BasketAdapter.ViewHolder>() {

    private lateinit var baskets: List<Basket>
    //var addQuantity: ((Int) -> Unit)? = null
    //var removeQuantity: ((Int) -> Unit)? = null
    //var removeProductFromBasket: ((Int) -> Unit)? = null

    init {
        data.observe(owner, Observer {
            Log.i("baskets on observe = ", it.toString())
            if (it != null) {
                baskets = it.sortedBy { it.createdAt }
            }
        })
    }

    override fun getItemCount(): Int {
        return  baskets.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.basket_list_item, parent, false)
        )
    }

    fun update() {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val basket : Basket = baskets[position]
        val price : Float = calcBasketPrice(basket)
        val date = SimpleDateFormat("dd/MM/yyyy hh:mm")
        holder.date.text = date.format(basket.createdAt)
        holder.price.text = String.format("%.2f€", price)
        holder.status.text = basket.status
        if (basket.status == "CANCELED") {
            holder.status.setCompoundDrawablesWithIntrinsicBounds(holder.canceledStatus, null, null, null)
            holder.status.setTextColor(Color.parseColor("#C91A21"))
        } else if (basket.status == "PENDING") {
            holder.status.setCompoundDrawablesWithIntrinsicBounds(holder.pendingStatus, null, null, null)
            holder.status.setTextColor(Color.parseColor("#1357C9"))
        } else {
            holder.status.setCompoundDrawablesWithIntrinsicBounds(holder.validatedStatus, null, null, null)
            holder.status.setTextColor(Color.parseColor("#15C936"))
        }
    }

    private fun calcBasketPrice(basket: Basket) : Float {
        var price : Float = 0.0F
        for(product in basket.products) {
            price += (product.price.toFloat() / 100)
        }
        return price
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        //val title: TextView = view.tv_product
        //val price: TextView = view.price_calc
        //val cashback: TextView = view.cashback
        //val quantity: TextView = view.quantity
        val date : TextView = view.basketDate
        val price : TextView = view.basketPrice
        val status : Button = view.basketStatus
        val canceledStatus : Drawable = view.context.getDrawable(R.drawable.ic_baseline_cancel_24)!!
        val pendingStatus : Drawable = view.context.getDrawable(R.drawable.ic_baseline_pending_24)!!
        val validatedStatus : Drawable = view.context.getDrawable(R.drawable.ic_baseline_check_circle_24)!!

        //init {
        //    view.addQuantity.setOnClickListener {
        //        addQuantity?.invoke(adapterPosition)
        //        notifyDataSetChanged()
        //    }
        //    view.removeQuantity.setOnClickListener{
        //        removeQuantity?.invoke(adapterPosition)
        //        notifyDataSetChanged()
        //    }
        //    view.removeProduct.setOnClickListener{
        //        removeProductFromBasket?.invoke(adapterPosition)
        //        notifyDataSetChanged()
        //    }
        //}
    }
}