package com.example.shacklehotelbuddy

import com.example.shacklehotelbuddy.data.Resource
import com.example.shacklehotelbuddy.data.database.dao.RecentSearchDao
import com.example.shacklehotelbuddy.data.mapper.PropertyMapper
import com.example.shacklehotelbuddy.data.mapper.RecentSearchMapper
import com.example.shacklehotelbuddy.data.network.ShackleService
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import okhttp3.RequestBody.Companion.toRequestBody
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

    suspend fun performSearch(
        checkInDate: DateData,
        checkOutDate: DateData,
        adults: Int,
        children: Int,
    ): Resource<List<Property>> {
        val body =
            buildPostJson(checkInDate, checkOutDate, adults, children).toString().toRequestBody()

        val response = shackleService.postSearch(
            key = BuildConfig.API_KEY,
            postBody = body
        )

        return if (response.isSuccessful && response.body() != null) {
            Resource.success(
                response.body()!!.data.propertySearchDto.properties.map {
                    propertyMapper.mapDtoToModel(it)
                }
            )
        } else {
            Resource.error(Exception("Error with code ${response.code()}"))
        }
    }

    private fun buildPostJson(
        checkInDate: DateData,
        checkOutDate: DateData,
        adults: Int,
        children: Int,
    ): JsonObject {
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
            addProperty("day", checkInDate.day)
            addProperty("month", checkInDate.month)
            addProperty("year", checkInDate.year)
        }

        val checkOutObj = JsonObject()
        with(checkOutObj) {
            addProperty("day", checkOutDate.day)
            addProperty("month", checkOutDate.month)
            addProperty("year", checkOutDate.year)
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