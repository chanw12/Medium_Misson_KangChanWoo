
<script>
    import {onMount} from "svelte";
    import {usernameStore} from "$lib/stores/store.js";
    import axios from "axios";
    import {getCookie} from "../util/getCookie.ts";

    let username = $state(null)
    onMount(()=>{
        fetchUserData();
    })
    const fetchUserData = async () => {
        try {
            // JWT 토큰을 쿠키에서 가져오기
            const token = getCookie('accessJwtToken')

            // 유저 정보를 가져오기 위한 요청
            const userResponse = await axios.get('http://localhost:8090/api/member/user', {
                headers: {
                    Authorization: `Bearer ${token}`, // JWT 토큰을 헤더에 추가
                },
            });
            username = userResponse.data.username;
            $usernameStore = username;
        } catch (error) {
            console.error('Error fetching user information:', error);
        }
    };
    async function fetchLogout() {
        const token = getCookie('accessJwtToken');

        const res = await axios.post('http://localhost:8090/api/logout',
            {},
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            .then(()=>{
                location.reload();
            })
    }
</script>



<div class="navbar bg-base-100 mb-20">
    <div class="navbar-start">
        <div class="dropdown">
            <div tabindex="0" role="button" class="btn btn-ghost btn-circle">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h7" /></svg>
            </div>
            <ul tabindex="0" class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52">

                {#if username != null}
                <li><a>유저 &nbsp : &nbsp  {username}</a></li>
                    <div class="w-full h-0.5 bg-[#ccc]"></div>

                <li><a href="http://localhost:5173/post/myList">내 글 목록</a></li>
                <li><button class="items-start" on:click={fetchLogout}>로그아웃</button></li>
                {/if}
                <li><a href="http://localhost:5173/post/list">전체 글 목록</a></li>
                {#if username == null}
                    <li><a href="http://localhost:5173/member/login">로그인</a></li>
                {/if}
            </ul>
            <ul class="flex space-x-4">

            </ul>
        </div>
    </div>
    <div class="navbar-center">
        <a class="btn btn-ghost text-xl">Blog</a>
    </div>
    <div class="navbar-end">
        <button class="btn btn-ghost btn-circle">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" /></svg>
        </button>
        <button class="btn btn-ghost btn-circle">
            <div class="indicator">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" /></svg>
                <span class="badge badge-xs badge-primary indicator-item"></span>
            </div>
        </button>
    </div>
</div>
