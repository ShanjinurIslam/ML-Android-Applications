package com.shanjinur.irisspecies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shanjinur.irisspecies.databinding.FragmentFormBinding
import com.shanjinur.irisspecies.databinding.FragmentResultBinding
import com.shanjinur.irisspecies.viewmodel.UserModel

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private lateinit var userModel:UserModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        userModel = ViewModelProvider(this).get(UserModel::class.java)

        _binding!!.prediction.text = arguments?.getString("predicted_class")

        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}