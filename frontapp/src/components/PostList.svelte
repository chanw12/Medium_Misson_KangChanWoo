<script>

    import {onMount} from "svelte";
    import axios from "axios";
    import {getCookie} from "../util/getCookie.ts";
    import { page } from '$app/stores';
    let pageNum;
    $effect(() => {
        const pageQuery = $page.url.searchParams.get('page');
        pageNum = pageQuery ? parseInt(pageQuery) : 0;
        fetchHomeList();
    });

    let postList = $state([]);
    let currentPage = $state()
    let totalPages = $state()
    let p = 0;


    const fetchHomeList = async () => {
        try {
            const token = getCookie('accessJwtToken')
            const response = await axios.get(`http://localhost:8090/api/post/list?page=${pageNum}`,{
                headers: {
                Authorization: `Bearer ${token}`, // JWT 토큰을 헤더에 추가
            },
        })
            console.log(response.data)
            postList = response.data.content;
            console.log(postList);
            currentPage = response.data.pageable.pageNumber+1;
            totalPages = response.data.totalPages;

        } catch (error) {
            console.error('Error fetching user information:', error);
        }
    };

</script>
<div class="flex-row ">
    <div class="absolute top-20 right-8">
        <a href="http://localhost:5173/post/write" class="btn btn-primary">글 작성</a>
    </div>
    <div class="overflow-x-auto">


        <div class="flex flex-wrap gap-20 justify-center">

            <!-- 첫 번째 카드 -->
            {#each postList as item}
                <div class="card w-96 bg-base-100 shadow-xl">
                    <figure><img src="https://daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.jpg" alt="Shoes" /></figure>
                    <div class="card-body">
                        <h2 class="card-title">{item.title}</h2>
                        <p>여기에 간단한 설명 넣기</p>
                        <div class="card-actions justify-between pt-10">
                            <div class="items-end">
                                <p class="text-sm opacity-50">작성자:{item.author.username}</p>
                            </div>
                            <a href="http://localhost:5173/post/{item.id}" class="btn btn-primary">자세히 보기</a>
                        </div>
                    </div>
                </div>
            {/each}
        </div>
        <div class="pagination-controls ">
            <div class="flex justify-center mt-20 items-center gap-6">
            {#if currentPage > 1}
                <button class="btn" on:click={()=>{location.href=`list?page=${pageNum-1}`}}>이전</button>
            {/if}

            <span>페이지 {currentPage} / {totalPages}</span>

            {#if currentPage < totalPages}
                <button class="btn" on:click={()=>{location.href=`list?page=${pageNum+1}`}}>다음</button>
            {/if}
            </div>
        </div>


    </div>
</div>