
<script>
    import {onMount} from "svelte";
    import { fade, slide } from 'svelte/transition';
    import {usernameStore} from "$lib/stores/store.js";
    import axios from "axios";
    import {getCookie} from "../util/getCookie.ts";
    import { searchKeyword,selectedSorting,selectedCategory } from "$lib/stores/store.js";
    import rq from "../util/rq.svelte.ts";


    let username = $state(null)
    let showSearch = $state(false);

    const backUrl = import.meta.env.VITE_BACK_API_URL;
    const frontUrl = import.meta.env.VITE_FRONT_URL;
    onMount(()=>{
        rq.initAuth();
    })




    async function fetchLogout() {
        rq.logout();

    }
    function toggleSearch(){
        showSearch = !showSearch;
    }

    async function handleSearch() {
        const searchQuery = document.getElementById('searchQuery').value;
        $searchKeyword = searchQuery
        const res = await axios.get(`${backUrl}/api/post/list?kw=${searchQuery}&sortCode=${$selectedSorting}&kwType=${$selectedCategory}`);
        location.href=`/post/list?kw=${searchQuery}&kwType=${$selectedCategory}&sortCode=${$selectedSorting}`
    }
</script>



<div class="navbar bg-base-100 mb-20">
    <div class="navbar-start">
        <div class="dropdown">
            <div tabindex="0" role="button" class="btn btn-ghost btn-circle">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h7" /></svg>
            </div>
            <ul tabindex="0" class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52">

                {#if rq.isLogin()}
                <li><a>유저 &nbsp : &nbsp  {rq.member.username}</a></li>
                    <div class="w-full h-0.5 bg-[#ccc]"></div>

                <li><a href="/post/myList">내 글 목록</a></li>
                <li><button class="items-start" on:click={fetchLogout}>로그아웃</button></li>
                {/if}
                <li><a href="/post/list">전체 글 목록</a></li>
                {#if rq.isLogout()}
                    <li><a href="/member/login">로그인</a></li>
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
        {#if !showSearch}
        <button class="btn btn-ghost btn-circle">
            <svg xmlns="http://www.w3.org/2000/svg" on:click={toggleSearch} class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" /></svg>
        </button>
        {/if}
        {#if showSearch}
            <div class="search-box flex items-center gap-2 " out:fade={{ duration: 300 }} in:slide={{ duration: 300 }}>
                <select class="select select-bordered w-1/3 max-w-xs" bind:value={$selectedCategory}>
                    <option value="title,body,author">전체</option>
                    <option value="title">제목</option>
                    <option value="content">내용</option>
                    <option value="author">작성자</option>
                    <option value="title,body">제목 + 내용</option>
                    <option value="title,author">제목 + 작성자</option>
                    <option value="body,author">내용 + 작성자</option>

                </select>
                <select class="select select-bordered w-1/3 max-w-xs" bind:value={$selectedSorting}>
                    <option value="createDesc">생성일 ▼</option>
                    <option value="createAsc">생성일 ▲</option>
                    <option value="viewCountDesc">조회수 ▼</option>
                    <option value="viewCountAsc">조회수 ▲</option>

                </select>
                <input type="text" id="searchQuery" placeholder="Type here" class="input input-bordered w-full" />
                <button class="btn btn-primary" on:click={handleSearch}>검색</button>
                <button class="btn" on:click={toggleSearch}>닫기</button>
            </div>
        {/if}
        <button class="btn btn-ghost btn-circle">
            <div class="indicator">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" /></svg>
                <span class="badge badge-xs badge-primary indicator-item"></span>
            </div>
        </button>
    </div>
</div>

