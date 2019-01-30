package com.kotlin.testmlkit

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val options = FirebaseVisionFaceDetectorOptions.Builder()
            .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ACCURATE) // 성능 (정확성)
            .build()

        val detector = FirebaseVision.getInstance()
            .getVisionFaceDetector(options)

        val result = detector.detectInImage(imageForBitmap((test.drawable as BitmapDrawable).bitmap))
            .addOnSuccessListener { faces ->
                for (face in faces) {
                    Log.d("hoho", "smile: ${face.boundingBox.centerX()}")
                    Log.d("hoho", "smile: ${face.boundingBox.centerY()}")
                    Log.d("hoho", "smile: ${face.boundingBox.width()}") // 얼굴 옆
                    Log.d("hoho", "smile: ${face.boundingBox.height()}") // 얼굴 높이
                    Log.d("hoho", "윤관선: ${face.headEulerAngleZ}")
                }
            }
            .addOnFailureListener { err ->

            }

    }

    private fun imageForBitmap(bitmap: Bitmap) = FirebaseVisionImage.fromBitmap(bitmap)!!

}
