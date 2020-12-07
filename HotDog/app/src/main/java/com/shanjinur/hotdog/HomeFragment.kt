package com.shanjinur.hotdog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.shanjinur.hotdog.databinding.FragmentCameraBinding
import com.shanjinur.hotdog.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        (activity as AppCompatActivity?)!!.supportActionBar?.title = "Home"

        _binding!!.start.setOnClickListener {
            Log.d("Clicked","Clicked")
            it.findNavController().navigate(R.id.action_homeFragment_to_cameraFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}