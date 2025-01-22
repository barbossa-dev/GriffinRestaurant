package ir.griffinstudio.griffinrestaurant.data.model

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject


@ViewModelScoped
class MainModel @Inject constructor() {
    val delayTime = 3000L
}