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

    async signIn(email, password) /* : Promise<User> */ {
        const body = JSON.stringify({ email: email, password: password });
        let response = await fetch(this.RESOURCES_URL + "/login", {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: body,
            credentials: 'include',
        });

        if (response.ok) {
            let user = await response.json();
            this.saveTokenIntoBrowserStorage(
                response.headers.get('Authorization'),
                user
            );
            return user;
        } else {
            console.log(response);
            return null;
        }
    }

    signOut() {
        this.token = null;
        this.user = null;
        window.sessionStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);
        window.localStorage.removeItem(this.BROWSER_STORAGE_ITEM_NAME);

        window.location.href = '/';
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