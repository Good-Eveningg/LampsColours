package com.example.lampcolours.screens.colorsPalette.colorPalette

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lampcolours.APP
import com.example.lampcolours.COLOR_ID
import com.example.lampcolours.R
import com.example.lampcolours.databinding.FragmentColorPaletteBinding
import com.example.lampcolours.models.colorPalette.ColorPaletteModel
import com.example.lampcolours.screens.adapters.ColorPaletteListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import vadiole.colorpicker.ColorModel
import vadiole.colorpicker.ColorPickerDialog


class ColorPalette : Fragment() {

    private val colorPaletteViewModel by viewModel<ColorPaletteViewModel>()
    lateinit var binding: FragmentColorPaletteBinding
    lateinit var rw: RecyclerView
    private val adapter by lazy { context?.let { ColorPaletteListAdapter(it) } }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentColorPaletteBinding.inflate(inflater, container, false)
        APP.setLastClicked(2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        binding.addColor.setOnClickListener {
            val colorPicker: ColorPickerDialog = ColorPickerDialog.Builder()
                .setInitialColor(
                    Color.rgb(
                        colorPaletteViewModel.getRedColor()!!,
                        colorPaletteViewModel.getGreenColor()!!,
                        colorPaletteViewModel.getBlueColor()!!
                    )
                )
                .setColorModel(ColorModel.RGB)
                .setColorModelSwitchEnabled(true)
                .setButtonOkText(android.R.string.ok)
                .setButtonCancelText(android.R.string.cancel)
                .onColorSelected { color: Int ->
                    colorPaletteViewModel.insertColor(
                        ColorPaletteModel(
                            redColor = color.red,
                            greenColor = color.green,
                            blueColor = color.blue
                        )
                    )
                    colorPaletteViewModel.saveRedColorValueRGB(color.red)
                    colorPaletteViewModel.saveGreenColorValueRGB(color.green)
                    colorPaletteViewModel.saveBlueColorValueRGB(color.blue)
                }
                .create()
            colorPicker.show(childFragmentManager, "color_picker")
        }
    }

    private fun init() {
        binding.addColor.imageTintList = ColorStateList.valueOf(Color.WHITE)
        context?.let { colorPaletteViewModel.initColorPaletteDataBase(it) }
        rw = binding.colorPaletteRV
        rw.adapter = adapter
        colorPaletteViewModel.getAllColors().observe(viewLifecycleOwner) { listColor ->
            adapter?.setList(listColor)
        }
    }


    companion object {

        fun clickColor(colorPaletteModel: ColorPaletteModel, view: View) {
            val bundle = Bundle()
            bundle.putSerializable(COLOR_ID, colorPaletteModel)
            view.findNavController().navigate(R.id.action_colorPalette2_to_detailedFragment, bundle)
        }

    }
}
