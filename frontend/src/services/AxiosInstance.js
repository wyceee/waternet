import axios from 'axios'

const instance = axios.create({
  baseURL: 'http://localhost:8085'
})

instance.interceptors.request.use((config) => {
  const rawData = localStorage.getItem('session_token') || sessionStorage.getItem('session_token')

  if (rawData) {
    try {
      const parsed = JSON.parse(rawData)
      let token = parsed?.token || ''
      token = token.replace(/^Bearer\s+/i, '')

      config.headers.Authorization = `Bearer ${token}`
    } catch (e) {
      console.warn('Invalid token format in storage', e)
    }
  }

  return config
})

export default instance
