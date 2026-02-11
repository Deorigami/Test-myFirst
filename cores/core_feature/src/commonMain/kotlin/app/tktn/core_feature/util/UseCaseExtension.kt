package app.tktn.core_feature.util

import androidx.lifecycle.viewModelScope
import app.tktn.core_feature.base.BaseScreenModel
import app.tktn.core_service.base.BaseUseCase
import app.tktn.core_service.model.StatefulResult
import kotlinx.coroutines.launch

context(vm: BaseScreenModel<*, *>)
fun <P, R> BaseUseCase<P, R>.executeInViewModel(
	param: P,
	onResult: (StatefulResult<R>) -> Unit = {}
) {
	vm.viewModelScope.launch {
		execute(param, onResult)
	}
}