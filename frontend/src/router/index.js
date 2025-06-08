import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/Home.vue';
import Dashboard from '../components/Dashboard.vue';
import RegisterMeasure from '../components/RegisterMeasure.vue';
import SupervisorPanel from '../components/SupervisorPanel.vue';
import Wallet from '../components/Wallet.vue';
import Login from '../components/Login.vue';
import Signup from '../components/Signup.vue';

const routes = [
    { path: '/', name: 'Home', component: Home },
    { path: '/dashboard', name: 'Dashboard', component: Dashboard },
    { path: '/register', name: 'RegisterMeasure', component: RegisterMeasure },
    { path: '/supervisor', name: 'SupervisorPanel', component: SupervisorPanel },
    { path: '/wallet', name: 'Wallet', component: Wallet },
    { path: '/login', name: 'Login', component: Login },
    { path: '/signup', name: 'Signup', component: Signup },
];

export const router = createRouter({
    history: createWebHistory(),
    routes
});