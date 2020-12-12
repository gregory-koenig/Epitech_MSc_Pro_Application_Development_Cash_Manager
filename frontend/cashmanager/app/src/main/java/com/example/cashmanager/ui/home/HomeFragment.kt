package com.example.cashmanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.cashmanager.R

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: HomeViewModel by activityViewModels()
        homeViewModel = viewModel
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        homeViewModel.initUser()

        val cardView: CardView = root.findViewById(R.id.productCard)
        val titleCard: TextView = root.findViewById(R.id.titleCard)
        val descriptionCard: TextView = root.findViewById(R.id.description)
        val priceCard: TextView = root.findViewById(R.id.price)
        val closeCardButton: Button = root.findViewById(R.id.closeCard)
        val addBasket : Button = root.findViewById(R.id.addBasket)


        homeViewModel.product.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                cardView.visibility = View.VISIBLE
                titleCard.text = it.title
                descriptionCard.text = it.description
                priceCard.text = "price : " + (it.price.toFloat() / 100).toString() + "€"
                if (it.stockQuantity == 0) {
                    addBasket.text = "Out of stock"
                    addBasket.isClickable = false
                    addBasket.isEnabled = false
                } else {
                    addBasket.text = "Add to cart"
                    addBasket.isClickable = true
                    addBasket.isEnabled = true
                }
            }
        })

        homeViewModel.scanStatusError.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(context, "scan error : invalid product", Toast.LENGTH_LONG).show()
            }
        })

        closeCardButton.setOnClickListener {
            homeViewModel.product.postValue(null)
            cardView.visibility = View.GONE
        }

        addBasket.setOnClickListener{
            homeViewModel.addProductToBasket()
            cardView.visibility = View.GONE
        }

        return root
    }

}