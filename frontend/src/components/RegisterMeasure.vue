<template>
  <div>
    <div class="register-header">
      <h1 class="register-title">Register Water Retention Measure</h1>
      <p class="register-subtitle">Submit a new water retention measure for approval</p>
    </div>

    <div class="register-card">
      <div class="register-card-content">
        <form class="register-form" @submit.prevent="submitForm">
          <div class="form-group">
            <label for="measure-type" class="form-label">Measure Type</label>
            <select id="measure-type" v-model="form.measureType" class="form-select">
              <option>Green Roof</option>
              <option>Rain Barrel</option>
              <option>Permeable Pavement</option>
              <option>Rain Garden</option>
              <option>Bioswale</option>
            </select>
          </div>

          <div class="form-group">
            <label for="description" class="form-label">Description</label>
            <textarea
                id="description"
                rows="4"
                v-model="form.description"
                class="form-textarea"
                placeholder="Describe your water retention measure..."
            ></textarea>
          </div>

          <div class="form-grid">
            <div class="form-group">
              <label for="area" class="form-label">Area (sq ft)</label>
              <input
                  type="number"
                  id="area"
                  v-model.number="form.area"
                  class="form-input"
                  placeholder="100"
              />
            </div>
            <div class="form-group">
              <label for="capacity" class="form-label">Water Capacity (gallons)</label>
              <input
                  type="number"
                  id="capacity"
                  v-model.number="form.capacity"
                  class="form-input"
                  placeholder="500"
              />
            </div>
          </div>

          <div class="form-group">
            <label for="location" class="form-label">Installation Location</label>
            <input
                type="text"
                id="location"
                v-model="form.location"
                class="form-input"
                placeholder="123 Main St, City, State"
            />
          </div>

          <div class="form-group">
            <label class="form-label">Upload Photos</label>
            <div class="upload-area">
              <div class="upload-content">
                <Upload class="upload-icon" />
                <div class="upload-text-row">
                  <label for="file-upload" class="upload-label">
                    <span>Upload files</span>
                    <input
                        id="file-upload"
                        name="file-upload"
                        type="file"
                        multiple
                        class="sr-only"
                        @change="handleFileUpload"
                    />
                  </label>
                  <p class="upload-or-text">or drag and drop</p>
                </div>
                <p class="upload-info">PNG, JPG, GIF up to 10MB</p>
              </div>
            </div>
          </div>

          <div class="form-actions">
            <button type="submit" class="submit-button">
              Submit for Approval
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { Upload } from 'lucide-vue-next';
import { createMeasure, validateMeasure } from '@/models/measure.js';

const currentUserId = 1;
const form = ref(createMeasure(currentUserId));

form.value.measureType = 'Green Roof';
form.value.area = null;
form.value.capacity = null;
form.value.files = [];

function handleFileUpload(event) {
  const files = Array.from(event.target.files);
  const reader = new FileReader();

  reader.onloadend = () => {
    form.value.photoUrl = reader.result;
    form.value.files = files;
  };

  if (files[0]) reader.readAsDataURL(files[0]);
}

function submitForm() {
  const errors = validateMeasure(form.value);
  if (errors.length > 0) {
    alert('Form errors:\n' + errors.join('\n'));
    return;
  }


  console.log('Submitted measure:', form.value);
  alert('Submitted for supervisor approval!');
}
</script>

<style scoped>
.register-header {
  margin-bottom: 2rem;
}

.register-title {
  font-size: 1.875rem;
  font-weight: 700;
  color: #111827;
  margin: 0;
}

.register-subtitle {
  margin-top: 0.5rem;
  color: #4b5563;
  font-size: 1rem;
}

.register-card {
  background: #fff;
  box-shadow: 0 1px 2px rgb(0 0 0 / 0.05);
  border-radius: 0.5rem;
}

.register-card-content {
  padding: 1.25rem 1rem 1.5rem;
}

.register-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-label {
  font-size: 0.875rem;
  font-weight: 500;
  color: #374151;
  margin-bottom: 0.25rem;
}

.form-select,
.form-input,
.form-textarea {
  border: 1px solid #d1d5db;
  border-radius: 0.375rem;
  padding: 0.5rem 0.75rem;
  font-size: 0.875rem;
  color: #111827;
  outline-offset: 2px;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

.form-select:focus,
.form-input:focus,
.form-textarea:focus {
  border-color: #3b82f6;
  box-shadow: 0 0 0 2px rgb(59 130 246 / 0.5);
  outline: none;
}

.form-textarea {
  resize: vertical;
  min-height: 6rem;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 1.5rem;
}

@media (min-width: 640px) {
  .form-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

.upload-area {
  margin-top: 0.25rem;
  padding: 1.25rem 1.5rem 1.5rem;
  border: 2px dashed #d1d5db;
  border-radius: 0.375rem;
  display: flex;
  justify-content: center;
}

.upload-content {
  text-align: center;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.upload-icon {
  height: 3rem;
  width: 3rem;
  color: #9ca3af;
  margin: 0 auto;
}

.upload-text-row {
  display: flex;
  justify-content: center;
  gap: 0.25rem;
  font-size: 0.875rem;
  color: #4b5563;
  align-items: center;
}

.upload-label {
  cursor: pointer;
  background: white;
  border-radius: 0.375rem;
  font-weight: 500;
  color: #2563eb;
  padding: 0.125rem 0.5rem;
  user-select: none;
  transition: color 0.2s ease;
}

.upload-label:hover {
  color: #1e40af;
}

.upload-label:focus-within {
  outline: 2px solid #3b82f6;
  outline-offset: 2px;
}

.sr-only {
  position: absolute !important;
  width: 1px !important;
  height: 1px !important;
  padding: 0 !important;
  margin: -1px !important;
  overflow: hidden !important;
  clip: rect(0,0,0,0) !important;
  white-space: nowrap !important;
  border: 0 !important;
}

.upload-or-text {
  padding-left: 0.25rem;
}

.upload-info {
  font-size: 0.75rem;
  color: #6b7280;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
}

.submit-button {
  margin-left: 0.75rem;
  padding: 0.5rem 1rem;
  font-size: 0.875rem;
  font-weight: 500;
  color: white;
  background-color: #2563eb;
  border: none;
  border-radius: 0.375rem;
  box-shadow: 0 1px 2px rgb(37 99 235 / 0.5);
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.submit-button:hover {
  background-color: #1e40af;
}

.submit-button:focus {
  outline: 2px solid #3b82f6;
  outline-offset: 2px;
}
</style>
