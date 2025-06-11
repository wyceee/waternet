export class SessionService {
    BROWSER_STORAGE_ITEM_NAME;
    RESOURCES_URL;
    token;
    user;

    constructor(resourcesUrl, browserStorageItemName) {
        this.BROWSER_STORAGE_ITEM_NAME = browserStorageItemName;
        this.RESOURCES_URL = resourcesUrl;
        this.token = null;
        this.user = null;

        console.log("BROWSER_STORAGE_ITEM_NAME:", this.BROWSER_STORAGE_ITEM_NAME); // Debugging

        // Initialize session from browser storage
        this.getTokenFromBrowserStorage();
    }

    async signIn(email, password) {
        try {
            const body = JSON.stringify({ email, password });

            const response = await fetch(`${this.RESOURCES_URL}/login`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body,
                credentials: 'include',
            });

            if (!response.ok) {
                console.error('Login failed:', response.status, response.statusText);
                return null;
            }

            const user = await response.json();
            const token = response.headers.get('Authorization');

            if (!token || !user) {
                console.error('Login response missing token or user data');
                return null;
            }

            this.saveTokenIntoBrowserStorage(token, user);
            return user;
        } catch (error) {
            console.error('Login error:', error);
            return null;
        }
    }

    async register(name, email, hashedPassword) {
        try {
            const response = await fetch(`${this.RESOURCES_URL}/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ name, email, hashedPassword }),
            });

            if (!response.ok) {
                throw new Error(await response.text() || 'Registration failed');
            }

            const user = await response.json();

            return user;
        } catch (error) {
            console.error('Registration error:', error);
            throw new Error(error.message || 'Registration failed');
        }
    }

    signOut(router) {
        this.token = null;
        this.user = null;
        window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
        window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);

        router.push('/');
    }

    saveTokenIntoBrowserStorage(token, user, persistent = false) {
        this.token = token;
        this.user = user;
        const data = JSON.stringify({ token, user });

        if (persistent) {
            window.localStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, data);
        } else {
            window.sessionStorage.setItem(this.BROWSER_STORAGE_ITEM_NAME, data);
        }
    }

    getTokenFromBrowserStorage() {
        const sessionData = window.sessionStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);
        const localData = window.localStorage.getItem(this.BROWSER_STORAGE_ITEM_NAME);

        const data = sessionData || localData;
        if (data) {
            const parsedData = JSON.parse(data);
            this.token = parsedData.token;
            this.user = parsedData.user;
        }
    }

    getCurrentToken() {
        return this.token;
    }

    isAuthenticated() {
        return !!this.user;
    }
}