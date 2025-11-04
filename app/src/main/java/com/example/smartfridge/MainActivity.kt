package com.example.SMARTFRIDGE

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.SMARTFRIDGE.Databasing.FoodMenuDATABASE
import com.example.SMARTFRIDGE.Databasing.FoodMenuREPOSITORY
import com.example.SMARTFRIDGE.ViewModeling.FoodMenuVIEWMODEL
import com.example.SMARTFRIDGE.ViewModeling.FoodMenuVMFACTORY
import com.example.SMARTFRIDGE.ui.theme.FoodInventoryScreen

class MainActivity : ComponentActivity() {
    // LAZY ------> initiates when first accessed not OnCreate, deffers/ delays initialization
    private val database by lazy { FoodMenuDATABASE.getDatabase(this) } //gets DB instance using context (ID/access/permission)
    private val repository by lazy { FoodMenuREPOSITORY(database.foodMenuDao()) } // Creates repo with DAO
    private val viewModelFactory by lazy { FoodMenuVMFACTORY(repository) } // factory created w repo dependecies
    private val viewModel by viewModels<FoodMenuVIEWMODEL> { viewModelFactory }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) { // bundle backpack to store important info between cycles/routines saves and restores activity state
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FoodInventoryScreen(viewModel = viewModel)
                }
            }
        }
    }
}
