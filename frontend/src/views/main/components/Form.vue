<script setup lang="ts">
const { x, y, r, errorMessage } = defineProps<{
  x: string;
  y: string;
  r: string;
  errorMessage: string;
}>();

const emit = defineEmits<{
  (e: 'update:x', value: string): void;
  (e: 'update:y', value: string): void;
  (e: 'update:r', value: string): void;
  (e: 'submit'): void;
}>();
</script>

<template>
  <div class="form__wrapper">
    <div class="form__input-group">
      <label class="form__label">Координата X (-3 ... 5):</label>
      <input
          class="form__input"
          type="text"
          :value="x"
          @input="emit('update:x', ($event.target as HTMLInputElement).value)"
          placeholder="Введите число"
          :class="{ 'form__input--error': errorMessage && errorMessage.includes('X') }"
      />
    </div>

    <div class="form__input-group">
      <label class="form__label">Координата Y (-5 ... 5):</label>
      <input
          class="form__input"
          type="text"
          :value="y"
          @input="emit('update:y', ($event.target as HTMLInputElement).value)"
          placeholder="Введите число"
          :class="{ 'form__input--error': errorMessage && errorMessage.includes('Y') }"
      />
    </div>

    <div class="form__input-group">
      <label class="form__label">Радиус R (-3 ... 5):</label>
      <input
          class="form__input"
          type="text"
          :value="r"
          @input="emit('update:r', ($event.target as HTMLInputElement).value)"
          placeholder="Введите число"
          :class="{ 'form__input--error': errorMessage && errorMessage.includes('R') }"
      />
    </div>

    <div class="form__error-msg" v-if="errorMessage">
      {{ errorMessage }}
    </div>

    <div class="form__actions">
      <button class="form__primary-btn" @click.prevent="emit('submit')">
        Проверить
      </button>
    </div>
  </div>
</template>

<style scoped lang="scss">
@use '../assets/form.scss';
</style>