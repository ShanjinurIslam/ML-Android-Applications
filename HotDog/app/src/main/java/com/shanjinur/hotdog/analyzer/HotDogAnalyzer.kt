package com.shanjinur.hotdog.analyzer

import android.content.Context
import android.graphics.*
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.lifecycle.MutableLiveData
import com.shanjinur.hotdog.classifier.HotDogClassifier
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

class HotDogAnalyzer(val context: Context,val output:MutableLiveData<String>): ImageAnalysis.Analyzer {
    val model:HotDogClassifier = HotDogClassifier(context)

    fun toBitmap(image: ImageProxy): Bitmap? {
        Log.d("Image Type",(image.format == ImageFormat.YUV_420_888).toString())
        val yBuffer = image.planes[0].buffer // Y
        val vuBuffer = image.planes[2].buffer // VU

        val ySize = yBuffer.remaining()
        val vuSize = vuBuffer.remaining()

        val nv21 = ByteArray(ySize + vuSize)

        yBuffer.get(nv21, 0, ySize)
        vuBuffer.get(nv21, ySize, vuSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, image.width, image.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        val imageBytes = out.toByteArray()
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return bitmap
    }

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()    // Rewind the buffer to zero
        val data = ByteArray(remaining())
        get(data)   // Copy the buffer into a byte array
        return data // Return the byte array
    }

    override fun analyze(image: ImageProxy) {
        val bitmap_image = toBitmap(image)
        output.postValue(model.predict(bitmap_image!!))
        image.close()
    }
}