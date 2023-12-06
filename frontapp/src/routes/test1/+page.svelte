<script>
    let username = $state('')

    let repositories = $state([]);

    async function fetchRepositories(){
        const res = await fetch('https://localhost:8090/api/member/join', {
            method: 'POST',
            body: JSON.stringify({

            })
        })
        repositories = await response.json();
    }


</script>

<svelte:head>
    <title>Test1</title>
    <meta name="description" content="테스트" />
</svelte:head>
<div class="p-5">
    <form on:submit|preventDefault={fetchRepositories}>
        <input class="input input-bordered" type="text" bind:value={username} placeholder="github 사용자 이름을 입력하세요">
        <button type="submit">데이터 가져오기</button>
    </form>
</div>


<section class="text-red-500 h-full">
    <ul>
        {#each repositories as repo}
            <li>{repo.name}</li>
        {/each}
    </ul>
</section>