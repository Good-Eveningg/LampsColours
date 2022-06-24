package com.example.lampcolours.screens.blueToothScreen

import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.lampcolours.R
import com.example.lampcolours.databinding.FragmentBlueToothBinding
import com.example.lampcolours.models.domain.BluetoothItem
import com.example.lampcolours.screens.adapters.DevicesListAvailableForBlueToothConnectAdapter
import com.example.lampcolours.screens.startScreen.StartFragment
import kotlinx.android.synthetic.main.fragment_blue_tooth.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val REQUEST_ENABLE_BT = 15

class BlueToothFragment : Fragment() {

    private val blueToothViewModel by viewModel<BlueToothViewModel>()
    private lateinit var binding: FragmentBlueToothBinding
    private val btManager by lazy {
        getSystemService(
            requireContext(),
            BluetoothManager::class.java
        )
    }
    private val btAdapter by lazy { btManager?.adapter }
    private lateinit var adapter: DevicesListAvailableForBlueToothConnectAdapter
    private val tempList = ArrayList<BluetoothItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlueToothBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fab = binding.floatingActionButton
        fab.imageTintList = ColorStateList.valueOf(Color.WHITE)
        val bap = binding.connectButton
        bap.imageTintList = ColorStateList.valueOf(Color.WHITE)

        adapter = DevicesListAvailableForBlueToothConnectAdapter()
        setImage()
        binding.floatingActionButton.setOnClickListener {
            bTSwitcher()
        }

        blueToothViewModel.devicesListToBTConnect.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        btAdapter?.let { blueToothViewModel.getDevicesList(it) }

        binding.connectButton.setOnClickListener {
            blueToothViewModel.connectDevice(currentMAC, btAdapter)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, StartFragment())
                .commit()
            Toast.makeText(view.context, "Saved MAC: $currentMAC", Toast.LENGTH_LONG).show()
        }
    }

    private fun bTSwitcher() {
        if (btAdapter?.isEnabled == false) {
            val i = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(i, REQUEST_ENABLE_BT)
            binding.connectButton.show()
        } else {
            btAdapter?.disable()
            floatingActionButton.setImageResource(R.drawable.ic_enable_bluetooth_white)
            binding.connectButton.hide()
            tempList.clear()
            binding.rvBtItems.adapter = adapter
            adapter.submitList(tempList)
        }
    }

    private fun setImage() {
        if (btAdapter?.isEnabled == true) {
            floatingActionButton.setImageResource(R.drawable.ic_disable_bluetooth)
            binding.rvBtItems.adapter = adapter
            binding.connectButton.show()

        } else {
            floatingActionButton.setImageResource(R.drawable.ic_enable_bluetooth_white)
            binding.connectButton.hide()
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

    }


    companion object {

        var currentMAC: String? = null
        fun onBtItemClicked(item: BluetoothItem, view: View) {
            currentMAC = item.mac
            Toast.makeText(view.context, "Current MAC: $currentMAC", Toast.LENGTH_LONG).show()
        }

    }

}