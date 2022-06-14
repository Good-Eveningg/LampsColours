package com.example.lampcolours.screens.BlueToothFragment

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.lampcolours.R
import com.example.lampcolours.databinding.FragmentBlueToothBinding


class BlueToothFragment : Fragment() {
    lateinit var binding: FragmentBlueToothBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlueToothBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }

}