// Auth utility functions

/**
 * Get the JWT token from localStorage
 */
function getToken() {
    return localStorage.getItem('token');
}

/**
 * Get user ID from localStorage
 */
function getUserId() {
    return localStorage.getItem('userId');
}

/**
 * Check if user is logged in
 */
function isLoggedIn() {
    return !!getToken();
}

/**
 * Make an authenticated API request
 */
async function authFetch(url, options = {}) {
    const token = getToken();
    
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };
    
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }
    
    return fetch(url, {
        ...options,
        headers
    });
}
