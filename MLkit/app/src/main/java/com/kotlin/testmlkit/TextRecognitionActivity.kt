package com.kotlin.testmlkit

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionCloudTextRecognizerOptions
import com.google.firebase.ml.vision.text.FirebaseVisionText
import java.util.*

class TextRecognitionActivity: AppCompatActivity() {

    // 기기별 모델에서 사용
   private fun recognizeText(image: FirebaseVisionImage) {
        val detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer

        val result = detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                processTextBlock(firebaseVisionText)
            }
            .addOnFailureListener {
            }
    }

    // cloud 모델에서 사용
    private fun recognizeTextCloud(image: FirebaseVisionImage) {
        val options = FirebaseVisionCloudTextRecognizerOptions.Builder()
            .setLanguageHints(Arrays.asList("en", "hi", "ko"))
            .build()

        val detector = FirebaseVision.getInstance().getCloudTextRecognizer(options)

        val result = detector.processImage(image)
            .addOnSuccessListener { firebaseVisionText ->
                processTextBlock(firebaseVisionText)
            }
            .addOnFailureListener {
            }
    }

    // 출력 내용
    private fun processTextBlock(result: FirebaseVisionText) {
        val resultText = result.text
        for (block in result.textBlocks) {
            val blockText = block.text
            val blockConfidence = block.confidence
            val blockLanguages = block.recognizedLanguages
            val blockCornerPoints = block.cornerPoints
            val blockFrame = block.boundingBox
            for (line in block.lines) {
                val lineText = line.text
                val lineConfidence = line.confidence
                val lineLanguages = line.recognizedLanguages
                val lineCornerPoints = line.cornerPoints
                val lineFrame = line.boundingBox
                for (element in line.elements) {
                    val elementText = element.text
                    val elementConfidence = element.confidence
                    val elementLanguages = element.recognizedLanguages
                    val elementCornerPoints = element.cornerPoints
                    val elementFrame = element.boundingBox
                }
            }
        }
    }
}