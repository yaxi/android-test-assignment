package com.example.shacklehotelbuddy.search.data

import com.example.shacklehotelbuddy.BuildConfig
import com.example.shacklehotelbuddy.data.Resource
import com.example.shacklehotelbuddy.data.database.dao.RecentSearchDao
import com.example.shacklehotelbuddy.data.database.entity.RecentSearchEntity
import com.example.shacklehotelbuddy.data.mapper.PropertyMapper
import com.example.shacklehotelbuddy.data.mapper.RecentSearchMapper
import com.example.shacklehotelbuddy.data.network.ShackleService
import com.example.shacklehotelbuddy.search.model.Property
import com.example.shacklehotelbuddy.search.model.RecentSearch
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(
    private val recentSearchDao: RecentSearchDao,
    private val recentSearchMapper: RecentSearchMapper,
    private val propertyMapper: PropertyMapper,
    private val shackleService: ShackleService,
) {

    suspend fun getRecentSearches(): List<RecentSearch> {
        return recentSearchDao.getRecentSearches().map {
            recentSearchMapper.mapEntityToModel(it)
        }
    }

    fun observeRecentSearches(): Flow<List<RecentSearch>> {
        return recentSearchDao.observeRecentSearches().map { list ->
            list.map { recentSearchMapper.mapEntityToModel(it) }
        }
    }

    suspend fun saveLastSearch(
        checkInDate: String,
        checkOutDate: String,
        adults: Int,
        children: Int,
    ) {
        val entity = RecentSearchEntity(
            checkInDate = checkInDate,
            checkOutDate = checkOutDate,
            adults = adults,
            children = children
        )
        recentSearchDao.insert(entity)
    }

    fun performSearch(
        checkInDateInMillis: Long,
        checkOutDateInMillis: Long,
        adults: Int,
        children: Int,
    ): Flow<Resource<List<Property>>> {
        return flow {
            emit(Resource.loading())
            val body =
                buildPostJson(
                    checkInDateInMillis = checkInDateInMillis,
                    checkOutDateInMillis = checkOutDateInMillis,
                    adults = adults,
                    children = children
                ).toString().toRequestBody()

            val response = shackleService.postSearch(
                key = BuildConfig.API_KEY,
                postBody = body
            )

            if (response.isSuccessful && response.body() != null) {
                emit(Resource.success(
                    response.body()!!.data.propertySearchDto.properties.map {
                        propertyMapper.mapDtoToModel(it)
                    }
                ))
            } else {
                emit(Resource.error(Exception("Error with code ${response.code()}")))
            }
        }
    }

    private fun buildPostJson(
        checkInDateInMillis: Long,
        checkOutDateInMillis: Long,
        adults: Int,
        children: Int,
    ): JsonObject {

        val checkInDate = Calendar.getInstance().also { it.timeInMillis = checkInDateInMillis }
        val checkOutDate = Calendar.getInstance().also { it.timeInMillis = checkOutDateInMillis }

        val jsonObject = JsonObject()
        with(jsonObject) {
            addProperty("currency", "USD") //TODO: based on device locale currency
            addProperty("locale", "en_US") //TODO: based on device locale currency

            val destinationObj = JsonObject()
            destinationObj.addProperty(
                "regionId",
                "6054439"
            ) //FIXME: hard-coded region, should be based on chosen location

            add("destination", destinationObj)
        }

        val checkInObj = JsonObject()
        with(checkInObj) {
            addProperty("day", checkInDate[Calendar.DAY_OF_MONTH])
            addProperty("month", checkInDate[Calendar.MONTH])
            addProperty("year", checkInDate[Calendar.DAY_OF_MONTH])
        }

        val checkOutObj = JsonObject()
        with(checkOutObj) {
            addProperty("day", checkOutDate[Calendar.DAY_OF_MONTH])
            addProperty("month", checkOutDate[Calendar.MONTH])
            addProperty("year", checkOutDate[Calendar.DAY_OF_MONTH])
        }

        val roomsArray = JsonArray()
        val roomObj = JsonObject()
        roomObj.addProperty("adults", adults)
        val childrenArray = JsonArray()
        repeat(children) {
            childrenArray.add(JsonObject())
        }
        roomObj.add("children", childrenArray)
        roomsArray.add(roomObj)

        with(jsonObject) {
            repeat(children) {
                childrenArray.add(JsonObject())
            }
            roomObj.add("children", childrenArray)
            roomsArray.add(roomObj)

            add("checkInDate", checkInObj)
            add("checkOutDate", checkOutObj)
            add("rooms", roomsArray)
        }
        return jsonObject
    }

}