package com.example.lampcolours.screens.startScreen

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

import com.example.lampcolours.databinding.FragmentStartBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
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


    private val startViewModel by viewModel<StartViewModel>()
    lateinit var binding: FragmentStartBinding

    var onoff = 0

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
        val brightnessSeek = binding.seekBar
        val brightnessText = binding.brightnessStatus
        startViewModel.getBrightness()?.let { binding.seekBar.progress = it }
        brightnessText.text = startViewModel.getBrightness().toString()
        val onoffswitcher = binding.switcher
        val colorpicker = binding.colorPicker



        brightnessSeek.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                startViewModel.saveBrightnessValue(brightnessSeek.progress)
                brightnessText.text = startViewModel.getBrightness().toString()

            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                brightnessSeek.progress = startViewModel.getBrightness()!!
            }

            override fun onStopTrackingTouch(seek: SeekBar) {

                Toast.makeText(
                    context, "Уровень яркости: " + "${startViewModel.getBrightness()}" + "%",
                    Toast.LENGTH_LONG
                ).show()
                if (onoff == 1) {
                    startViewModel.sendMessageToArduino(
                        "${startViewModel.getRedColor()} ${startViewModel.getGreenColor()} " +
                                "${startViewModel.getBlueColor()} ${startViewModel.getBrightness()} $onoff",
                        btAdapter!!
                    )
                }
            }

        })
        brightnessSeek.progress = startViewModel.getBrightness()!!


        onoffswitcher.setOnCheckedChangeListener { _, isChecked ->
            try {
                if (btAdapter?.isEnabled == true) {
                    onoff = if (isChecked) {
                        1
                    } else {
                        0
                    }
                    startViewModel.sendMessageToArduino(
                        "${startViewModel.getRedColor()} ${startViewModel.getGreenColor()} " +
                                "${startViewModel.getBlueColor()} ${startViewModel.getBrightness()} $onoff",
                        btAdapter!!
                    )

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
                .setInitialColor(
                    Color.rgb(
                        startViewModel.getRedColor()!!,
                        startViewModel.getGreenColor()!!, startViewModel.getBlueColor()!!
                    )
                )
                .setColorModel(ColorModel.RGB)
                .setColorModelSwitchEnabled(true)
                .setButtonOkText(R.string.ok)
                .setButtonCancelText(R.string.cancel)
                .onColorSelected { color: Int ->
                    startViewModel.saveRedColorValueRGB(color.red)
                    startViewModel.saveGreenColorValueRGB(color.green)
                    startViewModel.saveBlueColorValueRGB(color.blue)
                    if (onoff == 1) {
                        startViewModel.sendMessageToArduino(
                            "${startViewModel.getRedColor()} ${startViewModel.getGreenColor()} " +
                                    "${startViewModel.getBlueColor()} ${startViewModel.getBrightness()} $onoff",
                            btAdapter!!
                        )
                    }
                }
                .create()
            colorPicker.show(childFragmentManager, "color_picker")
        }
    }

}


