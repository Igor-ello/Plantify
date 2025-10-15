package com.obsessed.plant_detail

@HiltViewModel
class PlantDetailViewModel @Inject constructor(
    plantWithPhotosRepository: PlantWithPhotosRepositoryInterface,
    private val plantRepository: PlantRepositoryInterface,
    private val photoRepository: PhotoRepositoryInterface,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val plantId: Long = savedStateHandle.get<Long>("plantId") ?: 0L

    val plantWithPhotos: LiveData<PlantWithPhotos?> =
        plantWithPhotosRepository.getPlantWithPhotosById(plantId)

    private val _editedPlant = MutableLiveData<Plant>()
    val editedPlant: LiveData<Plant> get() = _editedPlant

    private val _editedPhotos = MutableLiveData<List<PlantPhoto>>(emptyList())
    val editedPhotos: LiveData<List<PlantPhoto>> get() = _editedPhotos

    init {
        // Сразу синхронизируем с LiveData от репозитория
        plantWithPhotos.observeForever { plantData ->
            plantData?.let {
                _editedPlant.value = it.plant
                _editedPhotos.value = it.photos
            }
        }
    }

    fun updateEditedPlant(plant: Plant) {
        _editedPlant.value = plant
    }

    fun updateEditedPhotos(photos: List<PlantPhoto>) {
        _editedPhotos.value = photos
    }

    fun saveChanges() {
        val plant = _editedPlant.value ?: return
        val photos = _editedPhotos.value ?: emptyList()
        viewModelScope.launch {
            plantRepository.updatePlant(plant)
            photos.forEach {
                if (it.id == 0L) photoRepository.insertPhoto(it.copy(plantId = plant.id))
                else photoRepository.updatePhoto(it)
            }
        }
    }

    fun resetChanges() {
        plantWithPhotos.value?.let {
            _editedPlant.value = it.plant
            _editedPhotos.value = it.photos
        }
    }

    fun deletePlant(onDeleted: () -> Unit) {
        val plant = _editedPlant.value ?: return
        viewModelScope.launch {
            photoRepository.deletePhotosByPlantId(plant.id)
            plantRepository.deletePlantById(plant.id)
            onDeleted()
        }
    }
}