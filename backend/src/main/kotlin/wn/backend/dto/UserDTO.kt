package wn.backend.dto

data class UserDTO(
    val id: Long?,
    val name: String,
    val email: String,
    val wallet: String
)