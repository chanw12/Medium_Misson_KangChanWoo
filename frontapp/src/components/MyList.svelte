<script>

    import {onMount} from "svelte";
    import axios from "axios";
    import {getCookie} from "../util/getCookie.ts";
    import rq from "../util/rq.svelte.ts";

    onMount(()=>{
        fetchHomeList();
    })
    let postList = $state([]);
    let errorMsg = $state("");

    function hideErrorMessage() {
        setTimeout(function() {
            var errorAlert = document.getElementById('errorAlert');
            errorMsg = "";
        }, 5000);
    }

    const handlePostClick = async (item,event)=>{
        if(item.paid){
            const userResponse = rq.member;
            const isPaidUser = userResponse.paid;
            console.log(rq.member.username)
            console.log(item.author.username);
            if(item.author.username != rq.member.username&& !isPaidUser){
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
            const accessToken = getCookie('accessToken');
            const refreshToken = getCookie('RefreshToken')

            const response = await axios.get('http://localhost:8090/api/post/myList',{
                headers: {
                    Authorization: `Bearer ${refreshToken}#${accessToken}`
                }
            })
            postList = response.data.content;
        } catch (error) {
            console.error('Error fetching user information:', error);
        }
    };

</script>
{#if errorMsg}
<div id="errorAlert" role="alert" class="alert alert-error w-1/4 fixed top-10 right-10 z-50">
    <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" /></svg>
    <span>{errorMsg}</span>
</div>
    {/if}
<div class="flex-row">
    <div class="absolute top-20 right-8">
        <a href="http://localhost:5173/post/write" class="btn btn-primary">글 작성</a>
    </div>
    <div class="overflow-x-auto">


        <div class="flex flex-wrap gap-20 justify-center">

            <!-- 첫 번째 카드 -->
            {#each postList as item}
                <div class="card w-96 bg-base-100  shadow-xl border border-gray-800 rounded-lg">
                    {#if item.imgUrl == null}
                        <figure><img src="https://daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.jpg" alt="Shoes" /></figure>
                    {:else }
                    <figure><img src="https://{item.imgUrl}" class="w-56 h-56"/></figure>
                    {/if}
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
                            <a on:click|preventDefault="{(event) => handlePostClick(item, event)}" class="btn btn-primary">자세히 보기</a>
                        </div>
                    </div>
                </div>
            {/each}
        </div>


    </div>
</div>