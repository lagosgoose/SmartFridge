package com.example.SMARTFRIDGE.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.SMARTFRIDGE.Databasing.*
import com.example.SMARTFRIDGE.ViewModeling.*
import com.example.SMARTFRIDGE.Databasing.FoodMenuDAO
import com.example.SMARTFRIDGE.Databasing.FoodMenuDATABASE
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodInventoryScreen(viewModel: FoodMenuVIEWMODEL) {
    val foodItems by viewModel.allFoods.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Food Item")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (foodItems.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            "USE THE ADD BUTTON TO ADD FOOD",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                // Summary Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                }

                // Dynamic Table
                FoodItemTable(
                    foodItems = foodItems,
                    onDeleteItem = { foodItem ->
                        viewModel.deleteFood(foodItem)
                    }
                )
            }
        }

        if (showAddDialog) {
            AddFoodItemDialog(
                onDismiss = { showAddDialog = false },
                onAddItem = { name: String, date: LocalDate ->
                    viewModel.addFood(name,date)
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun FoodItemTable(
    foodItems: List<FoodMenu>,
    onDeleteItem: (FoodMenu) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Table Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Food Name",
                modifier = Modifier.weight(2f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                "Expires",
                modifier = Modifier.weight(1.5f),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.width(40.dp))
        }


        // Table Rows - Scrollable
        LazyColumn {
            items(foodItems, key = { it.itemNumberID }) { foodItem ->
                FoodItemRow(
                    foodItem = foodItem,
                    onDeleteItem = onDeleteItem
                )
            }
        }
    }
}

@Composable
fun FoodItemRow(
    foodItem: FoodMenu,
    onDeleteItem: (FoodMenu) -> Unit // VOID ,, doesnt return anything
) {

    val backgroundColor = when { ////////////////////////////////// when DB empty
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = foodItem.foodName,
            modifier = Modifier.weight(2f),
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = formatDate(foodItem.expirationDate),
            modifier = Modifier.weight(1.5f),
            style = MaterialTheme.typography.bodySmall
        )

        IconButton(
            onClick = { onDeleteItem(foodItem) }
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}///

@Composable
fun AddFoodItemDialog(
    onDismiss: () -> Unit, // callback when dialog is dismissed,, void
    onAddItem: (String, LocalDate) -> Unit //
) {
    var foodName by remember { mutableStateOf("") } //Creates observable state with empty string to initiate
    var selectedDays by remember { mutableStateOf(7) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Food Item") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = foodName,
                    onValueChange = { foodName = it },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    "DAYS UNTIL EXPIRATION:",
                    style = MaterialTheme.typography.labelLarge
                )

                // Quick select buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(1, 2, 3, 4, 5).forEach { days ->
                        FilterChip(
                            selected = selectedDays == days,
                            onClick = { selectedDays = days },
                            label = { Text("$days") }
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (foodName.isNotBlank()) {
                        val expirationDate = LocalDate.now().plusDays(selectedDays.toLong())
                        onAddItem(foodName, expirationDate)
                    }
                },
                enabled = foodName.isNotBlank()
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")
    return date.format(formatter)
}