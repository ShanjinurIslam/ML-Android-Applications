package com.shanjinur.irisspecies.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shanjinur.irisspecies.ml.Classifier
import com.shanjinur.irisspecies.model.Record

class UserModel:ViewModel() {
    lateinit var record:Record

    fun createRecord(sl:Float,sw:Float,pl:Float,pw:Float): Unit {
        record = Record(sl,sw,pl,sw)
    }

    fun predict(context:Context):String{
        val model = Classifier(context)
        val predictedClass:String = model.predict(record)
        model.close()
        return predictedClass
    }
}