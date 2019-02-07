package com.kotlin.testmlkit

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.google.firebase.ml.vision.text.FirebaseVisionText
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // face
//
//        val options = FirebaseVisionFaceDetectorOptions.Builder()
//            .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
//            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ACCURATE) // 성능 (정확성)
//            .build()
//
//        val detector = FirebaseVision.getInstance()
//            .getVisionFaceDetector(options)
//
//        val result = detector.detectInImage(imageForBitmap((test.drawable as BitmapDrawable).bitmap))
//            .addOnSuccessListener { faces ->
//                for (face in faces) {
//                    Log.d("hoho", "smile: ${face.boundingBox.centerX()}")
//                    Log.d("hoho", "smile: ${face.boundingBox.centerY()}")
//                    Log.d("hoho", "smile: ${face.boundingBox.width()}") // 얼굴 옆
//                    Log.d("hoho", "smile: ${face.boundingBox.height()}") // 얼굴 높이
//                    Log.d("hoho", "윤관선: ${face.headEulerAngleZ}")
//                }
//            }
//            .addOnFailureListener { err ->
//
//            }

        // text
        val detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer
        val image = (test.drawable as BitmapDrawable).bitmap
        val result = detector.processImage(imageForBitmap((test.drawable as BitmapDrawable).bitmap))
            .addOnSuccessListener { firebaseVisionText ->
                test.setImageBitmap(null)


                val mutableImage = image.copy(Bitmap.Config.ARGB_8888, true)
                recognizeText(firebaseVisionText, mutableImage)

                test.setImageBitmap(mutableImage)
//                for (block in firebaseVisionText.textBlocks) {
//                    val blockText = block.text
//                    val blockConfidence = block.confidence
//                    val blockLanguages = block.recognizedLanguages
//                    val blockCornerPoints = block.cornerPoints
//                    val blockFrame = block.boundingBox
//                    for (line in block.lines) {
//                        val lineText = line.text
//                        val lineConfidence = line.confidence
//                        val lineLanguages = line.recognizedLanguages
//                        val lineCornerPoints = line.cornerPoints
//                        val lineFrame = line.boundingBox
//                        for (element in line.elements) {
//                            if (Pattern.matches("^[A-Z]*$", element.text)) {
//                                Log.d("hoho", "${element.text}")
//                            }
//                            val elementText = element.text
//                            val elementConfidence = element.confidence
//                            val elementLanguages = element.recognizedLanguages
//                            val elementCornerPoints = element.cornerPoints
//                            val elementFrame = element.boundingBox
//                        }
//                    }
//                }
            }
            .addOnFailureListener {
            }
    }
    private fun recognizeText(result: FirebaseVisionText?, image: Bitmap?) {
        if (result == null || image == null) {
            return
        }

        val canvas = Canvas(image)
        val rectPaint = Paint()
        rectPaint.color = Color.RED
        rectPaint.style = Paint.Style.STROKE
        rectPaint.strokeWidth = 4F
        val textPaint = Paint()
        textPaint.color = Color.RED
        textPaint.textSize = 40F

        var index = 0
        for (block in result.textBlocks) {
            for (line in block.lines) {
                canvas.drawRect(line.boundingBox, rectPaint)
                canvas.drawText(index.toString(), line.cornerPoints!![2].x.toFloat(), line.cornerPoints!![2].y.toFloat(), textPaint)
                index++
                Log.d("hoho", "$index, ${line.text}")
            }
        }
    }
    private fun imageForBitmap(bitmap: Bitmap) = FirebaseVisionImage.fromBitmap(bitmap)!!

}
