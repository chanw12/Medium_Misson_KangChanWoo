<script>
    import {data} from "autoprefixer";
    import axios from "axios";
    import {Cookies} from 'react-cookie';
    const cookies = new Cookies();

    let username = $state('')
    let password = $state('')
    let errorMsg = $state('')
    let jwtToken = $state('')

    let result = null
    function hideErrorMessage() {
        errorMsg = ""; // 에러 메시지 비우기
    }

    setTimeout(hideErrorMessage, 5000);
    async function fetchLogin(){
        const res = await axios.post('http://localhost:8090/api/authenticate',
            {
                username,
                password,
            }
        )
            .then(res =>{
                // if(res.status == 200){
                //     location.href="/"  //로그인 성공시 메인페이지로 이동
                // }
                jwtToken = res.data.token;
                console.log(jwtToken);
                cookies.set("accessJwtToken",jwtToken);

            })
            .catch(error =>{
                errorMsg = error.response.data.msg;
            })

    }

    async function fetchHelo1(){
        const res = await axios.get('http://localhost:8090/api/member/user',{
            headers: {
                Authorization: `Bearer ${cookies.get('accessJwtToken')}`
            }
        })
            .then(res =>{
                console.log(res.data);
            })
            .catch(error =>{
                errorMsg = error.response.data.msg;
            })

    }



</script>

<svelte:head>
    <title>Test1</title>
    <meta name="description" content="테스트" />
</svelte:head>

<div class="flex-col">
    <div class="items-center">Login</div>
    {#if errorMsg !== ''}
        <div role="alert" class="alert alert-warning">
            <svg xmlns="http://www.w3.org/2000/svg" class="stroke-current shrink-0 h-6 w-6" fill="none" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
            </svg>
            <span>{errorMsg}</span>
        </div>
    {/if}
    <form class="p-5" on:submit|preventDefault={fetchLogin}>
        <input bind:value={username} type="text" placeholder="ID" class="input input-bordered input-sm w-full max-w-xs" />
        <input bind:value={password} id="password" type="text" placeholder="Password" class="input input-bordered input-sm w-full max-w-xs" />
        <button type="submit">로그인</button>
    </form>
    <form  on:submit|preventDefault={fetchHelo1}>
        <button type="submit">제출1</button>
    </form>


</div>



