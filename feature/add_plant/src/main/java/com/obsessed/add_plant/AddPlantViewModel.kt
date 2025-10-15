package com.obsessed.add_plant

@HiltViewModel
class AddPlantViewModel @Inject constructor(
    private val plantRepository: PlantRepositoryInterface,
    private val photoRepository: PhotoRepositoryInterface
) : ViewModel() {

    // LiveData для нового растения и его фотографий
    private val _newPlant = MutableLiveData(Plant(main = MainInfo(genus = "", species = "")))
    val newPlant: LiveData<Plant> get() = _newPlant

    private val _newPlantPhotos = MutableLiveData<List<PlantPhoto>>(emptyList())
    val newPlantPhotos: LiveData<List<PlantPhoto>> get() = _newPlantPhotos

    // Обновление текущего растения
    fun updateNewPlant(plant: Plant) {
        _newPlant.value = plant
    }

    // Обновление фотографий
    fun updateNewPlantPhotos(photos: List<PlantPhoto>) {
        _newPlantPhotos.value = photos
    }

    // Сброс всех полей нового растения
    fun clearNewPlant() {
        _newPlant.value = Plant(main = MainInfo(genus = "", species = ""))
        _newPlantPhotos.value = emptyList()
    }

    // Сохранение нового растения и его фото в репозиторий
    fun saveNewPlant(onSaved: () -> Unit) {
        val plant = _newPlant.value ?: return
        val photos = _newPlantPhotos.value ?: emptyList()
        viewModelScope.launch {
            val plantId = plantRepository.insertPlant(plant)
            photos.forEach { photoRepository.insertPhoto(it.copy(plantId = plantId)) }
            clearNewPlant()
            onSaved()
        }
    }
}