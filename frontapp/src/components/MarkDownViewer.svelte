<script type="module">
    import { marked } from 'marked';

    const mhtml = marked.parse('# Marked in Node.js\n\nRendered by **marked**.\n # hi \n ## **hi** \n ### hi');
</script>
<div>
    {@html mhtml}
</div>
<div>
    {mhtml}
</div>
<h1>
    hi
</h1>
<h2>
    hi
</h2>