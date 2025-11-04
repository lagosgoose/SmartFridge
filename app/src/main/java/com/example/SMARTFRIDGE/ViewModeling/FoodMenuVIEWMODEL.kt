package com.example.SMARTFRIDGE.ViewModeling
// storin and managing UI data
// need to pass some input data by the factory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.SMARTFRIDGE.Databasing.FoodMenuREPOSITORY
import com.example.SMARTFRIDGE.Databasing.FoodMenu
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch
import java.time.LocalDate

// takes instance,, FMP --> uses dependeccy injection ,, VM inherits to give "lifecycle awareness" (adaptive behavior w associates)
class FoodMenuVIEWMODEL(private val repository: FoodMenuREPOSITORY) : ViewModel() {

    // getting all food from repo,,
    val allFoods: StateFlow<List<FoodMenu>> = repository.getAllFoods() // StateFlow auto update UI when it changes
        .stateIn( //////////////// converts cold to hot flow (cold waits) (hot doesnt)
            scope = viewModelScope, // uses VM coroutine scope  (canceled once VM clears)
            started = SharingStarted.WhileSubscribed(5000), // keeps flow active when subs (Composables) present and small buffer when leaving
            initialValue = emptyList()
        )

    val allFoodsByName: StateFlow<List<FoodMenu>> = repository.getAllFoodsByName()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val allFoodsByExpiration: StateFlow<List<FoodMenu>> =
        repository.getAllFoodsByExpirationDate()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )


/////////////////////// need 26 API instead of 24? ////////////////////////////////////
    @RequiresApi(Build.VERSION_CODES.O)
    fun addFood(foodName: String, expirationDate: LocalDate) {
        viewModelScope.launch {
            val foodMenu = FoodMenu(
                foodName = foodName,
                expirationDate = expirationDate
            )
            repository.addFood(foodMenu)
        }
    }

    fun updateFood(foodMenu: FoodMenu) {
        viewModelScope.launch { // launches coroutine , cancels if VM clears --> back button or new thing destroys it (VM)
            repository.updateFood(foodMenu)
        }
    }

    fun deleteFood(foodMenu: FoodMenu) {
        viewModelScope.launch {
            repository.deleteFood(foodMenu)
        }
    }

}