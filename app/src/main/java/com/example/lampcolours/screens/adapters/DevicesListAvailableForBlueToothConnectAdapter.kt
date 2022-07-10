package com.example.lampcolours.screens.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lampcolours.R
import com.example.lampcolours.models.domain.BluetoothItem
import com.example.lampcolours.databinding.ItemLayoutBinding
import com.example.lampcolours.screens.blueToothScreen.BlueToothFragment
import com.example.lampcolours.screens.blueToothScreen.BlueToothViewModel

class DevicesListAvailableForBlueToothConnectAdapter :
    ListAdapter<BluetoothItem, DevicesListAvailableForBlueToothConnectAdapter.ArrayViewHolder>(
        ArrayComparator()
    ) {

    override fun onViewAttachedToWindow(holder: ArrayViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.itemView
            .setOnClickListener { view: View ->
                BlueToothFragment.onBtItemClicked(getItem(holder.adapterPosition), view)
            }
    }

    class ArrayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemLayoutBinding.bind(view)

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
