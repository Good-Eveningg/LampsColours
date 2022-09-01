package com.example.lampcolours.screens.colorsPalette.detailedColorPaletteScreen

import android.graphics.Color
import android.graphics.ColorSpace
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import com.example.lampcolours.APP
import com.example.lampcolours.COLOR_ID
import com.example.lampcolours.R
import com.example.lampcolours.databinding.FragmentDetailedBinding
import com.example.lampcolours.models.colorPalette.ColorPaletteModel
import kotlinx.android.synthetic.main.fragment_detailed.*
import kotlinx.android.synthetic.main.fragment_detailed.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import vadiole.colorpicker.ColorModel
import vadiole.colorpicker.ColorPickerDialog


class DetailedFragment : Fragment() {

    lateinit var binding: FragmentDetailedBinding
    private val detailedFragmentViewModel by viewModel<DetailedFragmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailedBinding.inflate(inflater, container, false)
        APP.setLastClicked(3)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailedFragmentViewModel.getCurrentColor(arguments?.getSerializable(COLOR_ID) as ColorPaletteModel)

        binding.button.setBackgroundColor(
            Color.rgb(
                detailedFragmentViewModel.getRed(),
                detailedFragmentViewModel.getGreen(),
                detailedFragmentViewModel.getBlue()
            )
        )

        binding.button.setOnClickListener {
            val colorPicker: ColorPickerDialog = ColorPickerDialog.Builder()
                .setInitialColor(
                    Color.rgb(
                        detailedFragmentViewModel.getRed(),
                        detailedFragmentViewModel.getGreen(),
                        detailedFragmentViewModel.getBlue()
                    )
                )
                .setColorModel(ColorModel.RGB)
                .setColorModelSwitchEnabled(true)
                .setButtonOkText(android.R.string.ok)
                .setButtonCancelText(android.R.string.cancel)
                .onColorSelected { color: Int ->
                    detailedFragmentViewModel.saveRed(color.red)
                    detailedFragmentViewModel.saveGreen(color.green)
                    detailedFragmentViewModel.saveBlue(color.blue)
                    binding.button.setBackgroundColor(
                        Color.rgb(
                            color.red, color.green, color.blue
                        )
                    )
                }
                .create()
            colorPicker.show(childFragmentManager, "color_picker")
        }

        binding.brightnessBar.progress = detailedFragmentViewModel.getBrightness()
        binding.brightnessStatus.text = binding.brightnessBar.progress.toString()

        binding.brightnessBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                detailedFragmentViewModel.saveBrightnessValue(brightnessBar.progress)
                brightnessStatus.text = brightnessBar.progress.toString()

            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                brightnessBar.progress = detailedFragmentViewModel.getBrightness()
            }

            override fun onStopTrackingTouch(seek: SeekBar) {

            }

        })

        binding.saveButton.setOnClickListener {
            detailedFragmentViewModel.updateCurrentColor()
        }

        binding.deleteButton.setOnClickListener {
            detailedFragmentViewModel.deleteCurrentColor()
            APP.navController.navigate(R.id.action_detailedFragment_to_colorPalette2)
        }

        binding.backButton.setOnClickListener {
            APP.navController.navigate(R.id.action_detailedFragment_to_colorPalette2)

        }
    }
}