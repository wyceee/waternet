package wn.backend.sepolia

import java.math.BigInteger
import java.time.Instant

data class OnChainTx(
    val tx: String,
    val amount: BigInteger,
    val from: String,
    val to: String,
    val timestamp: Instant
)
