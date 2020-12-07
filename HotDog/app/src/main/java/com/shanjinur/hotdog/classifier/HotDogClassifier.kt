package com.shanjinur.hotdog.classifier

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.shanjinur.hotdog.ml.HotDogNotHotDogModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.TensorProcessor
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.label.TensorLabel

class HotDogClassifier(context: Context) {
    private val model:HotDogNotHotDogModel = HotDogNotHotDogModel.newInstance(context)

    fun predict(bitmap: Bitmap):String{
        try {
            // Tensorflow representation of the image
            var tfImage = TensorImage(DataType.FLOAT32)
            // Loading the original android image to the tensorflow image
            tfImage.load(bitmap)

            // Processing the image
            // The model we build uses 224x224 images as an input so we need so resize the image
            val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
                .build()
            tfImage = imageProcessor.process(tfImage)

            // Apply normalization operator for image classification (a necessary step)
            val probabilityProcessor =
                TensorProcessor.Builder().add(NormalizeOp(0f, 255f)).build()

            // running classification
            val outputs =
                model.process(probabilityProcessor.process(tfImage.tensorBuffer))
            // getting the output
            val outputBuffer = outputs.outputFeature0AsTensorBuffer

            // adding labels to the output
            val tensorLabel =
                TensorLabel(arrayListOf("hot_dog", "not_hot_dog"), outputBuffer)

            // getting the first label (hot dog) probability
            // if 80 (you can change that) then we are pretty sure it is a hotdog -> update UI
            val probability = tensorLabel.mapWithFloatValue["hot_dog"]
            probability?.let {
                Log.d("Probability Value",it.toString())

                if (it > 0.90) {
                    return "Hotdog!"
                } else {
                    return "Not Hotdog!"
                }
            }
            // Logs for debugging
            Log.d("sdf", "HOT DOG : " + probability)
        } catch (e: Exception) {
            Log.d("sdf", "Exception is " + e.localizedMessage)
        }

        return String()
    }

    fun close(){
       model.close()
    }
}