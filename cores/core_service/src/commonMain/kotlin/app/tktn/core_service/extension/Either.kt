package app.tktn.core_service.extension

import app.tktn.core_service.model.CoreError
import app.tktn.core_service.model.DomainResult
import app.tktn.core_service.model.StatefulResponse
import app.tktn.core_service.model.StatefulResult
import co.touchlab.kermit.Logger

suspend fun <R> either(block: suspend () -> DomainResult<R>): StatefulResponse<R> = runCatching {
    StatefulResponse.Success(block())
}.getOrElse {
    StatefulResponse.Error(it)
}

fun <T> StatefulResponse<T>.toResult(default: T): StatefulResult<T> {
	Logger.d("StatefulResponse") { this.toString() }
    return when (this) {
		is StatefulResponse.Success if this.data.data != null && this.data.status -> StatefulResult.Success(
			data = this.data.data,
			message = this.data.message ?: "Success",
			status = data.status,
			code = this.data.code
		)

		is StatefulResponse.Success if !this.data.status -> {
			StatefulResult.Failed(
				error = CoreError.Network(
                    code = this.data.code,
                    message = this.data.message ?: "Unknown error"
                )
			)
		}

		is StatefulResponse.Success if this.data.data == null && this.data.status -> StatefulResult.Success(
			data = default,
			message = this.data.message ?: "Success",
			status = data.status,
			code = this.data.code
		)

		is StatefulResponse.Success -> StatefulResult.Failed(
            CoreError.Unknown((this as? StatefulResponse.Success)?.data?.message ?: "No Data")
        )

		is StatefulResponse.Error -> StatefulResult.Failed(this.exception.toCoreError())
	}
}

fun Throwable.toCoreError(): CoreError {
    return when (this) {
        is io.ktor.client.plugins.ClientRequestException -> {
            if (this.response.status.value == 401) CoreError.Unauthorized
            else CoreError.Network(this.response.status.value, this.message ?: "Client Error")
        }
        is io.ktor.client.network.sockets.ConnectTimeoutException,
        is io.ktor.client.plugins.HttpRequestTimeoutException -> CoreError.Network(408, "Timeout")
        is Exception -> CoreError.Unknown(this.message ?: "Unknown Exception")
        else -> CoreError.Unknown(this.message ?: "Unknown Throwable")
    }
}