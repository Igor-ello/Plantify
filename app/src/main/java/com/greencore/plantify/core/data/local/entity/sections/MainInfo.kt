package com.greencore.plantify.core.data.local.entity.sections

import androidx.room.ColumnInfo
import kotlinx.serialization.Serializable

@Serializable
data class MainInfo(
    // Вид
    var species: String,
    // Род
    var genus: String,
    // Семейство
    var family: String? = null,
    // Порядок
    var order: String? = null,
    // Класс
    var class_: String? = null,
    // Отдел
    var phylum: String? = null,
    // Царство
    var kingdom: String = "Растения",

    // Full name
    @ColumnInfo(name = "full_name")
    var fullName: String? = null
)
