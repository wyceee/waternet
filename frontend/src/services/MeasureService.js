import  axios  from '../services/AxiosInstance.js';

const API_URL = '/api/measures';

class MeasureService {
    async getAllMeasures() {
        try {
            const response = await axios.get(API_URL);
            return response.data;
        } catch (error) {
            throw new Error(error.response?.data || 'Failed to fetch measures');
        }
    }

    async getMeasureById(id) {
        try {
            const response = await axios.get(`${API_URL}/${id}`);
            return response.data;
        } catch (error) {
            throw new Error(error.response?.data || 'Failed to fetch measure');
        }
    }

    async getMeasuresByUserId(userId) {
        try {
            const response = await axios.get(`${API_URL}/user/${userId}`);
            return response.data;
        } catch (error) {
            throw new Error(error.response?.data || 'Failed to fetch measures by user');
        }
    }

    async submitMeasure(measure) {
        try {
            const response = await axios.post(API_URL, measure);
            return response.data;
        } catch (error) {
            throw new Error(error.response?.data || 'Failed to submit measure');
        }
    }

    async approveMeasure(id) {
        try {
            const response = await axios.post(`${API_URL}/${id}/approve`);
            return response.data;
        } catch (error) {
            throw new Error(error.response?.data || 'Failed to approve measure');
        }
    }

    async rejectMeasure(id) {
        try {
            const response = await axios.post(`${API_URL}/${id}/reject`);
            return response.data;
        } catch (error) {
            throw new Error(error.response?.data || 'Failed to reject measure');
        }
    }

    async getUserRewardsPaginated(userId, page = 0, size = 10) {
        try {
            const response = await axios.get(`/api/measures/user/${userId}/rewards`, {
                params: { page, size }
            });

            const { content, totalElements } = response.data;
            return {
                data: content,
                total: totalElements
            };
        } catch (error) {
            console.error('Failed to fetch paginated rewards:', error);
            return { data: [], total: 0 };
        }
    }

}

export default new MeasureService();