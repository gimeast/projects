import jwtAxios from "./custom/jwtAxios.js";
import API_BASE_URL from "./config/apiConfig.js";


const url = API_BASE_URL

export const getVehicleList = async () => {
    const path = `${ url }/vehicles`;
    const res = await jwtAxios.get(path);
    return res.data
}

export const getBrandList = async () => {
    const path = `${ url }/vehicles/brand`;
    const res = await jwtAxios.get(path);
    return res.data
}

export const getModelList = async (brandIdx) => {
    const path = `${ url }/vehicles/model?brandIdx=${ brandIdx }`;
    const res = await jwtAxios.get(path);
    return res.data
}

export const getTrimList = async (modelIdx) => {
    const path = `${ url }/vehicles/trim?modelIdx=${ modelIdx }`;
    const res = await jwtAxios.get(path);
    return res.data
}

export const saveVehicle = async (numberPlate, kilometers, trimIdx) => {
    const path = `${ url }/vehicles`;
    const data = {
        numberPlate,
        kilometers,
        trim: { idx: trimIdx }
    };

    const res = await jwtAxios.post(path, data);
    return res.data
}
