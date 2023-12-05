<script>
    let username = $state('')

    let repositories = $state([]);

    async function fetchRepositories(){
        const response = await fetch(`https://api.github.com/users/${username}/repos?sort=updated&per_page=15`);
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