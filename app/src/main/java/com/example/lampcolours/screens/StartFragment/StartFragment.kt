package com.example.lampcolours.screens.StartFragment

import android.R
import android.bluetooth.BluetoothManager
import android.content.Context
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
import com.example.lampcolours.CURRENT_MAC
import com.example.lampcolours.SHARED_PREF_FILE_NAME
import com.example.lampcolours.adapters.BluetToothAdapter
import com.example.lampcolours.bt.BlueToothConnection
import com.example.lampcolours.databinding.FragmentStartBinding
import vadiole.colorpicker.ColorModel
import vadiole.colorpicker.ColorPickerDialog
import java.lang.Exception


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
    lateinit var currentMac: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val brightnessSeek = binding.seekBar
        val brightnessText = binding.brightnessStatus
        adapter = BluetToothAdapter()
        btConnection = btAdapter?.let { BlueToothConnection(it) }!!
        getSharedPref()
        val onoffswitcher = binding.switcher
        val colorpicker = binding.colorPicker
        if (btAdapter?.isEnabled == true && currentMac != "null") {
            btConnection.connect(currentMac)
        }

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
                brightnessSeek.progress = brightness
            }

            override fun onStopTrackingTouch(seek: SeekBar) {

                Toast.makeText(
                    context, "Уровень яркости: " + seek.progress + "%",
                    Toast.LENGTH_LONG
                ).show()
                if (onoff == 1) {
                    btConnection.sendMessage("$red $green $blue $brightness $onoff")
                }
            }

        })
        brightnessSeek.progress = brightness


        onoffswitcher.setOnCheckedChangeListener { _, isChecked ->
            try {
                if (btAdapter?.isEnabled == true) {
                    onoff = if (isChecked) {
                        1
                    } else {
                        0
                    }
                    btConnection.sendMessage("$red $green $blue $brightness $onoff")
                } else {
                    Toast.makeText(
                        context, "Switch on BT",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    context, "Connect to the Device",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        colorpicker.setOnClickListener {
            val colorPicker: ColorPickerDialog = ColorPickerDialog.Builder()
                .setInitialColor(Color.rgb(red, green, blue))
                .setColorModel(ColorModel.RGB)
                .setColorModelSwitchEnabled(true)
                .setButtonOkText(R.string.ok)
                .setButtonCancelText(R.string.cancel)
                .onColorSelected { color: Int ->
                    red = color.red
                    green = color.green
                    blue = color.blue
                    if (onoff == 1) {
                        btConnection.sendMessage("$red $green $blue $brightness $onoff")
                    }
                }
                .create()
            colorPicker.show(childFragmentManager, "color_picker")
        }
    }

    private fun getSharedPref(): String? {
        val sharedPref = context?.getSharedPreferences(SHARED_PREF_FILE_NAME, Context.MODE_PRIVATE)
        currentMac = sharedPref?.getString(CURRENT_MAC, null).toString()
        return sharedPref?.getString(CURRENT_MAC, null)
    }


}


