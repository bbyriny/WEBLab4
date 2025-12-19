<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { authService } from '../../../services/auth.service';

const router = useRouter();
const username = ref('');
const password = ref('');
const errorMessage = ref('');
const isLoading = ref(false);

const onSubmit = async () => {
  errorMessage.value = '';
  isLoading.value = true;

  try {
    await authService.login({
      username: username.value,
      password: password.value
    });
    router.push('/graph');
  } catch (e: any) {
    errorMessage.value = e.response?.status === 400
        ? 'Неверный логин или пароль'
        : 'Ошибка сервера';
  } finally {
    isLoading.value = false;
  }
};
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="input-group">
      <label>Логин</label>
      <input type="text" v-model="username" required>
    </div>

    <div class="input-group">
      <label>Пароль</label>
      <input type="password" v-model="password" required>
    </div>

    <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

    <button :disabled="!username || !password || isLoading">
      {{ isLoading ? 'Вход...' : 'Войти' }}
    </button>

    <div class="separator">
      Нет аккаунта?
      <router-link to="/auth/register">Зарегистрироваться</router-link>
    </div>
  </form>
</template>

<style scoped>
@import '../assets/login.css';
</style>
