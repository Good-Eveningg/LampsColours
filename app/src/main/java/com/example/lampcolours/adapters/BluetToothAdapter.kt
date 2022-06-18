package com.example.lampcolours.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lampcolours.R
import com.example.lampcolours.bt.BluetoothItem
import com.example.lampcolours.databinding.ItemLayoutBinding


class BluetToothAdapter :
    ListAdapter<BluetoothItem, BluetToothAdapter.ArrayViewHolder>(ArrayComparator()) {


    class ArrayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemLayoutBinding.bind(view)

        fun setData(item: BluetoothItem) {
            binding.name.text = item.name
            binding.mac.text = item.mac

        }

        companion object {
            fun create(parent: ViewGroup): ArrayViewHolder {
                return ArrayViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
                )
            }
        }
    }

    class ArrayComparator : DiffUtil.ItemCallback<BluetoothItem>() {
        override fun areItemsTheSame(oldItem: BluetoothItem, newItem: BluetoothItem): Boolean {
            return oldItem.mac == newItem.mac
        }

        override fun areContentsTheSame(oldItem: BluetoothItem, newItem: BluetoothItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArrayViewHolder {
        return ArrayViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ArrayViewHolder, position: Int) {
        holder.setData(getItem(position))
    }
}
