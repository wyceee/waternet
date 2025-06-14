package wn.backend.sepolia

import org.springframework.stereotype.Service
import org.web3j.abi.EventEncoder
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.Log
import org.web3j.tx.Contract
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger
import java.time.Instant

@Service
class ContractService(
    private val web3j: Web3j,
    private val adminCredentials: Credentials
) {
    private val contractAddress = "0x1E989Dc25884A3a0c301d41AF336E1eFb615073e"

    private fun loadContract(): HydRewards =
        HydRewards.load(contractAddress, web3j, adminCredentials, DefaultGasProvider())

    fun getOnChainTransfersToUser(toAddress: String): List<OnChainTx> {
        val contract = loadContract()
        val transferEventHash = EventEncoder.encode(HydRewards.TRANSFER_EVENT)

        val filter = EthFilter(
            DefaultBlockParameterName.EARLIEST,
            DefaultBlockParameterName.LATEST,
            contract.contractAddress
        ).addSingleTopic(transferEventHash)
            .addNullTopic()
            .addOptionalTopics("0x" + toAddress.lowercase().removePrefix("0x").padStart(64, '0'))  // to

        val logs = web3j.ethGetLogs(filter).send().logs

        return logs.mapNotNull { logResult ->
            val log = logResult as? Log ?: return@mapNotNull null
            val eventValues = Contract.staticExtractEventParameters(HydRewards.TRANSFER_EVENT, log)
            if (eventValues != null) {
                val from = eventValues.indexedValues[0].value as String
                val to = eventValues.indexedValues[1].value as String
                val value = eventValues.nonIndexedValues[0].value as BigInteger
                val txHash = log.transactionHash

                val blockResponse = web3j.ethGetBlockByHash(log.blockHash, false).send()
                val block = blockResponse.block ?: return@mapNotNull null
                val timestamp = Instant.ofEpochSecond(block.timestamp.longValueExact())

                OnChainTx(
                    tx = txHash,
                    amount = value,
                    from = from,
                    to = to,
                    timestamp = timestamp
                )
            } else null
        }
    }

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