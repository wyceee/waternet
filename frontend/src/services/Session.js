import { shallowReactive } from 'vue';
import { SessionService } from './SessionService.js';

export const BACKEND_URL = 'http://localhost:8085';
const JWT_STORAGE_ITEM = 'session_token';

export const sessionService = shallowReactive(
    new SessionService(`${BACKEND_URL}/authentication`, JWT_STORAGE_ITEM)
);
