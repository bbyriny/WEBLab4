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
  <form @submit.prevent="onSubmit" class="form">
    <div class="form__group">
      <label class="form__group-label">Логин</label>
      <input type="text" v-model="username" required class="form__group-input" />
    </div>

    <div class="form__group">
      <label class="form__group-label">Пароль</label>
      <input type="password" v-model="password" required class="form__group-input" />
    </div>

    <div v-if="errorMessage" class="form__error">{{ errorMessage }}</div>

    <button type="submit" :disabled="!username || !password || isLoading" class="form__button">
      {{ isLoading ? 'Вход...' : 'Войти' }}
    </button>

    <div class="form__separator">
      Нет аккаунта?
      <router-link class="form__separator-link" to="/auth/register">Зарегистрироваться</router-link>
    </div>
  </form>
</template>

<style scoped lang="scss">
@use '../assets/forms.scss';
</style>
