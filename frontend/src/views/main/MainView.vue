<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { authService } from '../../services/auth.service.ts';
import { pointsService, type PointResponse } from '../../services/points.service.ts';

import Graph from "./components/Graph.vue"
import Form from './components/Form.vue';
import Table from './components/Table.vue';
import { validateXYR } from './utils/validators.ts';
import backgroundImg from './assets/background_graph.jpg';

const router = useRouter();

const xValue = ref('');
const yValue = ref('');
const rValue = ref('');
const points = ref<PointResponse[]>([]);
const errorMessage = ref('');
const currentUser = ref('');

onMounted(() => {
  currentUser.value = authService.getUsername();
  loadPoints();
});

const logout = () => {
  authService.logout();
  router.push('/auth/login');
};

const loadPoints = async () => {
  try { points.value = await pointsService.getPoints(); }
  catch (e) { console.error(e); }
};

const clearPoints = async () => {
  try { await pointsService.deleteAllPoints(); points.value = []; }
  catch (e) { errorMessage.value = 'Ошибка очистки'; }
};

const submitForm = () => {
  const result = validateXYR(xValue.value, yValue.value, rValue.value);
  if (!result.ok) { errorMessage.value = result.error!; return; }
  const x = parseFloat(xValue.value.replace(',', '.'));
  const y = parseFloat(yValue.value.replace(',', '.'));
  const r = parseFloat(rValue.value.replace(',', '.'));
  sendPoint(x, y, r);
};

const sendPoint = async (x: number, y: number, r: number) => {
  try { const newPoint = await pointsService.addPoint({ x, y, r }); points.value.push(newPoint); }
  catch (e) { errorMessage.value = 'Ошибка сервера'; }
};

const handleGraphClick = (x: number, y: number) => {
  const r = rNumeric.value;
  if (r <= 0) { errorMessage.value = 'Введите R > 0 для клика'; return; }
  sendPoint(x, y, r);
};

const rNumeric = computed(() => {
  const val = parseFloat(rValue.value.replace(',', '.'));
  if (!isNaN(val) && val > 0 && val <= 5 && errorMessage.value.includes('R')) errorMessage.value = '';
  return (!isNaN(val) && val > 0 && val <= 5) ? val : 0;
});
</script>

<template>
  <div
      class="layout__graph"
      :style="{ backgroundImage: `url(${backgroundImg})` }"
  >
    <div class="layout__app-container">
      <header class="layout__top-bar">
        <div class="layout__user-panel">
          <span class="layout__username">
            Пользователь: <strong>{{ currentUser }}</strong>
          </span>
        </div>

        <a
            href="#"
            class="layout__logout-link"
            @click.prevent="logout"
        >
          Выйти
        </a>
      </header>

      <main class="layout__main-content">
        <section class="layout__card layout__card--graph">
          <h2 class="layout__card-title">Область</h2>
          <Graph
              :points="points"
              :current-r="rNumeric"
              @graph-click="handleGraphClick"
          />
        </section>

        <section class="layout__card layout__card--form">
          <h2 class="layout__card-title">Параметры</h2>
          <Form
              v-model:x="xValue"
              v-model:y="yValue"
              v-model:r="rValue"
              :error-message="errorMessage"
              @submit="submitForm"
          />
        </section>
      </main>

      <section class="layout__card layout__card--table">
        <Table
            :points="points"
            :current-r="rNumeric"
            @clear="clearPoints"
        />
      </section>
    </div>
  </div>
</template>

<style scoped lang="scss">
@use 'assets/layout.scss';
</style>