import { createRouter, createWebHistory } from 'vue-router';
import Dashboard from '../components/Dashboard.vue';
import RegisterMeasure from '../components/RegisterMeasure.vue';
import SupervisorPanel from '../components/SupervisorPanel.vue';
import Wallet from '../components/Wallet.vue';

const routes = [
    { path: '/', name: 'Dashboard', component: Dashboard },
    { path: '/register', name: 'RegisterMeasure', component: RegisterMeasure },
    { path: '/supervisor', name: 'SupervisorPanel', component: SupervisorPanel },
    { path: '/wallet', name: 'Wallet', component: Wallet },
];

export const router = createRouter({
    history: createWebHistory(),
    routes
});