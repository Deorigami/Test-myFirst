package com.cmp.template.core_feature.connectivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jordond.connectivity.Connectivity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.koin.core.annotation.Single
@Single
class ConnectivityViewModel(
    connectivity: Connectivity
) : ViewModel() {
    val isOnline = connectivity.statusUpdates
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = Connectivity.Status.Connected(false)
        )
}
