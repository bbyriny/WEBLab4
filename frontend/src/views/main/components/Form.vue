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
  <div class="form-wrapper">

    <div class="input-group">
      <label>Координата X (-3 ... 5):</label>
      <input
          type="text"
          :value="x"
          @input="emit('update:x', ($event.target as HTMLInputElement).value)"
          placeholder="Введите число"
          :class="{ 'error-border': errorMessage && errorMessage.includes('X') }"
      >
    </div>

    <div class="input-group">
      <label>Координата Y (-5 ... 5):</label>
      <input
          type="text"
          :value="y"
          @input="emit('update:y', ($event.target as HTMLInputElement).value)"
          placeholder="Введите число"
          :class="{ 'error-border': errorMessage && errorMessage.includes('Y') }"
      >
    </div>

    <div class="input-group">
      <label>Радиус R (-3 ... 5):</label>
      <input
          type="text"
          :value="r"
          @input="emit('update:r', ($event.target as HTMLInputElement).value)"
          placeholder="Введите число"
          :class="{ 'error-border': errorMessage && errorMessage.includes('R') }"
      >
    </div>

    <div class="error-msg" v-if="errorMessage">{{ errorMessage }}</div>

    <div class="form-actions">
      <button class="primary-btn" @click.prevent="emit('submit')">
        Проверить
      </button>
    </div>

  </div>
</template>

<style scoped>
@import '../assets/graph.css';
</style>