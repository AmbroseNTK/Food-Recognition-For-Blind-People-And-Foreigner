package ntk.ambrose.foodinspector.recognizer

import android.graphics.Bitmap

interface Classifier {
    fun recognizeImage(bitmap: Bitmap): ArrayList<Result>
}