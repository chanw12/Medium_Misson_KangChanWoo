<script>
	import axios from "axios";
	let errorMsg = $state('')
	let result = $state('')


	async function fetchHelo(){
		const res = await axios.post('http://localhost:8090/api/member/logout')
				.then(res =>{
					console.log(res.data);
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


	}

</script>

<svelte:head>
	<title>Home</title>
	<meta name="description" content="홈 화면" />
</svelte:head>


<form  on:submit|preventDefault={fetchHelo}>
	<button type="submit">제출</button>
</form>

<form  on:submit|preventDefault={fetchHelo1}>
	<button type="submit">제출1</button>
</form>