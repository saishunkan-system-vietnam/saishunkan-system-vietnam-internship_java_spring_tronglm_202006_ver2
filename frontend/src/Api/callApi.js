import axios from "axios"
export async function callApi(method, endpoint, data = null, params = null) {
    return await axios({
        url: endpoint,
        method: method,
        baseURL: "http://localhost:8081",
        data: data,
        params:params,
        withCredentials: true
    }).then(res => {
        console.log(res);
        return res;
    }).catch(error => {
        console.log(error);
        return {
            "code": 500,
            "message": 'Failed to connect to server. Please try again later.',
            "payload": []
        }
    });
}
