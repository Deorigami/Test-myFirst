package app.tktn.core_service.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ResultDto<DTO>(
    val data: DTO? = null,
	@SerialName("response_desc")
    val message: String? = "",
    val status: Boolean? = null,
) {
    companion object {
        fun <ENTITY, DTO> ResultDto<DTO>.toDomainResult(block: (DTO) -> ENTITY): DomainResult<ENTITY> {
            return DomainResult(
                data?.let { block.invoke(it) },
                message,
                status = status ?: false
            )
        }
    }
}