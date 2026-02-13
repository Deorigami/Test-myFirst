package app.tktn.core_service.model


sealed class StatefulResult<out T> {
    val succeeded
        get() = this is Success && data != null


    data class Success<out T>(val data: T, val message: String, val status: Boolean, val code: Int = 200) : StatefulResult<T>()
    data class Failed(val error: CoreError) : StatefulResult<Nothing>()
    data object Loading : StatefulResult<Nothing>()

    companion object {
        fun <T> StatefulResult<T>.onSuccess(block: (T) -> Unit) {
            if (this is Success) block.invoke(this.data)
        }
        fun <T> StatefulResult<T>.onError(block: (CoreError) -> Unit) {
            if (this is Failed) block.invoke(error)
        }
    }
}
