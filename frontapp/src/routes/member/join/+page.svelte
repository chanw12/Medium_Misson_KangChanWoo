<script>
    import axios from "axios";
    import {onMount} from "svelte";

    let username = $state('')
    let password = $state('')
    let passwordconfirm = $state('')
    let errorMsg = $state([])
    let result = null

    function hideErrorMessage() {
        errorMsg = ""; // 에러 메시지 비우기
    }
    async function fetchJoin(){
        const res = await axios.post('http://localhost:8090/api/member/join',
            {
                username,
                password,
                passwordconfirm
            }
            )
            .then(res =>{
                if(res.status == 200){
                    location.href="/"  //로그인 성공시 메인페이지로 이동
                }
            })
            .catch(error =>{
                errorMsg = error.response.data;
                console.log(errorMsg)
                setTimeout(hideErrorMessage,3000);
            })
        const json = await res.json()
        result = JSON.stringify(json)

    }


</script>

<svelte:head>
    <title>Test1</title>
    <meta name="description" content="테스트" />
</svelte:head>


<div class="flex-col items-center min-h-screen justify-center">
    <div class="text-4xl font-bold mb-6 text-center">Join</div>
    {#if errorMsg.length >0}
        <div class=" fixed top-32 left-80/4 toast toast-start toast-middle">
            {#each errorMsg as msg}
            <div class="alert alert-error">
                <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" /></svg>
                <span>{msg}</span>
            </div>
            {/each}
        </div>
    {/if}
    <form class="p-5 flex flex-col items-center space-y-4" on:submit|preventDefault={fetchJoin}>
        <input bind:value={username} type="text" placeholder="ID" class="input input-bordered input-sm w-full max-w-xs" />
        <input bind:value={password} id="password" type="password" placeholder="Password" class="input input-bordered input-sm w-full max-w-xs" />
        <input bind:value={passwordconfirm} id="passwordconfirm" type="password" placeholder="Password Confirm" class="input input-bordered input-sm w-full max-w-xs" />
        <button type="submit" class="btn btn-primary w-full max-w-xs">회원가입</button>
    </form>
</div>




