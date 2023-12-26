<script>
	import '../app.css';
	import Navigation from "../components/Navigation.svelte";
	const { children } = $props();
</script>
<Navigation></Navigation>
<div class="App">
	{@render children()}
</div>
