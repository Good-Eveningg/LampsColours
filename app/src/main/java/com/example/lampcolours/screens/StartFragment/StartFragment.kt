package com.example.lampcolours.screens.StartFragment

import android.R
import android.bluetooth.BluetoothManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.Fragment
import com.example.lampcolours.adapters.BluetToothAdapter
import com.example.lampcolours.bt.BlueToothConnection

import com.example.lampcolours.databinding.FragmentStartBinding
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.android.synthetic.main.fragment_start.view.*

import vadiole.colorpicker.ColorModel
import vadiole.colorpicker.ColorPickerDialog


class StartFragment : Fragment() {
    private val btManager by lazy {
        ContextCompat.getSystemService(
            requireContext(),
            BluetoothManager::class.java
        )
    }
    private val btAdapter by lazy { btManager?.adapter }
    private lateinit var adapter: BluetToothAdapter
    lateinit var btConnection: BlueToothConnection
    lateinit var binding: FragmentStartBinding
    var brightness = 10
    var onoff = 0
    var red = 255
    var green = 255
    var blue = 255
    lateinit var color: Color

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        color = Color.valueOf(red.toFloat(), green.toFloat(), blue.toFloat())
        val brightnessSeek = binding.seekBar
        val brightnessText = binding.brightnessStatus
        adapter = BluetToothAdapter()
        btConnection = btAdapter?.let { BlueToothConnection(it) }!!

        val onoffswitcher = binding.switcher
        val colorpicker = binding.colorPicker

        brightnessSeek.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                brightness = brightnessSeek.progress
                brightnessText.text = brightness.toString()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {

            }

            override fun onStopTrackingTouch(seek: SeekBar) {

                Toast.makeText(
                    context, "Уровень яркости: " + seek.progress + "%",
                    Toast.LENGTH_LONG
                ).show()
            }

        })
        brightnessSeek.setProgress(brightness)


        onoffswitcher.setOnCheckedChangeListener { _, isChecked ->
            val message: String
            if (isChecked) {

                message =
                    "Lamp is ON"
                onoff = 1
            } else {
                message = "Lamp is OFF"
                onoff = 0
            }

            Toast.makeText(
                context, "$message $red $green $blue $brightness $onoff",
                Toast.LENGTH_LONG
            ).show()
            btConnection.sendMessage("$red $green $blue $brightness $onoff")
        }

        colorpicker.setOnClickListener {

            val colorPicker: ColorPickerDialog = ColorPickerDialog.Builder()
                .setInitialColor(Color.rgb(red, green, blue))
                .setColorModel(ColorModel.RGB)
                .setColorModelSwitchEnabled(true)
                .setButtonOkText(android.R.string.ok)
                .setButtonCancelText(android.R.string.cancel)
                .onColorSelected { color: Int ->
                    red = color.red
                    green = color.green
                    blue = color.blue
                }
                .create()

            colorPicker.show(childFragmentManager, "color_picker")
        }

    }


}


