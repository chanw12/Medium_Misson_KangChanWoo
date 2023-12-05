<script>
	let username = $state('chanw12')

	let repositories = $state([]);

	$effect(async () =>{
		const response = await fetch(`https://api.github.com/users/${username}/repos?sort=updated&per_page=10`);
		repositories = await response.json();
	})
</script>

<svelte:head>
	<title>Home</title>
	<meta name="description" content="홈 화면" />
</svelte:head>

<section class="text-red-500">
	<ul>
		{#each repositories as repo}
			<li>{repo.name}</li>
		{/each}
	</ul>
</section>