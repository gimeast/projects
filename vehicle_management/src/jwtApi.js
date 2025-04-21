import axios from 'axios'
import Cookies from 'universal-cookie'
import jwtAxios from "./custom/jwtAxios.js";
import API_BASE_URL from "./config/apiConfig.js";

const url = API_BASE_URL
export const makeToken = async (mid, mpw) => {
    const path = url + "/token/make"
    const data = { mid, mpw }
    const res = await axios.post(path, data);
    return res.data;
}

const cookies = new Cookies(null, { path: '/', maxAge: 60 * 60 * 24 * 30 })

export const saveToken = (tokenName, tokenValue) => {
    cookies.set(tokenName, tokenValue)
}

export const getSamples = async (pageNum) => {
    const path = url + "/samples/list"
    const res = await jwtAxios.get(path);
    return res.data
}

export async function requestRefreshToken() {
    const mid = cookies.get("mid")
    const accessToken = cookies.get("accessToken")
    const refreshToken = cookies.get("refreshToken")

    if(!mid || !refreshToken || !accessToken) {
        throw Error("Cannot request refresh..")
    }

    const path = url + "/token/refresh"
    const header = {
        'content-type': 'application/x-www-form-urlencoded',
        'Authorization': 'Bearer ' + accessToken
    }
    const data = { refreshToken, mid }
    const res = await axios.post(path, data, { headers: header })
    return res.data
}