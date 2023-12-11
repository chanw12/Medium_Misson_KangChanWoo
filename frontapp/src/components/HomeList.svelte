<script>

    import {onMount} from "svelte";
    import axios from "axios";

    onMount(()=>{
        fetchHomeList();
    })
    let homeList = $state([]);
    const fetchHomeList = async () => {
        try {
            // JWT 토큰을 쿠키에서 가져오기
            // 유저 정보를 가져오기 위한 요청
            const response = await axios.get('http://localhost:8090/api/homelist');
            homeList = response.data;
            console.log(homeList);
        } catch (error) {
            console.error('Error fetching user information:', error);
        }
    };

</script>

<div class="overflow-x-auto">
<!--    <table class="table">-->
<!--        &lt;!&ndash; head &ndash;&gt;-->
<!--        <thead>-->
<!--        <tr>-->
<!--            <th>-->
<!--                <label>-->
<!--                    <input type="checkbox" class="checkbox" />-->
<!--                </label>-->
<!--            </th>-->
<!--            <th>Author</th>-->
<!--            <th>Title</th>-->
<!--            <th>CreateDate</th>-->
<!--            <th></th>-->
<!--        </tr>-->
<!--        </thead>-->

<!--        <tbody>-->
<!--        {#each homeList as item}-->
<!--        &lt;!&ndash; row 1 &ndash;&gt;-->
<!--        <tr>-->
<!--            <th>-->
<!--            </th>-->
<!--            <td>-->
<!--                <div class="flex items-center gap-3">-->
<!--                    <div>-->
<!--                        <div class="font-bold">{item.author.username}</div>-->
<!--&lt;!&ndash;                        <div class="text-sm opacity-50">United States</div>&ndash;&gt;-->
<!--                    </div>-->
<!--                </div>-->
<!--            </td>-->
<!--            <td>-->
<!--                {item.title}-->
<!--                <br/>-->
<!--&lt;!&ndash;&lt;!&ndash;                <span class="badge badge-ghost badge-sm">Desktop Support Technician</span>&ndash;&gt; 태그를 넣을꺼면 여기에다&ndash;&gt;-->
<!--            </td>-->
<!--            <td>{item.createDate}</td>-->

<!--        </tr>-->
<!--        {/each}-->
<!--        </tbody>-->
<!--        &lt;!&ndash; foot &ndash;&gt;-->
<!--&lt;!&ndash;        <tfoot>&ndash;&gt;-->
<!--&lt;!&ndash;        <tr>&ndash;&gt;-->
<!--&lt;!&ndash;            <th></th>&ndash;&gt;-->
<!--&lt;!&ndash;            <th>Name</th>&ndash;&gt;-->
<!--&lt;!&ndash;            <th>Job</th>&ndash;&gt;-->
<!--&lt;!&ndash;            <th>Favorite Color</th>&ndash;&gt;-->
<!--&lt;!&ndash;            <th></th>&ndash;&gt;-->
<!--&lt;!&ndash;        </tr>&ndash;&gt;-->
<!--&lt;!&ndash;        </tfoot>&ndash;&gt;-->

<!--    </table>-->
    <div class="flex flex-wrap gap-20 justify-center">
        <!-- 첫 번째 카드 -->
        {#each homeList as item}
        <div class="card w-96 bg-base-100 shadow-xl">
            <figure><img src="https://daisyui.com/images/stock/photo-1606107557195-0e29a4b5b4aa.jpg" alt="Shoes" /></figure>
            <div class="card-body">
                <h2 class="card-title">{item.title}</h2>
                <p>여기에 간단한 설명 넣기</p>
                <div class="card-actions justify-between pt-10">
                    <div class="items-end">
                        <p class="text-sm opacity-50">작성자:{item.author.username}</p>
                    </div>
                    <a href="http://localhost:5173/post/{item.id}" class="btn btn-primary">자세히 보기</a>
                </div>
            </div>
        </div>
            {/each}
    </div>

</div>