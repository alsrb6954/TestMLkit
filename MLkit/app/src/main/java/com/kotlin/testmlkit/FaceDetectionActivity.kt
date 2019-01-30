package com.kotlin.testmlkit

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.face.FirebaseVisionFace
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark

class FaceDetectionActivity: AppCompatActivity() {

    fun detectFaces(image:FirebaseVisionImage){
        val options = FirebaseVisionFaceDetectorOptions.Builder()
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS) // smile, eyes open
            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS) // 눈, 코, 입 구별 여부
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ACCURATE) // 성능 (정확성)
            .setMinFaceSize(0.15f) // 최소 얼굴 크기
            .enableTracking() // 얼굴마다 고유값 넣는 것(선언 하면 true)
            .build()

        val detector = FirebaseVision.getInstance()
            .getVisionFaceDetector(options)

        val result = detector.detectInImage(image)
            .addOnSuccessListener { faces ->
                processFaceList(faces)
            }
            .addOnFailureListener { err ->

            }
    }

    // option 조
    fun faceOptionsExamples(){
        
        // 정확한 인식 (ex)사진)
        val highAccuracyOpts = FirebaseVisionFaceDetectorOptions.Builder()
            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            .build()

        // 실시간으로 검사 하는 거 속도 중요 (ex)카메라)
        val realTimeOpts = FirebaseVisionFaceDetectorOptions.Builder()
            .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
            .build()

        // 웃는 얼굴 실시간 검사
        val realTimeSmaileOpts = FirebaseVisionFaceDetectorOptions.Builder()
            .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
            .build()

    }

    // 감지된 얼굴의 정보 얻기
    fun processFaceList(faces: List<FirebaseVisionFace>){
        for (face in faces) {
            val bounds = face.boundingBox
            val rotY = face.headEulerAngleY
            val rotZ = face.headEulerAngleZ

            // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
            // nose available):
            val leftEar = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)
            leftEar?.let {
                val leftEarPos = leftEar.position
            }

            // If contour detection was enabled:
            val leftEyeContour = face.getContour(FirebaseVisionFaceContour.LEFT_EYE).points
            val upperLipBottomContour = face.getContour(FirebaseVisionFaceContour.UPPER_LIP_BOTTOM).points

            // If classification was enabled:
            if (face.smilingProbability != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                val smileProb = face.smilingProbability
            }
            if (face.rightEyeOpenProbability != FirebaseVisionFace.UNCOMPUTED_PROBABILITY) {
                val rightEyeOpenProb = face.rightEyeOpenProbability
            }

            // If face tracking was enabled:
            if (face.trackingId != FirebaseVisionFace.INVALID_ID) {
                val id = face.trackingId
            }

        }
    }
}