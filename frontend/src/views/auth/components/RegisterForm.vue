<script setup lang="ts">
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { authService } from '../../../services/auth.service';
import { useRegisterValidation } from '../composables/useRegisterValidation';

const router = useRouter();

const {
  username,
  password,
  confirmPassword,
  touched,
  usernameErrors,
  passwordErrors,
  isUserInvalid,
  isPassInvalid,
  isMismatch,
  isFormInvalid,
  onBlur,
} = useRegisterValidation();

const isLoading = ref(false);
const errorMessage = ref('');

const onSubmit = async () => {
  touched.username = true;
  touched.password = true;
  touched.confirmPassword = true;

  if (isFormInvalid.value) return;

  isLoading.value = true;
  errorMessage.value = '';

  try {
    await authService.register({
      username: username.value,
      password: password.value
    });
    router.push('/auth/login');
  } catch (e: any) {
    if (e.response?.status === 409) {
      errorMessage.value = 'Пользователь с таким логином уже существует';
    } else {
      errorMessage.value = 'Ошибка при регистрации';
    }
  } finally {
    isLoading.value = false;
  }
};
</script>

<template>
  <form @submit.prevent="onSubmit" class="form">

    <div class="form__group">
      <label class="form__group-label" for="username">Логин</label>
      <input
          id="username"
          type="text"
          v-model="username"
          @blur="onBlur('username')"
          placeholder="Придумайте логин"
          :class="{'form__group-input--error': isUserInvalid && touched.username}"
          class="form__group-input"
      />
      <div v-if="isUserInvalid && touched.username" class="form__error-list">
        <div v-if="usernameErrors.required" class="form__error-list__item">Логин обязателен.</div>
        <div v-if="usernameErrors.minlength" class="form__error-list__item">Минимум 3 символа.</div>
      </div>
    </div>

    <div class="form__group">
      <label class="form__group-label" for="password">Пароль</label>
      <input
          id="password"
          type="password"
          v-model="password"
          @blur="onBlur('password')"
          placeholder="Придумайте пароль"
          :class="{'form__group-input--error': isPassInvalid && touched.password}"
          class="form__group-input"
      />
      <div v-if="isPassInvalid && touched.password" class="form__error-list">
        <div v-if="passwordErrors.required" class="form__error-list__item">Пароль обязателен.</div>
        <div v-if="passwordErrors.minlength" class="form__error-list__item">Минимум 6 символов.</div>
        <div v-if="passwordErrors.upper" class="form__error-list__item">Нужна заглавная буква.</div>
        <div v-if="passwordErrors.lower" class="form__error-list__item">Нужна строчная буква.</div>
        <div v-if="passwordErrors.numeric" class="form__error-list__item">Нужна цифра.</div>
      </div>
    </div>

    <div class="form__group">
      <label class="form__group-label" for="confirmPassword">Повторите пароль</label>
      <input
          id="confirmPassword"
          type="password"
          v-model="confirmPassword"
          @blur="onBlur('confirmPassword')"
          placeholder="Повторите пароль"
          :class="{'form__group-input--error': isMismatch && touched.confirmPassword}"
          class="form__group-input"
      />
      <div v-if="isMismatch && touched.confirmPassword" class="form__error-list">
      <div class="form__error-list__item">Пароли не совпадают.</div>
    </div>
    </div>

    <div v-if="errorMessage" class="form__error">{{ errorMessage }}</div>

    <button type="submit" class="form__button" :disabled="isFormInvalid || isLoading">
      {{ isLoading ? 'Регистрация...' : 'Зарегистрироваться' }}
    </button>

    <div class="form__separator">
      Уже есть аккаунт?
      <router-link class="form__separator-link" to="/auth/login">Войти</router-link>
    </div>

  </form>
</template>

<style scoped lang="scss">
@use '../assets/forms.scss';
</style>