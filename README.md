# 💧 Water Infiltration Web3 App

This project is a **Web3 application** that encourages households to take measures to retain rainwater (like green roofs, rain barrels, etc.). When a measure is approved, the household receives token rewards on the **VeChainThor blockchain**.

---

## 🚀 Features

✅ **Register measures**  
Users can upload photos and explanations of their water retention measures.

✅ **Supervisor approval**  
A designated supervisor verifies and approves/rejects submitted measures.

✅ **Token rewards**  
Once approved, users receive tokens via smart contracts on the VeChainThor blockchain.

✅ **Wallet overview**  
Users can view their token balance.

✅ **(Optional)** Marketplace and leaderboard for extra motivation.

---

## 🛠️ Tech Stack

- **Frontend:** [Vue.js](https://vuejs.org/)
- **Backend:** [Spring Boot](https://spring.io/projects/spring-boot) with **Kotlin** (Java 21)
- **Database:** SQL (PostgreSQL or MySQL)
- **Blockchain:** [VeChainThor](https://vechain.org/) using smart contracts in Solidity
- **Node.js microservice:** For direct blockchain interactions (via `@vechain/connex`)


---

## ⚙️ Running the project

### 🌐 Frontend
Runs at [http://localhost:5173](http://localhost:5173)

```bash
cd frontend
npm install
npm run dev
```

### 🖥️ Backend
Runs at [http://localhost:8085](http://localhost:8085)

```bash
cd backend
./mvnw spring-boot:run
```
### ⚡️ Blockchain Node.js service (for smart contract interactions)
```
cd blockchain-node
npm install
npm run start
```

---
## 🔗 API Communication

- **Frontend ↔️ Backend:**
REST API for measure registration, uploads, supervisor approval, and data queries.

- **Backend ↔️ Blockchain Node.js service:**
REST API for blockchain transaction execution.
---