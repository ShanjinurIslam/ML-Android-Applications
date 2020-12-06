package com.shanjinur.irisspecies.model

data class Record(val sepal_length:Float,val sepal_width:Float,val petal_length:Float,val petal_width:Float){
    fun toFloatArr(): FloatArray {
        return floatArrayOf(sepal_length,sepal_width,petal_length,petal_width)
    }
}