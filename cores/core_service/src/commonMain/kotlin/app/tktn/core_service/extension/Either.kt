package app.tktn.core_service.extension

import app.tktn.core_service.model.DomainResult
import app.tktn.core_service.model.Error
import app.tktn.core_service.model.StatefulResponse
import app.tktn.core_service.model.StatefulResult

suspend fun <R> either(block: suspend () -> DomainResult<R>): StatefulResponse<R> = runCatching {
    StatefulResponse.Success(block())
}.getOrElse {
    StatefulResponse.Error(it)
}

fun <T> StatefulResponse<T>.toResult(default: T): StatefulResult<T> {
    return when {

		// this one is for MASI Test

		this is StatefulResponse.Success && this.data.data != null -> StatefulResult.Success(
			data = this.data.data,
			message = this.data.message ?: "Success",
			status = data.status,
			code = 200
		)

        this is StatefulResponse.Success && this.data.status && this.data.data != null -> {
            StatefulResult.Success(
                data = this.data.data,
                message = this.data.message ?: "Success",
                status = data.status,
                code = this.data.code
            )
        }

        this is StatefulResponse.Success && !this.data.status -> {
            StatefulResult.Failed(
                error = Error(
                    data.status,
                    this.data.message,
                    code = 500
                )
            )
        }

        this is StatefulResponse.Success && this.data.data == null && this.data.status -> StatefulResult.Success(
            data = default,
            message = this.data.message ?: "Success",
            status = data.status,
            code = this.data.code
        )

        this is StatefulResponse.Error -> StatefulResult.Failed(this.exception.toError())
        else -> {
            val message = (this as? StatefulResponse.Error)?.exception?.message
                ?: (this as? StatefulResponse.Success)?.data?.message ?: "No Data"
            StatefulResult.Failed(
                Error(
                    false,
                    message,
                    code = 500
                )
            )
        }
    }
}

fun Throwable.toError(): Error {
//    if (isDevEnvironment) {
//        printStackTrace()
//    }
//    Napier.e(tag = "USE_CASE", message = "THROW :${this.message}")
    return when (this) {
//        is ClientRequestException -> {
//            Error(
//                status = false,
//                message = "Error",
//                code = this.response.status.value
//            )
//        }

        is Exception -> Error(
            status = false,
            message = this.message,
            code = 500
        )

        else -> Error(
            false,
            this.message,
            code = 500
        )
    }
}