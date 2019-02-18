package com.kotlin.testmlkit

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.util.*

@SuppressLint("Registered")
class BarcodeScanningActivity: AppCompatActivity() {
    private fun scanBarcodes(image: FirebaseVisionImage) {
        val options = FirebaseVisionBarcodeDetectorOptions.Builder()
            .setBarcodeFormats(
                FirebaseVisionBarcode.FORMAT_QR_CODE,
                FirebaseVisionBarcode.FORMAT_AZTEC)
            .build()

        val detector = FirebaseVision.getInstance()
            .visionBarcodeDetector

        // FirebaseVisionBarcodeDetector detector = FirebaseVision.getInstance()
        //        .getVisionBarcodeDetector(options);


        val result = detector.detectInImage(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    val bounds = barcode.boundingBox
                    val corners = barcode.cornerPoints

                    val rawValue = barcode.rawValue

                    val valueType = barcode.valueType
                    when (valueType) {
                        FirebaseVisionBarcode.TYPE_WIFI -> {
                            val ssid = barcode.wifi!!.ssid
                            val password = barcode.wifi!!.password
                            val type = barcode.wifi!!.encryptionType
                        }
                        FirebaseVisionBarcode.TYPE_URL -> {
                            val title = barcode.url!!.title
                            val url = barcode.url!!.url
                        }
                    }
                }
            }
            .addOnFailureListener {}
    }
}