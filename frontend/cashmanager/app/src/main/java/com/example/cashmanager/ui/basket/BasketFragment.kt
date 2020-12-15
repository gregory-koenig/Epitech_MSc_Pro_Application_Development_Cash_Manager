package com.example.cashmanager.ui.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cashmanager.R
import com.example.cashmanager.data.model.Product
import com.example.cashmanager.ui.home.HomeViewModel
import com.example.cashmanager.ui.payment.PaymentFragment
import kotlinx.android.synthetic.main.fragment_basket.*

class BasketFragment : Fragment() {

    private lateinit var basketViewModel: BasketViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        basketViewModel =
            ViewModelProvider(this).get(BasketViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_basket, container, false)
        val viewModel: HomeViewModel by activityViewModels()
        homeViewModel = viewModel

        homeViewModel.products.observe(viewLifecycleOwner, Observer {
            var total: Float = 0.0F
            for (product in it) {
                total += (product.price.toFloat() / 100)
            }
            homeViewModel.totalBasket.apply { value = total }
        })

        homeViewModel.totalBasket.observe(viewLifecycleOwner, Observer {
            val validate: Button = root.findViewById(R.id.validateCart)
            validate.text = String.format("checkout : %.2f€", it)
        })


        if (homeViewModel.products.value == null) {
            homeViewModel.products.apply { value = ArrayList() }
            //productSample()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //callback des bouttons d'actions du product dans le basket
        val productAdapter = ProductAdapter(homeViewModel.products, viewLifecycleOwner)
        productAdapter.addQuantity = { productIndex ->
           homeViewModel.addQuantity(productIndex)
        }
        productAdapter.removeQuantity = { productIndex ->
            homeViewModel.removeQuantity(productIndex)
        }
        productAdapter.removeProductFromBasket = { productIndex ->
            homeViewModel.removeProductFromBasket(productIndex)
        }

        //Update du boutton de validation

        val validate : Button = view.findViewById(R.id.validateCart)
        val emptyBasketMessage : TextView = view.findViewById(R.id.emptyBasketMessage)

        validate.setOnClickListener{
            val validToast = Toast.makeText(view.context, "Cart successfully created", Toast.LENGTH_LONG)
            val invalidToast = Toast.makeText(view.context, "Cart creation failed", Toast.LENGTH_LONG)
            homeViewModel.createBasket(validToast, invalidToast)
            val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
            val prev = requireFragmentManager().findFragmentByTag("dialog")
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)

            val dialogFragment: DialogFragment = PaymentFragment(productAdapter)
            dialogFragment.show(ft, "dialog")
        }

        homeViewModel.products.observe(viewLifecycleOwner, Observer {
            if (it.isEmpty()) {
                validate.visibility = View.GONE
                emptyBasketMessage.visibility = View.VISIBLE
            } else {
                validate.visibility = View.VISIBLE
                emptyBasketMessage.visibility = View.GONE
            }
        })

        //update des data au recyclerview
        recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = productAdapter
        }
    }
}