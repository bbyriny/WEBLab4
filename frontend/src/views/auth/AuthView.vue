<script setup lang="ts">
import { computed } from 'vue';

import AuthHeader from './components/AuthHeader.vue';
import LoginForm from './components/LoginForm.vue';
import RegisterForm from './components/RegisterForm.vue';
import backgroundImg from './assets/background.jpg';

const props = defineProps<{ mode: 'login' | 'register' }>();
const mode = computed(() => props.mode);

</script>

<template>
  <div class="auth-layout" :style="{ backgroundImage: `url(${backgroundImg})` }">

    <div class="auth-container">

      <AuthHeader />

      <main class="auth-box">
        <h2 v-if="mode === 'login'">Вход</h2>
        <h2 v-else>Регистрация</h2>

        <LoginForm v-if="mode === 'login'" />
        <RegisterForm v-else />

      </main>

    </div>

  </div>
</template>

<style scoped>
@import 'assets/auth.css';
</style>
