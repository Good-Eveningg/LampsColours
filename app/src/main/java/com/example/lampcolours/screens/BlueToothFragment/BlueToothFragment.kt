package com.example.lampcolours.screens.BlueToothFragment


import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService

import androidx.fragment.app.Fragment
import com.example.lampcolours.R
import com.example.lampcolours.adapters.BluetToothAdapter
import com.example.lampcolours.bt.BlueToothConnection
import com.example.lampcolours.bt.BluetoothItem
import com.example.lampcolours.databinding.FragmentBlueToothBinding
import kotlinx.android.synthetic.main.fragment_blue_tooth.*

const val REQUEST_ENABLE_BT = 15

class BlueToothFragment : Fragment() {
    lateinit var binding: FragmentBlueToothBinding
    val btManager by lazy { getSystemService(requireContext(), BluetoothManager::class.java) }
    val btAdapter by lazy { btManager?.adapter }
    private lateinit var adapter: BluetToothAdapter
    val tempList = ArrayList<BluetoothItem>()
    lateinit var btConnection: BlueToothConnection


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlueToothBinding.inflate(inflater, container, false)
        val fab = binding.floatingActionButton
        fab.imageTintList = ColorStateList.valueOf(Color.WHITE)
        val bap = binding.connectButton
        bap.imageTintList = ColorStateList.valueOf(Color.WHITE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = BluetToothAdapter()
        btConnection = btAdapter?.let { BlueToothConnection(it) }!!
        setImage()

        binding.floatingActionButton.setOnClickListener {
            bTSwitcher()
        }

        binding.connectButton.setOnClickListener {
            btConnection.connect(currentMAC)
        }
    }

    private fun bTSwitcher() {
        if (btAdapter?.isEnabled == false) {
            val i = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(i, REQUEST_ENABLE_BT)
        } else {
            btAdapter?.disable()
            floatingActionButton.setImageResource(R.drawable.ic_enable_bluetooth_white)
            tempList.clear()
            binding.rvBtItems.adapter = adapter
            adapter.submitList(tempList)
        }
    }

    private fun setImage() {
        if (btAdapter?.isEnabled == true) {
            floatingActionButton.setImageResource(R.drawable.ic_disable_bluetooth)
            binding.rvBtItems.adapter = adapter
            getPairedDevices()
        } else {
            floatingActionButton.setImageResource(R.drawable.ic_enable_bluetooth_white)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                floatingActionButton.setImageResource(R.drawable.ic_disable_bluetooth)
                binding.rvBtItems.adapter = adapter
                getPairedDevices()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        bTSwitcher()
    }

    private fun getPairedDevices() {
        val pairedDevices: Set<BluetoothDevice>? = btAdapter?.bondedDevices
        pairedDevices?.forEach {
            tempList.add(BluetoothItem(it.name, it.address))
        }
        adapter.submitList(tempList)
    }

    companion object {
        lateinit var currentMAC: String
        fun onBtItemClicked(item: BluetoothItem, view: View) {
            currentMAC = item.mac
            Toast.makeText(view.context, "Current MAC: $currentMAC", Toast.LENGTH_LONG).show()
        }
    }
}