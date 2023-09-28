package com.example.shacklehotelbuddy.data.mapper

import com.example.shacklehotelbuddy.search.model.Property
import com.example.shacklehotelbuddy.data.network.dto.PropertyDto
import javax.inject.Inject

class PropertyMapper @Inject constructor() {

    fun mapDtoToModel(dto: PropertyDto): Property {
        return Property(
            id = dto.id,
            name = dto.name,
            imageUrl = dto.propertyImage.image.url,
            neighborhood = dto.neighborhood.name,
            ratings = String.format("%.1f", dto.reviewsDto.score),
            pricePerNight = dto.price.lead.formatted
        )
    }
}