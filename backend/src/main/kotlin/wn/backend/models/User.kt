package wn.backend.models

import jakarta.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @Column(nullable = false)
    var hashedPassword: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var role: Role = Role.USER,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    val measures: MutableList<Measure> = mutableListOf(),

    @Column(nullable = false, unique = true, length = 42)
    var wallet: String // VeChain wallet address
)

enum class Role {
    USER,
    SUPERVISOR,
    ADMIN
}