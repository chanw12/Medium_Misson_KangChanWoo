import axios from "axios";
import {getCookie} from "./getCookie";


export async function getUserInfo(){
    const token = getCookie('accessJwtToken')
    const userResponse = await axios.get('http://localhost:8090/api/member/user', {
        headers: {
            Authorization: `Bearer ${token}`, // JWT 토큰을 헤더에 추가
        },
    });
    return userResponse;
}