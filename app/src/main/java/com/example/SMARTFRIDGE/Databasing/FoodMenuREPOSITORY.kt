// storin data and such
package com.example.SMARTFRIDGE.Databasing
import kotlinx.coroutines.flow.Flow

//class, dependency and dependency inject (the DAO)
class FoodMenuREPOSITORY (private val foodMenuDAO: FoodMenuDAO) {
// coco multitask ,,, suspend and resume , helps w blockage
    suspend fun addFood(foodMenu: FoodMenu) {
        foodMenuDAO.addFood(foodMenu)
    }
    suspend fun updateFood(foodMenu: FoodMenu) {
        foodMenuDAO.updateFood(foodMenu)

    }suspend fun deleteFood(foodMenu: FoodMenu) {
        foodMenuDAO.deleteFood(foodMenu)
    }
/////////////////////////////////////////////////////////////////

    // flow > list bc automattically updates UI

    fun getAllFoods(): Flow<List<FoodMenu>> {
        return foodMenuDAO.getAllFoods()
    }
    fun getAllFoodsByName(): Flow<List<FoodMenu>> {
        return foodMenuDAO.getAllFoodsByName()
    }

    fun getAllFoodsByExpirationDate(): Flow<List<FoodMenu>> {
        return foodMenuDAO.getAllFoodsByExpirationDate()
    }

    fun getFoodCount(): Flow<Int> {
        return foodMenuDAO.getFoodCount()
    }
}