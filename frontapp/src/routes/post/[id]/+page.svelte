<script>

import PostDetail from "../../../components/PostDetail.svelte";
export let data;
import { usernameStore } from "$lib/stores/store.js";
import {onMount} from "svelte";

onMount(()=>{
})

</script>
<PostDetail {data}></PostDetail>
