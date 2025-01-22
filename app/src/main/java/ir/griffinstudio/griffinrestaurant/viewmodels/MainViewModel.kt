package ir.griffinstudio.griffinrestaurant.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.griffinstudio.griffinrestaurant.data.model.MainModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var model: MainModel

    private val _finishSplashScreen = MutableLiveData<Boolean>()
    val finishSplashString: LiveData<Boolean> = _finishSplashScreen
    fun startTimer() {
        viewModelScope.launch {
            flow<Boolean> {
                delay(model.delayTime)
                emit(true)
            }.collect {
                _finishSplashScreen.value = it
            }
        }
    }
}