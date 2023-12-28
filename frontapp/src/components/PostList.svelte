<script>

    import {onMount} from "svelte";
    import axios from "axios";
    import {getCookie} from "../util/getCookie.ts";
    import { page } from '$app/stores';
    import { selectedCategory, selectedSorting,searchKeyword} from "$lib/stores/store.js";
    import {getUserInfo} from "../util/rq.ts";

    let pageNum;
    let category;
    let kw;
    let sort;

    $effect(() => {
        const pageQuery = $page.url.searchParams.get('page');
        const kwQuery= $page.url.searchParams.get('kw');
        const sortQuery= $page.url.searchParams.get('sortCode');
        const categoryQuery= $page.url.searchParams.get('kwType');
        pageNum = pageQuery ? parseInt(pageQuery) : 0;
        kw = kwQuery ? kwQuery: "";
        category = categoryQuery;
        sort = sortQuery;
        fetchHomeList();
    });


    let postList = $state([])
    let currentPage = $state()
    let totalPages = $state()
    let p = 0;
    let errorMsg = $state("");

    function hideErrorMessage() {
        setTimeout(function() {
            var errorAlert = document.getElementById('errorAlert');
            errorMsg = "";
        }, 5000);
    }

    const handlePostClick = async (item,event)=>{
        if(item.paid){
            const userResponse = await getUserInfo();
            const isPaidUser = userResponse.data.paid;

            if(!isPaidUser){
                event.preventDefault();
                errorMsg = "유료 회원만 이글을 볼 수 있습니다"
                hideErrorMessage();
                return;
            }
        }
        window.location.href=`/post/${item.id}`
    }

    const fetchHomeList = async () => {
        try {
            const token = getCookie('accessJwtToken')
            let url = "http://localhost:8090/api/post/list"
            if(pageNum || pageNum === 0){
                url += `?page=${pageNum}`
            }
            if(!kw == ""){
                url += `&kw=${kw}&kwType=${category}&sortCode=${sort}`
            }


            const response = await axios.get(url,{
                headers: {
                Authorization: `Bearer ${token}`, // JWT 토큰을 헤더에 추가
            },
        })
            postList = response.data.content;
            currentPage = response.data.pageable.pageNumber+1;
            totalPages = response.data.totalPages;
        } catch (error) {
            console.error('Error fetching user information:', error);
        }
    };

    function incrementPageParam(url) {
        const urlObj = new URL(url);
        const queryParams = new URLSearchParams(urlObj.search);

        // 'page' 파라미터의 현재 값을 찾아서 1 증가시킵니다.
        let page = parseInt(queryParams.get('page'), 10);
        if (!isNaN(page)) {
            queryParams.set('page', page + 1);
        } else {
            // 'page' 파라미터가 없거나 유효하지 않은 경우, 'page'를 1로 설정합니다.
            queryParams.set('page', 1);
        }

        // 변경된 쿼리 스트링을 원래 URL에 다시 연결합니다.
        return urlObj.origin + urlObj.pathname + '?' + queryParams.toString();
    }

    function decrementPageParam(url) {
        const urlObj = new URL(url);
        const queryParams = new URLSearchParams(urlObj.search);

        // 'page' 파라미터의 현재 값을 찾아서 1 감소시킵니다.
        let page = parseInt(queryParams.get('page'), 10);
        if (!isNaN(page) && page > 0) {
            queryParams.set('page', page - 1);
        } else {
            // 'page'가 0이하이거나 유효하지 않은 경우, 'page'를 0으로 설정합니다.
            queryParams.set('page', 0);
        }

        // 변경된 쿼리 스트링을 원래 URL에 다시 연결합니다.
        return urlObj.origin + urlObj.pathname + '?' + queryParams.toString();
    }

</script>
{#if errorMsg}
    <div id="errorAlert" role="alert" class="alert alert-error w-1/4 fixed top-10 right-10 z-50">
        <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
        <span>{errorMsg}</span>
    </div>
{/if}
<div class="flex-row ">
    <div class="absolute top-20 right-8">
        <a href="http://localhost:5173/post/write" class="btn btn-primary">글 작성</a>
    </div>
    <div class="overflow-x-auto">


        <div class="flex flex-wrap gap-20 justify-center">

            <!-- 첫 번째 카드 -->
            {#each postList as item}
                <div class="card w-96 bg-base-100 shadow-xl">
                    <figure><img src="https://{item.imgUrl}" class="w-56 h-56"/></figure>
                    <div class="card-body">
                        <div class="flex justify-between items-center">
                            <h2 class="card-title">{item.title}</h2>
                            {#if item.paid}
                                <div class="badge badge-primary">유료</div>
                            {/if}

                        </div>
                        <p>여기에 간단한 설명 넣기</p>
                        <div class="card-actions justify-between pt-10">
                            <div class="items-end">
                                <p class="text-sm opacity-50">작성자:{item.author.username}</p>
                            </div>
                            <button on:click|preventDefault="{(event) => handlePostClick(item, event)}" class="btn btn-primary">자세히 보기</button>
                        </div>
                    </div>
                </div>
            {/each}
        </div>
        <div class="pagination-controls ">
            <div class="flex justify-center mt-20 items-center gap-6">
            {#if currentPage > 1}
                <button class="btn" on:click={()=>{location.href=decrementPageParam($page.url)}}>이전</button>
            {/if}

            <span>페이지 {currentPage} / {totalPages}</span>

            {#if currentPage < totalPages}
                <button class="btn" on:click={()=>{location.href=incrementPageParam($page.url)}}>다음</button>
            {/if}
            </div>
        </div>


    </div>
</div>