package com.example.mysportproject.ui.fragments.nutrition.repositories

import androidx.lifecycle.MutableLiveData
import com.example.mysportproject.ui.fragments.nutrition.dao.DayDao
import com.example.mysportproject.ui.fragments.nutrition.interfaces.BasicOperationCallback
import com.example.mysportproject.ui.fragments.nutrition.models.ConverterToProduct
import com.example.mysportproject.ui.fragments.nutrition.models.FoodProduct
import com.example.mysportproject.ui.fragments.nutrition.models.Product
import com.example.mysportproject.ui.fragments.nutrition.models.day.Day
import com.example.mysportproject.ui.fragments.nutrition.models.day.DayDate
import com.example.mysportproject.ui.fragments.nutrition.models.day.Meal
import com.example.mysportproject.ui.fragments.nutrition.models.day.MealType
import com.example.mysportproject.ui.fragments.nutrition.models.localdb.IProduct
import com.example.mysportproject.ui.fragments.nutrition.util.DAY_FRAGMENTS_START_POSITION
import com.example.mysportproject.ui.fragments.nutrition.util.getDayDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


class DayRepository @Inject internal constructor(
    private val dayDao: DayDao
) {

    val currentPosition = MutableLiveData(DAY_FRAGMENTS_START_POSITION)

    val currentDay: MutableLiveData<Day> = MutableLiveData()

    //private var userId: String? = null
        //get() = FirebaseAuth.getInstance().uid

    /**
     * get date according to current position
     */
    fun getDate(position: Int): Date {
        val diff = position - DAY_FRAGMENTS_START_POSITION
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, diff)
        return calendar.time
    }

    fun getInitialDayDate(): DayDate {
        val date = Calendar.getInstance().time
        return date.getDayDate()
    }

//    fun checkFirebaseVersion(day: Day) = CoroutineScope(Dispatchers.IO).launch {
//        if (currentPosition.value != null) {
//            // check if same days are different in firebase and in local storage
//            searchDayInFirebase(day)?.let {
//                if (!day.equals(it)) {
//                    // if different, replace local with firebase version
//                    dayDao.deleteDay(day.dayId)
//                    insertDay(it)
//                    // update livedata to new value
//                    currentDay.postValue(it)
//                }
//            }
//        }
//    }

    suspend fun updateDay(pos: Int?) = coroutineScope {
        async(Dispatchers.IO) {

            val position = pos ?: currentPosition.value ?: DAY_FRAGMENTS_START_POSITION
            var receivedDay = getPreviousOrNextDay(position)

            currentPosition.postValue(position)
            currentDay.postValue(receivedDay)
        }
    }

    suspend fun getPreviousOrNextDay(
        position: Int
    ): Day {
        val date = getDate(position).getDayDate()
        // if there is next or previous day in DB, return it
        // else generate new day with needed day
        return dayDao.searchByStringDate(date.month, date.day)
            ?: generateDefaultDayWithSpecificDay(date)
    }

    private fun generateMeals(): List<Meal> {
        val mutableList = mutableListOf<Meal>()
        for (value in MealType.values())
            mutableList.add(Meal(products = arrayListOf(), mealType = value))
        return mutableList
    }

    private fun generateDefaultDayWithSpecificDay(date: DayDate): Day {
        val meals = generateMeals()
        val day = Day(
            meals = meals,
            dayId = 0,
            date = date
        )
        // save generated day
        insertDay(day)
        return day
    }


    fun saveSearchProductToDay(
        dateString: DayDate,
        mealType: Int,
        offlineProduct: FoodProduct?,
        grams: Float,
        productLocal: IProduct?
    ) =
        try {
            val converter = ConverterToProduct()
            val product = productLocal?.let {
                converter.convertLocal(it, grams)
            } ?: offlineProduct?.let { converter.convertFoodProduct(it, grams) }
            product?.let { addProductToDay(dateString, mealType, product) }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    /**
     * add product to the meals of given day
     */
    private fun addProductToDay(
        date: DayDate,
        mealType: Int,
        product: Product,
        addCallback: BasicOperationCallback? = null
    ) {
        // receive day by date
        var day = dayDao.searchByStringDate(date.month, date.day)

        // if day not exists, generate it
        if (day == null) {
            day = generateDefaultDayWithSpecificDay(date)
        }
        // add product to the meal of the day
        day.meals[mealType].products.add(product)
        insertDay(day)
        //saveDayToFirebase(day, addCallback)
    }


//    private fun saveDayToFirebase(
//        day: Day,
//        basicOperationCallback: BasicOperationCallback? = null
//    ) {
//        userId ?: return
//        val fs = Firebase.firestore
//
//        try {
//            // preapre data to store in fs
//            val data = DayToHashMapConverter().convertToHashMap(day)
//
//            fs.document(
//                "/users/$userId/days/${day.date.year}" +
//                        "/${day.date.month}/${day.date.day}"
//            ).set(data)
//                .addOnCompleteListener {
//                    if (!it.isSuccessful) {
//                        basicOperationCallback?.onFailure()
//                    }
//                }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }


    fun deleteAllDays() {
        dayDao.deleteAllDays()
    }


    fun getDayByDate(date: DayDate): Day? {
        return dayDao.searchByStringDate(date.month, date.day)
    }

    fun deleteProductFromDay(
        date: DayDate, product: Product, deleteCallback: BasicOperationCallback
    ): Boolean {
        val day = getDayByDate(date) ?: return false
        // remove product from day
        val isDeleted = day.removeProduct(product)
        if (isDeleted) {
            insertDay(day)
            // update firestore's day
            //saveDayToFirebase(day, deleteCallback)
        }
        return isDeleted
    }

    fun deleteDay(id: Int) {
        dayDao.deleteDay(id)
    }

    fun searchById(id: Int): Day? {
        return dayDao.searchById(id)
    }

    fun getSize(): Int {
        return dayDao.getSize()
    }

    fun getAllDays(): List<Day> {
        return dayDao.getAllDays()
    }

    fun insertDay(day: Day) {
        dayDao.insertDay(day)
    }

//    suspend fun searchDayInFirebase(receivedDay: Day): Day? = coroutineScope {
//        try {
//            userId ?: null
//            Firebase.firestore.document("/users/$userId/days/${receivedDay.date.year}/${receivedDay.date.month}/${receivedDay.date.day}")
//                .get()
//                .await().toObject(Day::class.java)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }


    fun resetDate() {
        currentPosition.value?.let {
            currentPosition.value = DAY_FRAGMENTS_START_POSITION
        }
    }

    fun updateWaterNum(newWaterNum: Int) {
        currentDay.value?.let {
            if (newWaterNum in 0..7) {
                CoroutineScope(Dispatchers.IO).launch {
                    dayDao.updateWaterGlasses(newWaterNum, it.dayId)
                    //updateWaterNumFirebase(newWaterNum, it)
                    currentDay.postValue(it.apply { waterNum = newWaterNum })
                }
            }
        }
    }

//    private fun updateWaterNumFirebase(waterNum: Int, day: Day) {
//        userId ?: return
//        Firebase.firestore.document("/users/$userId/days/${day.date.year}/${day.date.month}/${day.date.day}")
//            .update("waterNum", waterNum)
//    }

}