import { createRouter, createWebHistory } from 'vue-router';
import AuthView from '../views/auth/AuthView.vue';
import MainView from '../views/main/MainView.vue';
import { authService } from '../services/auth.service';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        { path: '/', redirect: '/auth/login' },

        {
            path: '/auth/login',
            component: AuthView,
            props: { mode: 'login' },
            meta: { guest: true }
        },

        {
            path: '/auth/register',
            component: AuthView,
            props: { mode: 'register' },
            meta: { guest: true }
        },

        {
            path: '/graph',
            component: MainView,
            meta: { requiresAuth: true }
        },
    ]
});

router.beforeEach((to, _from, next) => {
  const isLoggedIn = authService.isLoggedIn();

  if (to.meta.requiresAuth && !isLoggedIn) {
      next('/auth/login');
  } else if (to.meta.guest && isLoggedIn) {
    next('/graph'); 
  } else {
    next(); 
  }
});

export default router;