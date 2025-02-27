// src/services/api.js
import {API_BASE_URL} from '../../config'
import {refreshToken} from "@/services/auth";

export const fetchWithToken = async (endpoint, options = {}) => {
    const url = `${API_BASE_URL}${endpoint}`
    options.headers = options.headers || {}

    let accessToken = localStorage.getItem('accessToken')
    if (accessToken) {
        options.headers['Authorization'] = accessToken
    }

    let response = await fetch(url, options)

    if (response.status === 403) {
        accessToken = await refreshToken()
        options.headers['Authorization'] = accessToken
        response = await fetch(url, options)
    }

    return response
}