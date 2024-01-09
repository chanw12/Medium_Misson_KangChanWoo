<script>
    import { onMount } from 'svelte';

    let code = '';
    let message = '';

    onMount(() => {
        const urlParams = new URLSearchParams(window.location.search);
        code = urlParams.get("code");
        message = urlParams.get("message");
    });
</script>

<div class="result wrapper">
    <div class="box_section">
        <h2>결제 실패</h2>
        <p>{`에러 코드: ${code}`}</p>
        <p>{`실패 사유: ${message}`}</p>
    </div>
</div>
