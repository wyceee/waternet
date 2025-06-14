<template>
  <div class="auth-container">
    <h1 class="auth-title">Signup</h1>
    <form v-if="!privateKey" @submit.prevent="handleSignup">
      <div class="form-group">
        <label for="name">Name</label>
        <input type="text" id="name" v-model="name" required />
      </div>
      <div class="form-group">
        <label for="email">Email</label>
        <input type="email" id="email" v-model="email" required />
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" v-model="password" required />
      </div>
      <div class="form-group">
        <label for="confirmPassword">Confirm Password</label>
        <input type="password" id="confirmPassword" v-model="confirmPassword" required />
      </div>
      <button type="submit" class="auth-button">Signup</button>
    </form>

    <!-- Modal Popup -->
    <div v-if="privateKey" class="modal-overlay">
      <div class="modal">
        <h2>Signup Successful!</h2>
        <p>Your wallet private key:</p>
        <pre>{{ privateKey }}</pre>
        <p>Store this key securely. Do not share it with anyone. This key is needed in case of recovery.</p>
        <button @click="closeModal" class="modal-button">Close</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
import { useRouter } from 'vue-router';

const name = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const privateKey = ref(null);
const router = useRouter();
const sessionService = inject('sessionService');

async function handleSignup() {
  if (password.value !== confirmPassword.value) {
    return;
  }

  try {
    const hashedPassword = await hashPassword(password.value);
    const response = await sessionService.register(name.value, email.value, hashedPassword);

    const data = response.data || response;

    if (data.user && data.privateKey) {
      privateKey.value = data.privateKey;
    }
  } catch (error) {
  }
}

function closeModal() {
  privateKey.value = null;
  router.push('/login');
}

async function hashPassword(password) {
  return password;
}
</script>

<style>
.auth-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 2rem;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.auth-title {
  font-size: 1.5rem;
  margin-bottom: 1rem;
  text-align: center;
}

.form-group {
  margin-bottom: 1rem;
}

label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
}

input {
  width: 100%;
  padding: 0.5rem;
  border: 1px solid #d1d5db;
  border-radius: 4px;
}

.auth-button {
  width: 5rem;
  padding: 0.75rem;
  background-color: #10b981;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

.auth-button:hover {
  background-color: #059669;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal {
  background: white;
  padding: 2rem;
  border-radius: 8px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.modal pre {
  background: #f3f4f6;
  padding: 1rem;
  border-radius: 4px;
  overflow-x: auto;
}

.modal-button {
  margin-top: 1rem;
  padding: 0.75rem 1.5rem;
  background-color: #10b981;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

.modal-button:hover {
  background-color: #059669;
}
</style>