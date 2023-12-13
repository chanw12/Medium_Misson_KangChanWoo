

<script>
    import {beforeUpdate, onMount} from "svelte";
    import {usernameStore} from "$lib/stores/store.js";

    export let data;
    import axios from "axios";
    import {getCookie} from "../util/getCookie.ts";
    let author;
    let title;
    let body;
    let post_id = data.id;
    let commentbody
    let commentlist = ([]);

    onMount(()=>{
        fetchPostData();
        fetchCommentData();
    })


    const fetchCommentData = async () => {
        try {
            const token = getCookie('accessJwtToken')
            const Response = await axios.get(`http://localhost:8090/api/comment/get/${data.id}`,{
                headers: {
                    Authorization: `Bearer ${token}`, // JWT 토큰을 헤더에 추가
                },
            }).then(res=>{
                console.log(res.data)
                commentlist = res.data;
            });
        } catch (error) {
            console.error('Error fetching information:', error);
        }
    };


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
        const res = await axios.delete(`http://localhost:8090/api/post/${data.id}/delete`,
        {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        });
        location.href="/"
    }

    async function writeComment(){
        const token = getCookie('accessJwtToken')
        const res = await axios.post(`http://localhost:8090/api/comment/write`,
            {
                body:commentbody,username:author,post_id
            },
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            });
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

    <div class="border-t mb-8 mt-20"></div>
    <div>
    <h1>Comment</h1>

    <div class="mt-8 flex gap-2 items-center">
        <textarea id="commentbody" class="w-full px-3 py-2 border rounded-md focus:outline-none focus:border-blue-500" rows="4" placeholder="댓글을 입력하세요..." bind:value={commentbody}></textarea>
        <button on:click={writeComment} class="mt-2 btn btn-primary ">댓글 등록</button>
    </div>
    </div>

    <div class="border-t my-8"></div>


    <div>  댓글</div>
    {#each commentlist as comment}

        <div class="mt-8">
            <div class="border rounded-md p-4">
                <div class="flex items-center justify-between mb-2">
                    <div class="flex items-center">
                        <span class="font-bold mr-2">{comment.author.username}:</span>
                        <span class="text-gray-600">{comment.body}</span>
                    </div>
                    {#if ($usernameStore == comment.author.username)}
                        <div class="flex gap-2">
                            <button class="text-xs">수정</button>
                            <p>/</p>
                            <button class="text-xs">삭제</button>
                        </div>
                    {/if}
                </div>
                <!-- 댓글 내용이 들어가는 부분 -->
            </div>
            <!-- 다른 댓글들도 유사한 방식으로 표시 -->
        </div>
    {/each}

</div>
