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
  <div class="table-container">
    <div class="table-header">
      <h2>История</h2>
      <button class="clear-btn" v-if="points.length > 0" @click="emit('clear')">
        Очистить всё
      </button>
    </div>

    <div class="table-responsive">
      <table>
        <thead>
        <tr>
          <th>X</th><th>Y</th><th>R</th><th>Результат</th>
          <th>Текущее время</th><th>Время выполнения</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="p in points" :key="p.id">
          <td>{{ p.x }}</td>
          <td>{{ p.y }}</td>
          <td>{{ p.r }}</td>
          <td :class="hit(p) ? 'hit' : 'miss'">
            {{ hit(p) ? 'Попал' : 'Промах' }}
          </td>
          <td>{{ p.startTime }}</td>
          <td>{{ p.executionTime }} мс</td>
        </tr>
        <tr v-if="points.length === 0">
          <td colspan="6" class="empty-cell">Нет данных</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped>
@import "../assets/graph.css";
</style>