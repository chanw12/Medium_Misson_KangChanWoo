<script>
    import axios from "axios";
    import { onMount } from 'svelte';
    import {getCookie} from "../../../util/getCookie.ts";
    import {usernameStore} from "$lib/stores/store.js";

    let title = $state('')
    let body = $state('')
    let published = $state(false)
    let cookieValue = $state('')
    let paid = $state(false);
    let file = $state();
    $effect(()=> {
        cookieValue = getCookie("accessJwtToken")

        if ($usernameStore == null) {
            location.href = "/member/login";
        }
        }
    )

    function handleFileSelection(event) {
        file = event.target.files[0]; // file은 상태 변수
    }


    async function fetchWrite() {
        const formData = new FormData();

        // 입력된 글 데이터 추가
        formData.append('title', title);
        formData.append('body', body);
        formData.append('published', published);
        formData.append('paid', paid);

        if(file){
            formData.append('file',file);
        }

        try{
            const res = await axios.post('http://localhost:8090/api/post/write',formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'Authorization' : `Bearer ${cookieValue}`
                    }
                })
                .then(res=>{
                    console.log('Post and file uploaded successfully:', res.data);
                    location.href=`/post/${res.data.id}`
                })

        }catch (error){
            console.error('Error uploading post and file:', error);
        }


    }


</script>

<svelte:head>
    <title>Write</title>
    <meta name="description" content="글 작성" />
</svelte:head>

<form on:submit|preventDefault={fetchWrite} class="max-w-2xl mx-auto mt-8 p-4 bg-gray-900 shadow-md rounded-md">
    <div class="mb-4">
        <label for="title" class="block text-gray-700 text-sm font-bold mb-2">제목</label>
        <input type="text" id="title" class="w-full p-2 border rounded-md" bind:value={title} />
    </div>

    <div class="mb-4">
        <label for="body" class="block text-gray-700 text-sm font-bold mb-2">내용</label>
        <textarea id="body" class="w-full p-2 border rounded-md textarea-lg h-96" bind:value={body}></textarea>
    </div>
    <input type="file" on:change={handleFileSelection}>
    <div class="mb-4">
        <label class="flex items-center">
            <input type="checkbox" class="mr-2" bind:checked={published} />
            <span class="text-sm">공개 여부</span>
        </label>
    </div>
    <div class="mb-4">
        <label class="flex items-center">
            <input type="checkbox" class="mr-2" bind:checked={paid} />
            <span class="text-sm">유료 멤버쉽</span>
        </label>
    </div>

    <button type="submit" class="bg-blue-500 text-white p-2 rounded-md hover:bg-blue-700">
        저장
    </button>
</form>
