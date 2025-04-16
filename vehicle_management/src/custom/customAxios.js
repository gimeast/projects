import { requestRefreshToken, saveToken } from "../api.js";
import axios from "axios";
import Cookies from "universal-cookie";


const jwtAxios = axios.create()
const cookies = new Cookies(null, { path: '/', maxAge: 60 * 60 * 24 * 30 })

const beforeRequest = (config) => {
    console.log('beforeRequest')
    const accessToken = cookies.get("accessToken")
    if(!accessToken) {
        throw Error("No Token")
    }

    config.headers["Authorization"] = "Bearer " + accessToken
    return config;
}

const beforeResponse = (response) => {
    console.log('beforeResponse')
    return response
}

const errorResponse = (error) => {
    console.log('errorResponse')

    const status = error.response.status;
    const res = error.response.data
    const errorMsg = res.error
    console.error(status, res, errorMsg)

    const refreshFn = async () => {
        console.log("Refresh Token")
        const data = await requestRefreshToken()
        saveToken("accessToken", data.accessToken)
        saveToken("refreshToken", data.refreshToken)

        error.config.headers["Authorization"] = "Bearer " + data.accessToken
        return await axios(error.config)
    }

    if(errorMsg.includes("expired")) {
        return refreshFn()
    } else {
        return Promise.reject(error)
    }
}

jwtAxios.interceptors.request.use(beforeRequest)
jwtAxios.interceptors.response.use(beforeResponse, errorResponse)

export default jwtAxios