import axios from '../services/AxiosInstance.js';

const API_URL = '/api/users';

class UserBalanceService {
    async getUserBalance(userId) {
        try {
            const response = await axios.get(`${API_URL}/${userId}/balance`);
            return response.data;
        } catch (error) {
            console.error('Error fetching user balance:', error.response?.data || error.message);
            return null;
        }
    }
}

export default new UserBalanceService();