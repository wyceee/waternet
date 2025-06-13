import wn.backend.models.Measure
import java.time.Instant

data class MeasureDTO(
    val userId: Long,
    val description: String,
    val photoUrl: String,
    val location: String,
    val timestamp: Instant,
    val status: Measure.Status,
    val measureType: String,
    val txHash: String,
    val area: Double?,
    val capacity: Double?
)
