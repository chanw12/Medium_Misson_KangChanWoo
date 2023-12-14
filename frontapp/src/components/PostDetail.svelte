

<script>
    import {  onMount} from "svelte";
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
    let editingCommentId;
    let votenum

    onMount(()=>{
        fetchPostData();
        fetchCommentData();
    })

    const handleEditClick = (id) => {
        editingCommentId = id;
    };

    const fetchAddVote = async (id) => {

        try {
            const token = getCookie('accessJwtToken')
            const Response = await axios.post(`http://localhost:8090/api/vote/${data.id}`,{
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                }).then(res=>{
                location.reload();
            });
        } catch (error) {
            console.error('Error fetching information:', error);
        }
    };

    const fetchModiComment = async (id,body) => {

        try {
            const token = getCookie('accessJwtToken')
            const Response = await axios.post(`http://localhost:8090/api/comment/modify/${id}`,{
                    body
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    }
                }).then(res=>{

                    fetchCommentData();
                    location.reload();
            });
        } catch (error) {
            console.error('Error fetching information:', error);
        }
    };

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
    const fetchDeleteComment = async (event) => {
        const id = event.currentTarget.dataset.data;
            const token = getCookie('accessJwtToken')
            const Response = await axios.delete(`http://localhost:8090/api/comment/delete/${id}`,{
                headers: {
                    Authorization: `Bearer ${token}`, // JWT 토큰을 헤더에 추가
                },
            }).then(()=>{
                fetchCommentData()
                location.reload()
                }
            );

    };


    const fetchPostData = async () => {
        try {
            const token = getCookie('accessJwtToken')
            const Response = await axios.get(`http://localhost:8090/api/post/${data.id}`,{
                headers: {
                    Authorization: `Bearer ${token}`, // JWT 토큰을 헤더에 추가
                },
            });
            console.log(Response);
            title = Response.data.title;
            author = Response.data.author.username;
            body =Response.data.body;
            votenum = Response.data.voter.length
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
            }).then(()=>{
            fetchCommentData()
            location.reload()
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
    <div class="flex justify-center mt-20">
        <button class="btn bg-gray-500 btn-outline flex-col" on:click={fetchAddVote}>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M6.633 10.5c.806 0 1.533-.446 2.031-1.08a9.041 9.041 0 012.861-2.4c.723-.384 1.35-.956 1.653-1.715a4.498 4.498 0 00.322-1.672V3a.75.75 0 01.75-.75A2.25 2.25 0 0116.5 4.5c0 1.152-.26 2.243-.723 3.218-.266.558.107 1.282.725 1.282h3.126c1.026 0 1.945.694 2.054 1.715.045.422.068.85.068 1.285a11.95 11.95 0 01-2.649 7.521c-.388.482-.987.729-1.605.729H13.48c-.483 0-.964-.078-1.423-.23l-3.114-1.04a4.501 4.501 0 00-1.423-.23H5.904M14.25 9h2.25M5.904 18.75c.083.205.173.405.27.602.197.4-.078.898-.523.898h-.908c-.889 0-1.713-.518-1.972-1.368a12 12 0 01-.521-3.507c0-1.553.295-3.036.831-4.398C3.387 10.203 4.167 9.75 5 9.75h1.053c.472 0 .745.556.5.96a8.958 8.958 0 00-1.302 4.665c0 1.194.232 2.333.654 3.375z" />
            </svg>
            <span>{votenum}</span>

        </button>
    </div>
    <div class="border-t mb-8 mt-10"></div>
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
                            <button class="text-xs" on:click={() => handleEditClick(comment.id)} >수정</button>
                            <p>/</p>
                            <button class="text-xs" on:click={fetchDeleteComment}  data-data="{comment.id}">삭제</button>
                        </div>
                    {/if}
                </div>
                <!-- 댓글 내용이 들어가는 부분 -->
            </div>
            {#if (editingCommentId === comment.id)}
                <div class="flex justify-center items-center">
                <textarea  bind:value={comment.body}
                           class="w-full px-3 py-2 h-20 border rounded-md focus:outline-none focus:border-blue-500 mt-4 mx-2"
                           rows="4"></textarea>
                <button class="btn btn-primary" on:click={()=>fetchModiComment(comment.id,comment.body) }>저장</button>
                </div>
            {/if}
            <!-- 다른 댓글들도 유사한 방식으로 표시 -->
        </div>
    {/each}

</div>
