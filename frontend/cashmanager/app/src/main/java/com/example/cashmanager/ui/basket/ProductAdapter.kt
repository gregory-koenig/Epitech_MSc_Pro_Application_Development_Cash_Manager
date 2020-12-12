package com.example.cashmanager.ui.basket

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.cashmanager.R
import com.example.cashmanager.data.model.Product
import kotlinx.android.synthetic.main.fragment_basket.view.*
import kotlinx.android.synthetic.main.product_list_item.view.*

class ProductAdapter(val data: MutableLiveData<List<Product>>, owner: LifecycleOwner) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private lateinit var products: List<Product>
    var addQuantity: ((Int) -> Unit)? = null
    var removeQuantity: ((Int) -> Unit)? = null
    var removeProductFromBasket: ((Int) -> Unit)? = null

    init {
        data.observe(owner, Observer {
            Log.i("products on observe = ", it.toString())
            products = it
        })
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
        )
    }

    fun update() {
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("products = ", products.toString())
        holder.title.text = products[position].title
        holder.price.text = (products[position].price.toFloat() / 100).toString() + "€"
        holder.cashback.text =
            "Points: +" + (products[position].cashback.toFloat() / 100).toString() + "€"
        holder.quantity.text =
            "x " + products[position].quantity + "/" + products[position].stockQuantity
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val title: TextView = view.tv_product
        val price: TextView = view.price_calc
        val cashback: TextView = view.cashback
        val quantity: TextView = view.quantity

        init {
            view.addQuantity.setOnClickListener {
                addQuantity?.invoke(adapterPosition)
                notifyDataSetChanged()
            }
            view.removeQuantity.setOnClickListener{
                removeQuantity?.invoke(adapterPosition)
                notifyDataSetChanged()
            }
            view.removeProduct.setOnClickListener{
                removeProductFromBasket?.invoke(adapterPosition)
                notifyDataSetChanged()
            }
        }
    }
}