package com.shanjinur.irisspecies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.shanjinur.irisspecies.databinding.FragmentFormBinding
import com.shanjinur.irisspecies.viewmodel.UserModel

class FormFragment : Fragment() {
    private lateinit var userModel:UserModel
    private var _binding: FragmentFormBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        val view = binding.root

        userModel = ViewModelProvider(this).get(UserModel::class.java)

        _binding!!.submit.setOnClickListener {
            val sl = _binding!!.sepalLength.text.toString().toFloat()
            val sw = _binding!!.sepalWidth.text.toString().toFloat()
            val pl = _binding!!.petalLength.text.toString().toFloat()
            val pw = _binding!!.petalWidth.text.toString().toFloat()

            userModel.createRecord(sl,sw,pl, pw)
            val prediction = userModel.predict(requireContext())

            val bundle = bundleOf("predicted_class" to prediction)

            view.findNavController().navigate(R.id.action_formFragment_to_resultFragment,bundle)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}