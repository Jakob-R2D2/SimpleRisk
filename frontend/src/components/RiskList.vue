<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
  apiBase: { type: String, required: true },
})

const risks = ref([])
const loading = ref(false)
const error = ref(null)
const form = ref({ title: '', description: '', status: 'OPEN' })

async function loadRisks() {
  loading.value = true
  error.value = null
  try {
    const res = await fetch(`${props.apiBase}/risks`)
    if (!res.ok) throw new Error('Risiken konnten nicht geladen werden')
    risks.value = await res.json()
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}

async function createRisk() {
  if (!form.value.title?.trim()) return
  loading.value = true
  error.value = null
  try {
    const res = await fetch(`${props.apiBase}/risks`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        title: form.value.title.trim(),
        description: form.value.description?.trim() || null,
        status: form.value.status,
      }),
    })
    if (!res.ok) throw new Error('Risiko konnte nicht erstellt werden')
    form.value = { title: '', description: '', status: 'OPEN' }
    await loadRisks()
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}

onMounted(loadRisks)
</script>

<template>
  <section class="risk-section">
    <h2>Risiken</h2>
    <p v-if="error" class="error">{{ error }}</p>

    <form @submit.prevent="createRisk" class="form">
      <input
        v-model="form.title"
        type="text"
        placeholder="Titel"
        required
        class="input"
      />
      <textarea
        v-model="form.description"
        placeholder="Beschreibung (optional)"
        rows="2"
        class="input"
      />
      <select v-model="form.status" class="input">
        <option value="OPEN">Offen</option>
        <option value="IN_PROGRESS">In Bearbeitung</option>
        <option value="CLOSED">Geschlossen</option>
      </select>
      <button type="submit" :disabled="loading" class="btn">Anlegen</button>
    </form>

    <div v-if="loading && !risks.length" class="loading">Lade …</div>
    <ul v-else class="list">
      <li v-for="r in risks" :key="r.id" class="card">
        <strong>{{ r.title }}</strong>
        <span class="badge">{{ r.status }}</span>
        <p v-if="r.description" class="desc">{{ r.description }}</p>
      </li>
    </ul>
  </section>
</template>

<style scoped>
.risk-section h2 {
  margin: 0 0 1rem 0;
  font-size: 1.1rem;
}
.error {
  color: #e66;
  margin-bottom: 1rem;
}
.form {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  margin-bottom: 1.5rem;
}
.input {
  padding: 0.5rem;
  border: 1px solid #444;
  border-radius: 6px;
  background: #252538;
  color: #eee;
  font-size: 1rem;
}
.input:focus {
  outline: none;
  border-color: #6c8;
}
.btn {
  padding: 0.6rem 1rem;
  background: #2d5a3d;
  color: #fff;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  font-size: 1rem;
}
.btn:hover:not(:disabled) {
  background: #3d6a4d;
}
.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}
.loading {
  color: #888;
}
.list {
  list-style: none;
  margin: 0;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}
.card {
  padding: 1rem;
  background: #252538;
  border-radius: 8px;
  border: 1px solid #333;
}
.badge {
  margin-left: 0.5rem;
  font-size: 0.75rem;
  color: #8a8;
  font-weight: normal;
}
.desc {
  margin: 0.5rem 0 0 0;
  color: #aaa;
  font-size: 0.9rem;
}
</style>
