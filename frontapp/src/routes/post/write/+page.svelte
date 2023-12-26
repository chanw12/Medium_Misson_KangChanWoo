<script>
    import axios from "axios";
    import { onMount } from 'svelte';
    import {getCookie} from "../../../util/getCookie.ts";
    import {usernameStore} from "$lib/stores/store.js";

    let title = $state('')
    let body = $state('')
    let published = $state('')
    let cookieValue = $state('')

    $effect(()=> {
        cookieValue = getCookie("accessJwtToken")

        if ($usernameStore == null) {
            location.href = "/member/login";
        }
        }
    )


    async function fetchWrite() {
        const res = await axios.post('http://localhost:8090/api/post/write',
        {
            title,body,published
        },
        {
            headers: {
            Authorization: `Bearer ${cookieValue}`
        }
        })
            .then(res=>{
                location.href=`/post/${res.data.id}`
            })
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

    <div class="mb-4">
        <label class="flex items-center">
            <input type="checkbox" class="mr-2" bind:checked={published} />
            <span class="text-sm">공개 여부</span>
        </label>
    </div>

    <button type="submit" class="bg-blue-500 text-white p-2 rounded-md hover:bg-blue-700">
        저장
    </button>
</form>
