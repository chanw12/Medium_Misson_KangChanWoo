<script>
    import axios from "axios";
    import { onMount } from 'svelte';


    let title = $state('')
    let body = $state('')
    let isPublished = $state('')
    let cookieValue = $state('')
    onMount(() => {
        // 쿠키 읽기 함수 호출
        cookieValue = getCookie('accessJwtToken');
        console.log(cookieValue)
    });

    async function fetchHelo1(){
        const res = await axios.get('http://localhost:8090/api/member/user',{
            headers: {
                Authorization: `Bearer ${cookieValue}`
            }
        })
            .then(res =>{
                console.log(res.data);
            })


    }

    async function fetchWrite() {
        const res = await axios.post('http://localhost:8090/api/post/write',
        {
            title,body,isPublished
        },
        {
            headers: {
            Authorization: `Bearer ${cookieValue}}`
        }
        })
    }

    function getCookie(name) {
        const decodedCookie = decodeURIComponent(document.cookie);
        const cookies = decodedCookie.split(';');
        for (let i = 0; i < cookies.length; i++) {
            let cookie = cookies[i];
            while (cookie.charAt(0) === ' ') {
                cookie = cookie.substring(1);
            }
            if (cookie.indexOf(name + '=') === 0) {
                return cookie.substring(name.length + 1, cookie.length);
            }
        }
        return '';
    }



</script>

<svelte:head>
    <title>Write</title>
    <meta name="description" content="글 작성" />
</svelte:head>

<div>
    <form class="p-5" on:submit|preventDefault={fetchWrite}>
        <input bind:value={title} type="text" class="input input-bordered input-sm w-full max-w-xs" />
        <textarea bind:value={body}/>
        <input type="checkbox" bind:value={isPublished}/>
        <button type="submit">로그인</button>
    </form>

    <form  on:submit|preventDefault={fetchHelo1}>
        <button type="submit">제출1</button>
    </form>
</div>