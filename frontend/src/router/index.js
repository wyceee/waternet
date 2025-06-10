import { createRouter, createWebHistory } from 'vue-router';
import Home from '../components/Home.vue';
import Dashboard from '../components/Dashboard.vue';
import RegisterMeasure from '../components/RegisterMeasure.vue';
import SupervisorPanel from '../components/SupervisorPanel.vue';
import Wallet from '../components/Wallet.vue';
import Login from '../components/Login.vue';
import Signup from '../components/Signup.vue';
import { reactive } from 'vue';
import { sessionService } from '../App.vue'; // Import the globally provided sessionService

const routes = [
    { path: '/', name: 'Home', component: Home },
    { path: '/dashboard', name: 'Dashboard', component: Dashboard },
    { path: '/register', name: 'RegisterMeasure', component: RegisterMeasure },
    { path: '/supervisor', name: 'SupervisorPanel', component: SupervisorPanel },
    { path: '/wallet', name: 'Wallet', component: Wallet },
    { path: '/login', name: 'Login', component: Login },
    { path: '/signup', name: 'Signup', component: Signup },
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

// Make sessionService reactive
const reactiveSessionService = reactive(sessionService);

router.beforeEach((to, from, next) => {
    const user = reactiveSessionService.user;
    const isAuthenticated = reactiveSessionService.isAuthenticated();

    if (!isAuthenticated && to.path !== '/' && to.path !== '/login' && to.path !== '/signup') {
        // Redirect unauthenticated users to the homepage
        return next('/');
    }

    if (isAuthenticated) {
        if (user.role === 'USER' && to.path === '/supervisor') {
            // Prevent USER role from accessing the supervisor panel
            return next('/');
        }

        if (user.role === 'SUPERVISOR' && to.path !== '/supervisor' && to.path !== '/') {
            // Prevent SUPERVISOR role from accessing other pages
            return next('/supervisor');
        }
    }

    next(); // Allow navigation
});

export default router;