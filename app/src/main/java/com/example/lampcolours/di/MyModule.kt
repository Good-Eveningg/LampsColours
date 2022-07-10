package com.example.lampcolours.di

import com.example.lampcolours.data.blueTooth.ArduinoCommunicationImpl
import com.example.lampcolours.data.blueTooth.ConnectedDeviceCommunicationImpl
import com.example.lampcolours.data.colorPickerDataRepo.ColorRepoImpl
import com.example.lampcolours.data.repositories.blueToothRepo.BlueToothRepoImpl
import com.example.lampcolours.data.repositories.permissionRepo.PermissionRepoImpl
import com.example.lampcolours.data.repositories.sharedPrefRepo.SharedPrefRepoImpl
import com.example.lampcolours.models.domain.ArduinoResponse
import com.example.lampcolours.screens.MainActivityViewModel
import com.example.lampcolours.screens.blueToothScreen.BlueToothViewModel
import com.example.lampcolours.screens.startScreen.StartViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { ArduinoCommunicationImpl() }
    single { ConnectedDeviceCommunicationImpl() }
    single { BlueToothRepoImpl(get(), get()) }
    single { SharedPrefRepoImpl(androidContext()) }
    single { PermissionRepoImpl(androidContext()) }
    single { ColorRepoImpl(androidContext()) }
}

val viewModelModule = module {
    viewModel { BlueToothViewModel(get(), get()) }
    viewModel { StartViewModel(get(), get(), get()) }
    viewModel { MainActivityViewModel(get(), get(), get()) }
}