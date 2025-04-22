import jwtAxios from "./custom/jwtAxios.js";
import API_BASE_URL from "./config/apiConfig.js";


const url = API_BASE_URL

export const getSpecList = async (search, page) => {
    const path = `${ url }/admin/vehicles?search=${ search }&page=${ page }`;
    const res = await jwtAxios.get(path);
    return res.data
}

export const saveSpec = async (brandName, modelName, drivetrain, fuelType, transmission) => {
    const path = `${ url }/admin/vehicles`;

    const data = {
        "brandDTO": {
            "name": brandName
        },
        "modelDTO": {
            "name": modelName
        },
        "trimDTO": {
            "drivetrain": drivetrain,
            "fuelType": fuelType,
            "transmission": transmission
        }
    }

    const res = await jwtAxios.post(path, data);
    return res.data
}

export const deleteSpecList = async (trimIdxs) => {
    const path = `${ url }/admin/vehicles/delete`;

    const res = await jwtAxios.post(path, trimIdxs);
    return res.data;
}

export const getTrimPartsList = async (trimIdx) => {
    const path = `${ url }/admin/vehicles/trim/parts?trimIdx=${ trimIdx }`;

    const res = await jwtAxios.get(path);
    return res.data;
}

export const saveParts = async (name) => {
    const path = `${ url }/admin/vehicles/parts?name=${ name }`;

    const res = await jwtAxios.post(path);
    return res.data;
}

export const getPartsList = async () => {
    const path = `${ url }/admin/vehicles/parts`;

    const res = await jwtAxios.get(path);
    return res.data;
}

export const saveTrimPartsList = async (dataList) => {
    const path = `${ url }/admin/vehicles/trim/parts`;

    const res = await jwtAxios.post(path, dataList);
    return res.data;
}


export const deleteTrimParts = async (trimPartsIdx) => {
    const path = `${ url }/admin/vehicles/trim/parts?trimPartsIdx=${ trimPartsIdx }`;

    const res = await jwtAxios.delete(path);
    return res.data;
}


