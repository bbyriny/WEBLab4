<script setup lang="ts">
import type { PointResponse } from '../../../services/points.service.ts';
import { checkHit } from '../utils/hitCheck.ts';

const { points, currentR } = defineProps<{
  points: PointResponse[];
  currentR: number;
}>();

const emit = defineEmits<{
  (e: 'clear'): void;
}>();

const hit = (p: PointResponse) => checkHit(p.x, p.y, currentR || p.r);
</script>

<template>
  <div class="table__wrapper">
    <div class="table__header">
      <h2 class="table__title">История</h2>
      <button
          class="table__clear-btn"
          v-if="points.length > 0"
          @click="emit('clear')"
      >
        Очистить всё
      </button>
    </div>

    <div class="table__responsive">
      <table class="table__table">
        <thead>
        <tr>
          <th class="table__cell table__cell--header">X</th>
          <th class="table__cell table__cell--header">Y</th>
          <th class="table__cell table__cell--header">R</th>
          <th class="table__cell table__cell--header">Результат</th>
          <th class="table__cell table__cell--header">Текущее время</th>
          <th class="table__cell table__cell--header">Время выполнения</th>
        </tr>
        </thead>

        <tbody>
        <tr v-for="p in points" :key="p.id">
          <td class="table__cell">{{ p.x }}</td>
          <td class="table__cell">{{ p.y }}</td>
          <td class="table__cell">{{ p.r }}</td>
          <td
              :class="[
                'table__cell',
                hit(p) ? 'table__cell--hit' : 'table__cell--miss'
              ]"
          >
            {{ hit(p) ? 'Попал' : 'Промах' }}
          </td>
          <td class="table__cell">{{ p.startTime }}</td>
          <td class="table__cell">{{ p.executionTime }} мс</td>
        </tr>

        <tr v-if="points.length === 0">
          <td colspan="6" class="table__cell table__cell--empty">
            Нет данных
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped lang="scss">
@use '../assets/table.scss';
</style>