<script>
    import '@toast-ui/editor/dist/toastui-editor.css';
    // import Editor from '@toast-ui/editor';
    import {onDestroy, onMount} from "svelte";
    import {toastui} from "@toast-ui/editor";

    const Editor = toast.Editor;
    let editor
    onMount(()=>{
        editor = new Editor({
            el: document.querySelector('#editor'),
            height: '600px',
            initialEditType: 'markdown',
            previewStyle: 'vertical',
            theme:'dark',

        });

    })
    onDestroy(() => {
        // 메모리 누수를 방지하기 위해 편집기 인스턴스 제거
        if (editor) {
            editor.destroy()
        }
    });
</script>

<div id="editor" class="bg-white" >

</div>