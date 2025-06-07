<template>
  <div class="dashboard-container">
    <div class="header-section">
      <h1 class="dashboard-title">Dashboard</h1>
      <p class="dashboard-subtitle">Track your water retention measures and rewards</p>
    </div>

    <!-- Stats Cards -->
    <div class="stats-grid">
      <div class="card">
        <div class="card-content">
          <div class="card-row">
            <div class="icon-wrapper gray-icon">
              <BarChart3 class="icon" />
            </div>
            <div class="text-wrapper">
              <dl>
                <dt class="label">Total Measures</dt>
                <dd class="value">24</dd>
              </dl>
            </div>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-content">
          <div class="card-row">
            <div class="icon-wrapper green-icon">
              <CheckCircle class="icon" />
            </div>
            <div class="text-wrapper">
              <dl>
                <dt class="label">Approved</dt>
                <dd class="value">18</dd>
              </dl>
            </div>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-content">
          <div class="card-row">
            <div class="icon-wrapper yellow-icon">
              <Clock class="icon" />
            </div>
            <div class="text-wrapper">
              <dl>
                <dt class="label">Pending</dt>
                <dd class="value">4</dd>
              </dl>
            </div>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-content">
          <div class="card-row">
            <div class="icon-wrapper blue-icon">
              <Coins class="icon" />
            </div>
            <div class="text-wrapper">
              <dl>
                <dt class="label">Tokens Earned</dt>
                <dd class="value">1,250 AQR</dd>
              </dl>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Recent Measures -->
    <div class="recent-measures">
      <div class="recent-header">
        <h3 class="recent-title">Your Recent Measures</h3>
        <p class="recent-subtitle">Latest water retention measures submitted</p>
      </div>
      <ul class="measures-list">
        <li class="measure-item">
          <div class="measure-info">
            <div class="avatar green-bg">
              <Leaf class="avatar-icon green-icon-dark" />
            </div>
            <div class="measure-text">
              <div class="measure-name">Green Roof Installation</div>
              <div class="measure-date">Submitted 2 days ago</div>
            </div>
          </div>
          <span class="status approved">Approved</span>
        </li>

        <li class="measure-item">
          <div class="measure-info">
            <div class="avatar blue-bg">
              <Droplets class="avatar-icon blue-icon-dark" />
            </div>
            <div class="measure-text">
              <div class="measure-name">Rain Barrel System</div>
              <div class="measure-date">Submitted 5 days ago</div>
            </div>
          </div>
          <span class="status pending">Pending</span>
        </li>

        <li class="measure-item">
          <div class="measure-info">
            <div class="avatar purple-bg">
              <TreePine class="avatar-icon purple-icon-dark" />
            </div>
            <div class="measure-text">
              <div class="measure-name">Permeable Pavement</div>
              <div class="measure-date">Submitted 1 week ago</div>
            </div>
          </div>
          <span class="status approved">Approved</span>
        </li>
      </ul>
    </div>
  </div>
</template>

<style>
.dashboard-container {
  margin: 3rem 30rem;
}

@media (min-width: 640px) {
  .dashboard-container {
    padding-left: 0;
    padding-right: 0;
  }
}

.header-section {
  margin-bottom: 32px;
}

.dashboard-title {
  font-size: 1.875rem; /* 3xl */
  font-weight: 700;
  color: #111827; /* gray-900 */
  margin: 0;
}

.dashboard-subtitle {
  margin-top: 8px;
  color: #4b5563; /* gray-600 */
  font-size: 1rem;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: 20px;
  margin-bottom: 32px;
}

@media (min-width: 640px) {
  .stats-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (min-width: 1024px) {
  .stats-grid {
    grid-template-columns: repeat(4, 1fr);
  }
}

.card {
  background-color: #ffffff;
  overflow: hidden;
  box-shadow: 0 1px 3px rgb(0 0 0 / 0.1),
  0 1px 2px rgb(0 0 0 / 0.06);
  border-radius: 0.5rem; /* rounded-lg */
}

.card-content {
  padding: 20px;
}

.card-row {
  display: flex;
  align-items: center;
}

.icon-wrapper {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.gray-icon {
  color: #9ca3af; /* gray-400 */
}

.green-icon {
  color: #34d399; /* green-400 */
}

.yellow-icon {
  color: #fbbf24; /* yellow-400 */
}

.blue-icon {
  color: #60a5fa; /* blue-400 */
}

.icon {
  width: 24px; /* 6 */
  height: 24px;
}

.text-wrapper {
  margin-left: 20px;
  flex: 1 1 0%;
  min-width: 0;
}

.label {
  font-size: 0.875rem; /* text-sm */
  font-weight: 500;
  color: #6b7280; /* gray-500 */
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin: 0;
}

.value {
  font-size: 1.125rem; /* text-lg */
  font-weight: 500;
  color: #111827; /* gray-900 */
  margin: 0;
}

/* Recent Measures */

.recent-measures {
  background-color: #fff;
  box-shadow: 0 1px 3px rgb(0 0 0 / 0.1),
  0 1px 2px rgb(0 0 0 / 0.06);
  overflow: hidden;
  border-radius: 0.375rem; /* rounded-md */
}

.recent-header {
  padding: 20px 16px;
}

.recent-title {
  font-size: 1.125rem; /* lg */
  font-weight: 500;
  color: #111827; /* gray-900 */
  margin: 0;
  line-height: 1.5rem; /* leading-6 */
}

.recent-subtitle {
  margin-top: 4px;
  max-width: 32rem; /* max-w-2xl */
  font-size: 0.875rem; /* sm */
  color: #6b7280; /* gray-500 */
  margin-bottom: 0;
}

.measures-list {
  list-style: none;
  margin: 0;
  padding: 0;
  border-top: 1px solid #e5e7eb; /* divide-gray-200 */
}

.measure-item {
  padding: 16px 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #e5e7eb; /* divide-gray-200 */
}

.measure-info {
  display: flex;
  align-items: center;
}

.avatar {
  flex-shrink: 0;
  width: 40px;
  height: 40px;
  border-radius: 9999px; /* full */
  display: flex;
  align-items: center;
  justify-content: center;
}

.green-bg {
  background-color: #d1fae5; /* green-100 */
}

.blue-bg {
  background-color: #bfdbfe; /* blue-100 */
}

.purple-bg {
  background-color: #e9d5ff; /* purple-100 */
}

.avatar-icon {
  width: 20px;
  height: 20px;
}

.green-icon-dark {
  color: #059669; /* green-600 */
}

.blue-icon-dark {
  color: #2563eb; /* blue-600 */
}

.purple-icon-dark {
  color: #7c3aed; /* purple-600 */
}

.measure-text {
  margin-left: 16px;
}

.measure-name {
  font-size: 0.875rem;
  font-weight: 500;
  color: #111827;
  margin: 0;
}

.measure-date {
  font-size: 0.875rem;
  color: #6b7280;
  margin: 0;
}

.status {
  padding: 2px 8px;
  font-size: 0.75rem; /* text-xs */
  line-height: 1.25rem; /* leading-5 */
  font-weight: 600;
  border-radius: 9999px; /* rounded-full */
  display: inline-flex;
  align-items: center;
  white-space: nowrap;
}

.approved {
  background-color: #d1fae5; /* green-100 */
  color: #065f46; /* green-800 */
}

.pending {
  background-color: #fef3c7; /* yellow-100 */
  color: #92400e; /* yellow-800 */
}
</style>
