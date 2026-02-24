<script setup>
import { ref, onMounted } from 'vue'
import RiskList from './components/RiskList.vue'

const apiBase = import.meta.env.DEV ? '/api' : 'http://localhost:8080/api'

onMounted(() => {})
</script>

<template>
  <div class="app">
    <header class="header">
      <h1>SimpleRisk</h1>
      <p>Risikomanagement – Backend 3-Layer / DAO, Vue.js Frontend</p>
    </header>
    <main>
      <RiskList :api-base="apiBase" />
    </main>
  </div>
</template>

<style>
* {
  box-sizing: border-box;
}
body {
  margin: 0;
  font-family: system-ui, sans-serif;
  background: #1a1a2e;
  color: #eee;
  min-height: 100vh;
}
.app {
  max-width: 720px;
  margin: 0 auto;
  padding: 1.5rem;
}
.header {
  margin-bottom: 2rem;
  border-bottom: 1px solid #333;
  padding-bottom: 1rem;
}
.header h1 {
  margin: 0 0 0.25rem 0;
  font-size: 1.5rem;
}
.header p {
  margin: 0;
  color: #888;
  font-size: 0.9rem;
}
</style>
