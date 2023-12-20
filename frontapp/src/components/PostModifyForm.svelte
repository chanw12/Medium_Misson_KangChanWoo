<script>
    import {onMount} from "svelte";
    import {getCookie} from "../util/getCookie.ts";
    import axios from "axios";
    import {navigate} from "svelte-routing";
    export let data;

    let published;
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
            })
            title = Response.data.title;
            body =Response.data.body;
            published = Response.data.published;
        } catch (error) {
            console.error('Error fetching information:', error);
        }
    };

    async function handleSubmit(){
        try{
            const token = getCookie('accessJwtToken')
            const res = await axios.put(`http://localhost:8090/api/post/${data.id}/modify`,
                {
                    title,body,published
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }).then(
                location.href = `/post/${data.id}`
            )

        }catch(error){
            console.error('Error fetching information:', error);
        }



    }
</script>

<form on:submit|preventDefault={handleSubmit} class="max-w-2xl mx-auto mt-8 p-4 bg-gray-900 shadow-md rounded-md">
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



