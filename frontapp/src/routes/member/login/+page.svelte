
<script>
    import axios from "axios";
    import {getCookie} from "../../../util/getCookie.ts";
    import {usernameStore} from "$lib/stores/store.js";

    import {sineOut} from "svelte/easing";


    let username = $state('')
    let password = $state('')
    let errorMsg = $state('')
    let accessToken = $state('')
    let RefreshToken = $state('')


    function hideErrorMessage() {
        errorMsg = ""; // 에러 메시지 비우기
    }

    setTimeout(hideErrorMessage, 5000);



    async function fetchLogin(){
        const res = await axios.post('http://localhost:8090/api/login', {
            username,
            password,
        }, {
            withCredentials: true
        })
            .then(res =>{
                console.log(res)

                location.href="/"
            })
            .catch(error =>{
                errorMsg = error.response.data.msg;
            })

    }
    $effect(()=>{
        if($usernameStore != null){
            location.href ="/";
        }
    })


</script>

<svelte:head>
    <title>Test1</title>
    <meta name="description" content="테스트" />
</svelte:head>

<button class="absolute top-20 left-4 btn btn-ghost" onclick={() => window.history.back()}>
    <svg class="h-6 w-6 fill-current md:h-8 md:w-8" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24"><path d="M15.41,16.58L10.83,12L15.41,7.41L14,6L8,12L14,18L15.41,16.58Z"></path></svg>

</button>
<div class="flex-col items-center min-h-screen justify-center">
    <div class="text-4xl font-bold mb-6 text-center">Login</div>
    {#if errorMsg !== ''}
        <div role="alert" class="alert alert-warning flex items-center mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6 mr-2" fill="none" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
            </svg>
            <span>{errorMsg}</span>
        </div>
    {/if}
    <form class="p-5 flex flex-col items-center space-y-4" on:submit|preventDefault={fetchLogin}>
        <input bind:value={username} type="text" placeholder="ID" class="input input-bordered input-sm w-full max-w-xs" />
        <input bind:value={password} id="password" type="password" placeholder="Password" class="input input-bordered input-sm w-full max-w-xs" />
        <button type="submit" class="btn btn-primary w-full max-w-xs">로그인</button>
    </form>
    <div class="flex space-x-4 items-center justify-center">
        <a href="/member/join" class="btn btn-neutral w-full max-w-xs">회원가입</a>
    </div>
    <hr class="my-4 border-t border-gray-300 w-full max-w-sm mx-auto" />

    <div class="flex space-x-4 items-center justify-center">
        <a href="http://localhost:8090/member/socialLogin?redirectUrl=http://localhost:5173">
            <img class="items-center justify-center" src="https://ghuazvmjviqg21655229.cdn.ntruss.com/prjimg/kakao_login_medium_wide.png" height="45" width="300"/>
        </a>
    </div>

</div>
