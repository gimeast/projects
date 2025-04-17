import jwtAxios from "./custom/jwtAxios.js";

const url = "http://localhost:8080/api/v1"

export const getSpecList = async (search, page) => {
    const path = url + `/admin/vehicles?search=${ search }&page=${ page }`
    const res = await jwtAxios.get(path);
    return res.data
}
