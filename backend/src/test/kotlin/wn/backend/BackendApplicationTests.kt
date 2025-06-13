package wn.backend

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import wn.backend.sepolia.ContractService

@SpringBootTest
class ContractServiceTest {

	@Autowired
	lateinit var contractService: ContractService

	@Test
	fun testNameFunction() {
		val contractName = contractService.name()
		assertNotNull(contractName)
		println("Contract name: $contractName")
	}
}
