package app.tktn.core_service.model

sealed class CoreError {
    data class Network(val code: Int, val message: String) : CoreError()
    data class Database(val message: String) : CoreError()
    data class Unknown(val message: String) : CoreError()
    data object NoInternet : CoreError()
    data object Unauthorized : CoreError()
    
    val displayMessage: String
        get() = when (this) {
            is Network -> "Network error ($code): $message"
            is Database -> "Database error: $message"
            is Unknown -> "An unknown error occurred: $message"
            is NoInternet -> "No internet connection"
            is Unauthorized -> "Unauthorized access. Please check your API key."
        }
}
