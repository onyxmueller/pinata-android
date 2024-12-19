package net.onyxmueller.pinataandroiddemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import net.onyxmueller.pinataandroiddemo.data.DemoFile

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    var isLoading: Boolean = false
    val toastLiveData = MutableLiveData<String>()

    private val demoFilesFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val demoFilesListFlow =
        demoFilesFetchingIndex.flatMapLatest {
            mainRepository.fetchDemoFiles(
                onStart = { isLoading = true },
                onComplete = { isLoading = false },
                onError = { toastLiveData.postValue(it) },
            )
        }

    val demoFilesListLiveData = MutableLiveData<List<DemoFile>>()

    init {
        viewModelScope.launch {
            demoFilesListFlow.collect { demoFiles ->
                demoFilesListLiveData.value = demoFiles
            }
        }
    }
}
