package com.example.myplants.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_table")
data class Plant (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

//    MAIN

    // Название растения
    @ColumnInfo(name = "name")
    var name: String,

    // Вид растения
    @ColumnInfo(name = "species")
    var species: String,

    // Избранное
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    // Список желаний
    @ColumnInfo(name = "is_wishlist")
    val isWishlist: Boolean = false,

    // Подвид растения
    @ColumnInfo(name = "sub_species")
    var subSpecies: String? = null,

//    Additional

    // Освещение
    @ColumnInfo(name = "lighting")
    var lighting: String? = null,

    // Цветение
    @ColumnInfo(name = "bloom")
    var bloom: String? = null,

    // Состав почвы
    @ColumnInfo(name = "soil_composition")
    var soilComposition: String? = null,

    // Пересадка
    @ColumnInfo(name = "transfer")
    var transfer: String? = null,

    // Температура
    @ColumnInfo(name = "temperature")
    var temperature: String? = null,

    // Влажность воздуха
    @ColumnInfo(name = "air_humidity")
    var airHumidity: String? = null,

    // Период покоя
    @ColumnInfo(name = "rest_period")
    var restPeriod: String? = null,

    // Размножение
    @ColumnInfo(name = "reproduction")
    var reproduction: String? = null,

    // Вредители
    @ColumnInfo(name = "pests")
    var pests: String? = null,

    // Болезни
    @ColumnInfo(name = "diseases")
    var diseases: String? = null,

    // Возраст
    @ColumnInfo(name = "first_landing")
    var firstLanding: String? = null,

    // Требования к солнечному свету
    @ColumnInfo(name = "sunlight_requirement")
    val sunlightRequirement: String? = null,

    // Ядовитость
    @ColumnInfo(name = "is_toxic")
    val isToxic: Boolean? = null,

    // О растении
    @ColumnInfo(name = "about_the_plant")
    var aboutThePlant: String? = null,

//    PAIR PARAMETERS

    // Полив
    @ColumnInfo(name = "watering")
    val watering: String? = null,

    // Частота полива в днях
    @ColumnInfo(name = "watering_frequency")
    var wateringFrequency: Int? = null,

    // Дата последнего полива (в формате "yyyy-MM-dd")
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: String? = null,


    // Подкормки (удобрения)
    @ColumnInfo(name = "fertilizer")
    var fertilizer: String? = null,

    // Частота удобрения в месяцах
    @ColumnInfo(name = "fertilization_frequency")
    val fertilizationFrequency: Int? = null,
)