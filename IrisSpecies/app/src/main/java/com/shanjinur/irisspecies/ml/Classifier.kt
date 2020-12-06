package com.shanjinur.irisspecies.ml

import android.content.Context
import com.shanjinur.irisspecies.model.Record
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.label.TensorLabel
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class Classifier(context: Context) {
    private var model: Model = Model.newInstance(context)
    private val labels = arrayListOf("Iris-setosa", "Iris-versicolor","Iris-virginica")
    val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 4), DataType.FLOAT32)

    fun predict(record: Record): String {
        inputFeature0.loadArray(record.toFloatArr())
        val outputs = model.process(inputFeature0)
        val outputBuffer = outputs.outputFeature0AsTensorBuffer
        val tensorLabel = TensorLabel(labels, outputBuffer)

        var max:Double = -1.0
        var index:Int = -1
        var count:Int = 0

        for (each in outputBuffer.floatArray){
            if(max<each){
                max = each.toDouble()
                index = count
            }
            count += 1
        }

        return labels[index]
    }

    fun close(): Unit {
        model.close()
    }
}