# 💧 Water Infiltration Web3 App

This project is a Web3 application that encourages households to implement rainwater retention measures (such as green roofs, rain barrels, etc.). When a measure is approved, the household receives token rewards via smart contracts on the Ethereum testnet blockchain. [Flow Architecture](FLOW_ARCHITECTURE.md), [ERD](ERD.md).

---

## 🚀 Features

✅ **Dashboard**  
Users can see an overview of their water retention measures, their status and token balance.

✅ **Register measures**  
Users can upload photos and explanations of their water retention measures.

✅ **Supervisor approval**  
A designated supervisor verifies and approves/rejects submitted measures. After approval, the user receives tokens as a reward and both ends will see the tx ID links.

✅ **Token rewards**  
Once approved, users receive tokens via smart contracts on the Ethereum testnet blockchain.

✅ **Wallet overview**  
Users can view their token balance and the recent transactions to their wallet with tx ID links.

✅ **Wallet creation**  
After registration, a wallet is automatically created for the user and prompted with the wallet private key (they only need this if they lose their account credentials), allowing them to receive tokens.

✅ **Fully dynamic authentication**  
Every endpoint is protected by authentication with roles and JWTokens, and users can register, log in, and log out. Only the supervisor can approve measures, secured contract in the backend.

✅ **Fully dynamic authentication**  
No web3 wallet or tx signing is required for users and supervisors! Everything is handled in the backend.

---

## 🛠️ Tech Stack

- **Frontend:** [Vue.js](https://vuejs.org/)
- **Backend:** [Spring Boot](https://spring.io/projects/spring-boot) with **Kotlin** (Java 21) & Gradle KOTLIN DSL
- **Database:** H2 SQL in memory database
- **Blockchain:**  
    - The backend uses a web3j to interact with smart contracts on the Ethereum testnet.
    - Using the Metamask Infura RPC provider for blockchain interactions.
    - The ContractService is integrated with backend API endpoints to handle blockchain operations.
    - All blockchain operations are handled securely in the backend, so users do not need to interact with the blockchain directly.
- **Authentication:** JWT (JSON Web Tokens) and user roles
- **Smart Contract:**  
    - The smart contract is written in Solidity and deployed on the Ethereum testnet.
    - The contract allows minting tokens, transferring tokens, and querying balances.
    - [Blockchain explorer](https://sepolia.etherscan.io/token/0x1e989dc25884a3a0c301d41af336e1efb615073e)
---

## ⚙️ Running the project
> **Note:** You will need blockchain environment keys (`WEB3J_RPC_URL`, `ADMIN_PRIVATE_KEY`) to run the backend. Ask your team members for these keys.


### 🌐 Frontend
Runs at [http://localhost:5173](http://localhost:5173)

UNIX or (windows execute 1 by 1)
```bash
cd frontend
npm install
npm run dev
```

### 🖥️ Backend
Runs at [http://localhost:8085](http://localhost:8085)

UNIX
```bash
cd backend
./gradlew build
./mvnw spring-boot:run
```

WINDOWS (execute 1 by 1)
```bash
cd backend
.\gradlew build
.\gradlew bootRun
```

### 🗄️ H2 Database Access
The backend uses an in-memory H2 database, which is automatically created and populated with initial data on startup. You can access the H2 console at [http://localhost:8085/h2-console](http://localhost:8085/h2-console). The ONLY credentials you need to alter after visiting the console is JDBC URL: and you can find this almost at the bottom of the backend console under 
```2025-06-14T20:22:05.051+02:00  INFO 13696 --- [backend] [  restartedMain] o.s.b.a.h2.H2ConsoleAutoConfiguration    : H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:a2d7ebf9-0e5b-4f6e-acb7-0441b5a68ea0'``` in this case the JDBC URL is `jdbc:h2:mem:a2d7ebf9-0e5b-4f6e-acb7-0441b5a68ea0`. This changes every time you restart the backend, so you will need to update it accordingly.


---

## 🔗 Flow Architecture & ERD
- **[Flow Architecture](FLOW_ARCHITECTURE.md)**: Detailed flow from frontend to backend, including routing, services, authentication, and controllers.
- **[ERD](ERD.md)**: Entity-Relationship Diagram showing the database structure and relationships between entities.

---