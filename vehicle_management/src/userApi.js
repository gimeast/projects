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

export const deleteVehicle = async (memberVehicleIdx) => {
    const path = `${ url }/vehicles/${ memberVehicleIdx }`;

    const res = await jwtAxios.delete(path);
    return res.data
}

export const getMaintenanceList = async (memberVehicleIdx, search, page, size) => {
    const path = `${ url }/vehicles/maintenance?memberVehicleIdx=${ memberVehicleIdx }&search=${ search }&page=${ page }&size=${ size }`;
    const res = await jwtAxios.get(path);
    return res.data
}

export const saveMaintenance = async (data) => {
    const path = `${ url }/vehicles/maintenance`;
    const res = await jwtAxios.post(path, data);
    return res.data
}

export const editMaintenance = async (data) => {
    const path = `${ url }/vehicles/maintenance`;
    const res = await jwtAxios.put(path, data);
    return res.data
}

export const deleteMaintenance = async (idx) => {
    const path = `${ url }/vehicles/maintenance/${ idx }`;
    const res = await jwtAxios.delete(path);
    return res.data
}

export const getPartsList = async (memberVehicleIdx) => {
    const path = `${ url }/vehicles/parts?memberVehicleIdx=${ memberVehicleIdx }`;
    const res = await jwtAxios.get(path);
    return res.data
}
