package com.example.lampcolours.screens.blueToothScreen

import android.Manifest
import android.app.Activity.RESULT_OK
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.lampcolours.APP
import com.example.lampcolours.R
import com.example.lampcolours.REQUEST_ENABLE_BT
import com.example.lampcolours.databinding.FragmentBlueToothBinding
import com.example.lampcolours.models.domain.BluetoothItem
import com.example.lampcolours.screens.MainActivity
import com.example.lampcolours.screens.adapters.DevicesListAvailableForBlueToothConnectAdapter
import com.example.lampcolours.screens.startScreen.StartFragment
import kotlinx.android.synthetic.main.fragment_blue_tooth.*
import org.koin.androidx.viewmodel.ext.android.viewModel


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
    private val adapter: DevicesListAvailableForBlueToothConnectAdapter by lazy { DevicesListAvailableForBlueToothConnectAdapter() }
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlueToothBinding.inflate(inflater, container, false)
        APP.setLastClicked(1)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity = MainActivity()
        blueToothViewModel.devicesListToBTConnect.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        val fab = binding.floatingActionButton
        fab.imageTintList = ColorStateList.valueOf(Color.WHITE)
        val bap = binding.connectButton
        bap.imageTintList = ColorStateList.valueOf(Color.WHITE)
        setImage()


        binding.floatingActionButton.setOnClickListener {
            bTSwitcher()
            binding.rvBtItems.adapter = adapter
            btAdapter?.let { blueToothViewModel.getDevicesList(it) }
        }


        btAdapter?.let { blueToothViewModel.getDevicesList(it) }

        binding.connectButton.setOnClickListener {
            blueToothViewModel.saveMac(currentMAC)
            APP.navController.navigate(R.id.action_blueToothFragment_to_startFragment)
            Toast.makeText(view.context, "Saved MAC: $currentMAC", Toast.LENGTH_LONG).show()
        }
    }

    private fun bTSwitcher() {
        if (btAdapter?.isEnabled == false) {
            val i = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(i, REQUEST_ENABLE_BT)
            binding.connectButton.show()

        } else {
            if (context?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Manifest.permission.BLUETOOTH_CONNECT
                    )
                } != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            btAdapter?.disable()
            floatingActionButton.setImageResource(R.drawable.ic_enable_bluetooth_white)
            binding.connectButton.hide()
            blueToothViewModel.getDevicesList(btAdapter!!)
        }
    }

    private fun setImage() {
        if (btAdapter?.isEnabled == true) {
            floatingActionButton.setImageResource(R.drawable.ic_disable_bluetooth)
            binding.connectButton.show()
            binding.rvBtItems.adapter = adapter
            btAdapter?.let { blueToothViewModel.getDevicesList(it) }
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
                btAdapter?.let { blueToothViewModel.getDevicesList(it) }
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


    companion object {

        var currentMAC: String? = null
        fun onBtItemClicked(item: BluetoothItem, view: View) {
            currentMAC = item.mac
            Toast.makeText(view.context, "Current MAC: $currentMAC", Toast.LENGTH_LONG).show()
        }
    }

}