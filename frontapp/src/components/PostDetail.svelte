

<script>
    import {onMount} from "svelte";
    import {usernameStore} from "$lib/stores/store.js";

    export let data;
    import axios from "axios";
    import {getCookie} from "../util/getCookie.ts";
    let author;
    let title;
    let body;
    onMount(()=>{
        fetchPostData();
    })
    const fetchPostData = async () => {
        try {
            const token = getCookie('accessJwtToken')
            const Response = await axios.get(`http://localhost:8090/api/post/${data.id}`,{
                headers: {
                    Authorization: `Bearer ${token}`, // JWT 토큰을 헤더에 추가
                },
            });
            title = Response.data.title;
            author = Response.data.author.username;
            body =Response.data.body;
        } catch (error) {
            console.error('Error fetching information:', error);
        }
    };
    async function deletePost(){
        const token = getCookie('accessJwtToken')
        console.log(token);
        const res = await axios.delete(`http://localhost:8090/api/post/${data.id}/delete`,
        {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        });
        location.href="/"
    }

</script>

<div class="max-w-4xl mx-auto my-8">

    <h1 class="text-3xl font-bold mb-4 ">{title}</h1>
    <div class="justify-between flex items-center mt-10 mb-20">
        <p class="text-gray-600 mb-2">작성자: {author}</p>
        {#if ($usernameStore == author)}
            <div class="mb-5 mx-2">
                <a href="http://localhost:5173/post/{data.id}/modify" class="btn btn-sm btn-primary ">글 수정</a>
                <a on:click={deletePost}  class="btn btn-sm btn-error ">글 삭제</a>
            </div>
        {/if}
    </div>
    <p class="w-auto break-all">{body}</p>

</div>
