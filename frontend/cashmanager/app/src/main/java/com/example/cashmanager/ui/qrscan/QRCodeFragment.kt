package com.example.cashmanager.ui.qrscan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cashmanager.R
import com.example.cashmanager.ui.home.HomeViewModel
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import java.lang.NumberFormatException

class QRCodeFragment : Fragment(), BarcodeCallback {

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
        var productCode: Int? = null
    }

    private lateinit var barcodeView: CompoundBarcodeView

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: HomeViewModel by activityViewModels()
        homeViewModel = viewModel
        val root = inflater.inflate(R.layout.fragment_qr_code, container, false)
        barcodeView = root.findViewById(R.id.barcode_scanner_view)
        barcodeView.setStatusText("")
        barcodeView.decodeContinuous(this)

        homeViewModel.product.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                onResume()
            }
        })

        homeViewModel.scanStatusError.observe(viewLifecycleOwner, Observer {
            if (it != null)
                onResume()
        })

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!checkCameraPermission()) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkCameraPermission()) {
            barcodeView.resume()
        }
    }

    override fun onPause() {
        barcodeView.pause()
        super.onPause()
    }

    private fun checkCameraPermission(): Boolean {
        return context?.let {
            ActivityCompat.checkSelfPermission(it, Manifest.permission.CAMERA)
        } != PackageManager.PERMISSION_DENIED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(context, "camera permission granted", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(context, "camera permission denied", Toast.LENGTH_LONG).show()
        }
    }

    override fun barcodeResult(result: BarcodeResult) {
        try {
            Log.i("result of bar code = ", result.toString())
            productCode = result.toString().toInt()
            homeViewModel.getProductById(productCode.toString().toInt())
            //view?.visibility = GONE
            onPause()
        } catch (error : NumberFormatException) {
            Toast.makeText(context, "scan error: invalid qrcode", Toast.LENGTH_LONG).show()
        }
    }
}