package com.example.SMARTFRIDGE.ViewModeling

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.SMARTFRIDGE.Databasing.FoodMenuREPOSITORY

class FoodMenuVMFACTORY(
    private val repository: FoodMenuREPOSITORY
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodMenuVIEWMODEL::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FoodMenuVIEWMODEL(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
