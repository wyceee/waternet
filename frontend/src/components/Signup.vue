<template>
  <div class="auth-container">
    <h1 class="auth-title">Signup</h1>
    <form @submit.prevent="handleSignup">
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
  </div>
</template>

<script setup>
import {ref, inject} from 'vue';
import {useRouter} from 'vue-router';

const name = ref('');
const email = ref('');
const password = ref('');
const confirmPassword = ref('');
const router = useRouter();

// Inject SessionService
const sessionService = inject('sessionService');

async function handleSignup() {
  if (password.value !== confirmPassword.value) {
    alert('Passwords do not match');
    return;
  }

  try {
    const hashedPassword = await hashPassword(password.value); // Example hashing function
    const user = await sessionService.register(name.value, email.value, hashedPassword);
    if (user) {
      alert('Signup successful!');
      router.push('/login');
    }
  } catch (error) {
    alert('An error occurred during signup: ' + error.message);
  }
}

// Example hashing function (replace with actual implementation)
async function hashPassword(password) {
  return password; // Replace with actual hashing logic
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
  width: 100%;
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
</style>