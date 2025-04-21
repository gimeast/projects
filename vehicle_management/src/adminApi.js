import jwtAxios from "./custom/jwtAxios.js";
import API_BASE_URL from "./config/apiConfig.js";


const url = API_BASE_URL

export const getSpecList = async (search, page) => {
    const path = url + `/admin/vehicles?search=${ search }&page=${ page }`
    const res = await jwtAxios.get(path);
    return res.data
}

export const saveSpec = async (brandName, modelName, drivetrain, fuelType, transmission) => {
    const path = url + `/admin/vehicles`

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


