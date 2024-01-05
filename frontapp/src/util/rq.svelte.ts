import axios from "axios";
import {getCookie} from "./getCookie";
import type {components, paths} from "$lib/types/api/v1/schema";
import createClient from "openapi-fetch";


class RqSvelte {

    public member: components['schemas']['MemberDto']

    constructor() {
        let id = $state(0);
        let username = $state('');
        let createDate = $state('');
        let modifyDate = $state('');
        let authorities: string[] = $state([]);
        let paid = $state(false);

        this.member = {
            get id() {
                return id;
            },
            set id(value: number) {
                id = value;
            },
            get createDate() {
                return createDate;
            },
            set createDate(value: string) {
                createDate = value;
            },
            get modifyDate() {
                return modifyDate;
            },
            set modifyDate(value: string) {
                modifyDate = value;
            },
            get username() {
                return username;
            },
            set username(value: string) {
                username = value;
            },
            get authorities() {
                return authorities;
            },
            set authorities(value: string[]) {
                authorities = value;
            },
            get paid(){
                return paid;
            },
            set paid(value: boolean){
                paid = value;
            }

        };
    }
    public apiEndPointsWithFetch(fetch: any) {
        return createClient<paths>({
            baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
            credentials: 'include',
            fetch
        });
    }

    public apiEndPoints() {
        return createClient<paths>({
            baseUrl: import.meta.env.VITE_CORE_API_BASE_URL,
            credentials: 'include'
        });
    }
    public isLogin() {
        return this.member.id !== 0;
    }

    public async initAuth() {
        console.log('love')
        const accessToken = getCookie('accessToken');
        const refreshToken = getCookie('RefreshToken')
        const { data } = await axios.get('http://localhost:8090/api/member/user', {
            headers: {
                Authorization: `Bearer ${refreshToken}#${accessToken}`, // JWT 토큰을 헤더에 추가
                withCredentials: true
            },
        });

        if (data) {
            this.setLogined(data);
        }
    }

    public isLogout() {
        return !this.isLogin();
    }
    public setLogined(member: components['schemas']['MemberDto']) {
        this.member.id = member.id;
        this.member.createDate = member.createDate;
        this.member.modifyDate = member.modifyDate;
        this.member.username = member.username;
        this.member.authorities = member.authorities;
    }
    public setLogout() {
        this.member.id = 0;
        this.member.createDate = '';
        this.member.modifyDate = '';
        this.member.username = '';
        this.member.authorities = [];

    }
    public async logout() {
        const accessToken = getCookie('accessToken');
        const refreshToken = getCookie('RefreshToken')
        const {} = await axios.post('http://localhost:8090/api/logout',
            {
                withCredentials: true,
                headers: {
                    Authorization: `Bearer ${refreshToken}#${accessToken}`,
                },


            })
        document.cookie = 'accessToken' + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        document.cookie = 'RefreshToken' + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';

        this.setLogout();

    }

}

const rq = new RqSvelte();
export default rq;
