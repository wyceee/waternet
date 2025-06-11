package wn.backend.sepolia

import org.springframework.stereotype.Service
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger

@Service
class ContractService(
    private val web3j: Web3j,
    private val adminCredentials: Credentials
) {
    private val contractAddress = "0x1E989Dc25884A3a0c301d41AF336E1eFb615073e"

    private fun loadContract(): HydRewards =
        HydRewards.load(contractAddress, web3j, adminCredentials, DefaultGasProvider())

    fun transfer(to: String, amount: BigInteger) =
        loadContract().transfer(to, amount).send()

    fun mint(user: String, amount: BigInteger) =
        loadContract().mint(user, amount).send()

    fun balanceOf(owner: String): BigInteger =
        loadContract().balanceOf(owner).send()

    fun totalSupply(): BigInteger =
        loadContract().totalSupply().send()

    fun name(): String =
        loadContract().name().send()

    fun symbol(): String =
        loadContract().symbol().send()

    fun decimals(): BigInteger =
        loadContract().decimals().send()

    fun owner(): String =
        loadContract().owner().send()
}