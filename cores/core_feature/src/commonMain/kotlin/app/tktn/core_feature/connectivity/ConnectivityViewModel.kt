package app.tktn.core_feature.connectivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.Single

@Single
class ConnectivityViewModel(
    private val connectivity: Connectivity
) : ViewModel() {

    val isConnectedFlow: StateFlow<Boolean> = connectivity.statusUpdates
        .map { it is Connectivity.Status.Connected }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = true
        )

    init {
        connectivity.start()
    }

    override fun onCleared() {
        super.onCleared()
        connectivity.stop()
    }
}
