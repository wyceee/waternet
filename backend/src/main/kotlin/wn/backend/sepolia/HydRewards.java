package wn.backend.sepolia;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

public class HydRewards extends Contract {
    public static final String BINARY = "0x1E989Dc25884A3a0c301d41AF336E1eFb615073e"; // Replace with contract bytecode if deploying

    public static final String FUNC_BALANCEOF = "balanceOf";
    public static final String FUNC_TRANSFER = "transfer";
    public static final String FUNC_MINT = "mint";
    public static final String FUNC_TOTALSUPPLY = "totalSupply";
    public static final String FUNC_OWNER = "owner";
    public static final String FUNC_NAME = "name";
    public static final String FUNC_SYMBOL = "symbol";
    public static final String FUNC_DECIMALS = "decimals";
    public static final String FUNC_BALANCES = "balances";

    protected HydRewards(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    protected HydRewards(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String _to, BigInteger _amount) {
        final Function function = new Function(
                FUNC_TRANSFER,
                Arrays.asList(new Address(_to), new Uint256(_amount)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> mint(String user, BigInteger amount) {
        final Function function = new Function(
                FUNC_MINT,
                Arrays.asList(new Address(user), new Uint256(amount)),
                Collections.emptyList()
        );
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(
                FUNC_BALANCEOF,
                Arrays.asList(new Address(_owner)),
                Arrays.asList(new TypeReference<Uint256>() {})
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(
                FUNC_TOTALSUPPLY,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint256>() {})
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(
                FUNC_NAME,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Utf8String>() {})
        );
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(
                FUNC_SYMBOL,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Utf8String>() {})
        );
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final Function function = new Function(
                FUNC_DECIMALS,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Uint8>() {})
        );
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> owner() {
        final Function function = new Function(
                FUNC_OWNER,
                Arrays.asList(),
                Arrays.asList(new TypeReference<Address>() {})
        );
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static HydRewards load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new HydRewards(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static HydRewards load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new HydRewards(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
