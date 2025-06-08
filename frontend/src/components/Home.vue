<template>
  <div v-if="currentPage === 'home'" class="home-page">
    <div class="hero-section">
      <div class="hero-container">
        <div class="hero-icon">
          <div class="icon-circle">
            <Droplets class="icon" />
          </div>
        </div>
        <h1 class="hero-title">
          Turn Water Conservation Into
          <span class="highlight">Rewards</span>
        </h1>
        <p class="hero-description">
          Join the Hydrewards ecosystem and earn tokens for implementing water retention measures.
          Help your community manage stormwater while building a sustainable future.
        </p>
      </div>
    </div>

    <div class="features-section">
      <div class="section-header">
        <h2>How Hydrewards Works</h2>
        <p>Simple steps to start earning rewards for your water conservation efforts</p>
      </div>
      <div class="features-grid">
        <div class="feature-item">
          <div class="feature-icon green-bg">
            <Leaf class="feature-icon-inner green" />
          </div>
          <h3>1. Implement Measures</h3>
          <p>Install water retention solutions like green roofs, rain barrels, or permeable pavement</p>
        </div>
        <div class="feature-item">
          <div class="feature-icon yellow-bg">
            <FileCheck class="feature-icon-inner yellow" />
          </div>
          <h3>2. Submit for Approval</h3>
          <p>Register your water retention measure with photos and details for supervisor review</p>
        </div>
        <div class="feature-item">
          <div class="feature-icon blue-bg">
            <Coins class="feature-icon-inner blue" />
          </div>
          <h3>3. Earn Tokens</h3>
          <p>Receive HYD tokens on VeChainThor blockchain once your measure is approved</p>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref } from 'vue';
import { Droplets, Leaf, FileCheck, Coins} from 'lucide-vue-next';

const currentPage = ref('home');

</script>

<style scoped>
.home-page {
  padding: 1.5rem;
}

.hero-section {
  text-align: center;
  margin-bottom: 7rem;
  background: rgba(233, 243, 255, 0.6);
  padding: 1rem 0;
  border-radius: 1rem;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.1), 0 1px 3px rgba(0, 0, 0, 0.06);
}

.hero-container {
  max-width: 64rem;
  margin: 0 auto;
}

.hero-icon {
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
}

.icon-circle {
  background-color: #bfdbfe;
  border-radius: 50%;
  padding: 1.5rem;
}

.icon {
  width: 4rem;
  height: 4rem;
  color: #2563eb;
}

.hero-title {
  font-size: 3rem;
  font-weight: 700;
  color: #111827;
  margin-bottom: 1.5rem;
}

.highlight {
  color: #2563eb;
}

.hero-description {
  font-size: 1.25rem;
  color: #4b5563;
  margin-bottom: 2rem;
  max-width: 48rem;
  margin-left: auto;
  margin-right: auto;
}

.features-section{
  margin-bottom: 4rem;
}

.section-header {
  text-align: center;
  margin-bottom: 4rem;
}

.section-header h2 {
  font-size: 1.875rem;
  font-weight: 700;
  color: #111827;
  margin-bottom: 1rem;
}

.section-header p {
  font-size: 1.125rem;
  color: #4b5563;
  max-width: 32rem;
  margin: 0 auto;
}

.features-grid{
  display: grid;
  gap: 2rem;
}

.features-grid {
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
}

.feature-item {
  text-align: center;
}

.feature-icon {
  border-radius: 50%;
  padding: 1rem;
  width: 4rem;
  height: 4rem;
  margin: 0 auto 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.green-bg { background-color: #d1fae5; }
.yellow-bg { background-color: #fef9c3; }
.blue-bg { background-color: #92c1ff; }

.feature-icon-inner {
  width: 2rem;
  height: 2rem;
}

.green { color: #16a34a; }
.yellow { color: #ca8a04; }
.blue { color: #2563eb; }

.cta-section h2 {
  font-size: 1.875rem;
  font-weight: 700;
  color: #111827;
  margin-bottom: 1rem;
}

.cta-section p {
  font-size: 1.125rem;
  color: #4b5563;
  margin-bottom: 2rem;
  max-width: 32rem;
  margin-left: auto;
  margin-right: auto;
}
</style>
