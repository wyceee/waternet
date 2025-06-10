import axios from 'axios';

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
}

export default new MeasureService();