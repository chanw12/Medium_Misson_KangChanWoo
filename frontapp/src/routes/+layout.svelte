<script>
	import '../app.css';

	const { children } = $props();
</script>

<div data-theme="light" class="app">
	{@render children()}
</div>
