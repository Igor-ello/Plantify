package com.obsessed.main

@HiltViewModel
class MainViewModel @Inject constructor(
    private val facade: MainFacadeInterface
) : ViewModel() {

    private val _genusMap = MutableLiveData<Map<String, Genus>>(emptyMap())
    val genusMap: LiveData<Map<String, Genus>> = _genusMap

    val plants: LiveData<List<PlantWithPhotos>> = facade.getAllPlantsWithPhotos()

    init {
        viewModelScope.launch {
            PlantDataInitializer.initialize(facade)
        }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            facade.updatePlant(plant)
        }
    }

    // Favourite
    val favorites: LiveData<List<PlantWithPhotos>> = facade.getFavorites()

    fun toggleFavorite(plant: Plant) {
        viewModelScope.launch {
            facade.setFavorite(plant.id, !plant.state.isFavorite)
        }
    }

    // Wishlist
    val wishlist: LiveData<List<PlantWithPhotos>> = facade.getWishlist()

    fun toggleWishlist(plant: Plant) {
        viewModelScope.launch {
            facade.setWishlist(plant.id, !plant.state.isWishlist)
        }
    }

    // Genus
    init {
        viewModelScope.launch {
            PlantDataInitializer.initialize(facade)
        }

        // Наблюдаем за растениями и когда они загружаются - загружаем роды
        viewModelScope.launch {
            plants.asFlow().collect { plantsList ->
                if (plantsList.isNotEmpty()) {
                    loadAllGenuses(plantsList)
                }
            }
        }
    }

    private suspend fun loadAllGenuses(plantsList: List<PlantWithPhotos>) {
        val genusNames = plantsList.map { it.plant.main.genus }.distinct()
        val newGenusMap = mutableMapOf<String, Genus>()

        genusNames.forEach { genusName ->
            var genus = facade.getGenusByName(genusName)

//            if (genus == null) {
//                val newGenus = Genus(
//                    id = 0L,
//                    main = MainInfo(genus = genusName, species = ""),
//                    care = CareInfo(),
//                    lifecycle = LifecycleInfo(),
//                    health = HealthInfo(),
//                    state = StateInfo()
//                )
//                val newId = facade.insertGenus(newGenus)
//                genus = facade.getGenusById(newId)
//            }

            if (genus != null) {
                newGenusMap[genusName] = genus
            }
        }

        _genusMap.value = newGenusMap
    }
}