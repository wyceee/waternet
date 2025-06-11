package wn.backend.dto

data class RegisterResponseDTO(
    val user: UserDTO,
    val privateKey: String
)