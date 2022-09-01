package com.example.lampcolours.screens.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lampcolours.APP
import com.example.lampcolours.R
import com.example.lampcolours.models.colorPalette.ColorPaletteModel
import com.example.lampcolours.screens.colorsPalette.colorPalette.ColorPalette
import kotlinx.android.synthetic.main.palette_item.view.*

class ColorPaletteListAdapter(private val context: Context) :
    RecyclerView.Adapter<ColorPaletteListAdapter.ColorPaletteViewHolder>() {

    var colorList = emptyList<ColorPaletteModel>()

    class ColorPaletteViewHolder(view: View) : RecyclerView.ViewHolder(view)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPaletteViewHolder {
        return ColorPaletteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.palette_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ColorPaletteViewHolder, position: Int) {
        holder.itemView.colorToSend.setBackgroundColor(
            Color.rgb(
                colorList[position].redColor,
                colorList[position].greenColor,
                colorList[position].blueColor
            )
        )

    }

    override fun getItemCount(): Int {
        return colorList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<ColorPaletteModel>) {
        colorList = list
        notifyDataSetChanged()
    }

    override fun onViewAttachedToWindow(holder: ColorPaletteViewHolder) {
        holder.itemView.setOnLongClickListener {
            ColorPalette.clickColor(colorList[holder.adapterPosition], it)
            true
        }

        holder.itemView.setOnClickListener {

            APP.sendMessageToArduino(
                colorList[holder.adapterPosition].redColor,
                colorList[holder.adapterPosition].greenColor,
                colorList[holder.adapterPosition].blueColor,
                colorList[holder.adapterPosition].brightnessValue,
                colorList[holder.adapterPosition].lampSwitchOnStatus
            )

        }
    }

}