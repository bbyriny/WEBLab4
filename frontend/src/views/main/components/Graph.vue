<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';
import type { PointResponse } from '../../../services/points.service.ts';
import { useGraphScript } from './graphLogic.ts';

const props = defineProps<{
  points: PointResponse[];
  currentR: number;
}>();

const emit = defineEmits<{
  (e: 'graph-click', x: number, y: number): void
}>();

const { GRAPH_WIDTH, GRAPH_HEIGHT, CENTER_X, CENTER_Y, rPx, halfRPx, drawPoints, onSvgClick } =
    useGraphScript(props, emit);
</script>

<template>
  <div class="graph__wrapper">
    <svg
        class="graph__svg"
        :width="GRAPH_WIDTH"
        :height="GRAPH_HEIGHT"
        :viewBox="`0 0 ${GRAPH_WIDTH} ${GRAPH_HEIGHT}`"
        @click="onSvgClick"
    >
      <polygon points="350,175 340,170 340,180" fill="black"/>
      <polygon points="175,0 170,10 180,10" fill="black"/>
      <text x="335" y="165" font-weight="bold">x</text>
      <text x="185" y="15" font-weight="bold">y</text>

      <g v-if="currentR > 0">
        <rect
            :x="CENTER_X"
            :y="CENTER_Y - rPx"
            :width="rPx"
            :height="rPx"
            fill="#4a90e2"
            fill-opacity="0.9"
            stroke="none"
        />
        <polygon
            :points="`${CENTER_X},${CENTER_Y} ${CENTER_X - rPx},${CENTER_Y} ${CENTER_X},${CENTER_Y - halfRPx}`"
            fill="#4a90e2"
            fill-opacity="0.9"
            stroke="none"
        />
        <path
            :d="`M ${CENTER_X} ${CENTER_Y} L ${CENTER_X - rPx} ${CENTER_Y} A ${rPx} ${rPx} 0 0 0 ${CENTER_X} ${CENTER_Y + rPx} Z`"
            fill="#4a90e2"
            fill-opacity="0.9"
            stroke="none"
        />
        <line :x1="CENTER_X + rPx" :y1="CENTER_Y - 3" :x2="CENTER_X + rPx" :y2="CENTER_Y + 3" stroke="black"/>
        <text :x="CENTER_X + rPx" :y="CENTER_Y + 15" text-anchor="middle" font-size="12">R</text>
        <line :x1="CENTER_X + halfRPx" :y1="CENTER_Y - 3" :x2="CENTER_X + halfRPx" :y2="CENTER_Y + 3" stroke="black"/>
        <text :x="CENTER_X + halfRPx" :y="CENTER_Y + 15" text-anchor="middle" font-size="12">R/2</text>
        <line :x1="CENTER_X - rPx" :y1="CENTER_Y - 3" :x2="CENTER_X - rPx" :y2="CENTER_Y + 3" stroke="black"/>
        <text :x="CENTER_X - rPx" :y="CENTER_Y + 15" text-anchor="middle" font-size="12">-R</text>
        <line :x1="CENTER_X - halfRPx" :y1="CENTER_Y - 3" :x2="CENTER_X - halfRPx" :y2="CENTER_Y + 3" stroke="black"/>
        <text :x="CENTER_X - halfRPx" :y="CENTER_Y + 15" text-anchor="middle" font-size="12">-R/2</text>

        <line :x1="CENTER_X - 3" :y1="CENTER_Y - rPx" :x2="CENTER_X + 3" :y2="CENTER_Y - rPx" stroke="black"/>
        <text :x="CENTER_X + 10" :y="CENTER_Y - rPx + 4" font-size="12">R</text>
        <line :x1="CENTER_X - 3" :y1="CENTER_Y - halfRPx" :x2="CENTER_X + 3" :y2="CENTER_Y - halfRPx" stroke="black"/>
        <text :x="CENTER_X + 10" :y="CENTER_Y - halfRPx + 4" font-size="12">R/2</text>
        <line :x1="CENTER_X - 3" :y1="CENTER_Y + rPx" :x2="CENTER_X + 3" :y2="CENTER_Y + rPx" stroke="black"/>
        <text :x="CENTER_X + 10" :y="CENTER_Y + rPx + 4" font-size="12">-R</text>
        <line :x1="CENTER_X - 3" :y1="CENTER_Y + halfRPx" :x2="CENTER_X + 3" :y2="CENTER_Y + halfRPx" stroke="black"/>
        <text :x="CENTER_X + 10" :y="CENTER_Y + halfRPx + 4" font-size="12">-R/2</text>
      </g>

      <line :x1="0" :y1="CENTER_Y" :x2="GRAPH_WIDTH" :y2="CENTER_Y" stroke="black" stroke-width="2"/>
      <line :x1="CENTER_X" :y1="0" :x2="CENTER_X" :y2="GRAPH_HEIGHT" stroke="black" stroke-width="2"/>

      <circle
          v-for="p in drawPoints"
          :key="p.id"
          :cx="p.cx"
          :cy="p.cy"
          r="4"
          :fill="p.isHitNow ? '#00C853' : '#D50000'"
          stroke="#222"
      />
    </svg>

    <p class="graph__hint" v-if="!currentR || currentR <= 0">
      Необходимо ввести R > 0
    </p>
  </div>
</template>

<style scoped lang="scss">
@use '../assets/graph.scss';
</style>
