<script>
    import axios from 'axios';

    async function handleFileUpload(event) {
        const file = event.target.files[0];
        if (!file) return;

        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await axios.post('http://localhost:8090/api/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });
            console.log('File uploaded successfully:', response.data);
        } catch (error) {
            console.error('Error uploading file:', error);
        }
    }
</script>

<form on:submit|preventDefault>
    <input type="file" on:change={handleFileUpload} />
</form>
<img src="https://ghuazvmjviqg21655229.cdn.ntruss.com/OIG.jpeg">
<img src="https://ghuazvmjviqg21655229.cdn.ntruss.com/sample-object.png">

