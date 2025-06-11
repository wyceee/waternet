package wn.backend.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*
import java.time.Instant

@Entity
data class Measure(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("measures")
    val user: User,

    @Column(nullable = false)
    var description: String = "",

    @Column(nullable = false)
    var photoUrl: String = "",

    @Column(nullable = false)
    var location: String = "",

    @Column(nullable = false)
    val timestamp: Instant = Instant.now(),

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: Status = Status.PENDING,

    @Column(nullable = false)
    var measureType: String = "",

    var area: Double? = null,

    var capacity: Double? = null
) {
    enum class Status {
        PENDING,
        APPROVED,
        REJECTED
    }
}