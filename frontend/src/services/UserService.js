import axios from 'axios';

const API_URL = '/authentication';

class UserService {
    async login(email, password) {
        try {
            const response = await axios.post(`${API_URL}/login`, { email, password });
            return response.data;
        } catch (error) {
            throw new Error(error.response?.data || 'Login failed');
        }
    }

    async register(name, email, hashedPassword) {
        try {
            const response = await axios.post(`${API_URL}/register`, { name, email, hashedPassword });
            return response.data;
        } catch (error) {
            throw new Error(error.response?.data || 'Registration failed');
        }
    }
}

export default new UserService();