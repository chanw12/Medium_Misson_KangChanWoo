<script>
    import { onMount } from 'svelte';
    import { goto } from '$app/navigation';
    import { page } from '$app/stores';
    import {getCookie} from "../../../../util/getCookie.ts";
    import axios from "axios";

    let orderId;
    let amount;
    let paymentKey;

    const backUrl = import.meta.env.VITE_BACK_API_URL;

    onMount(async () => {
        const urlParams = new URLSearchParams(window.location.search);
        orderId = urlParams.get("orderId");
        amount = urlParams.get("amount");
        paymentKey = urlParams.get("paymentKey");
        const accessToken = getCookie('accessToken');
        const refreshToken = getCookie('RefreshToken')

        const requestData = { orderId, amount, paymentKey };

        try {
            const response = await axios.post(`${backUrl}/confirm`,JSON.stringify(requestData), {
                headers: {
                    "Content-Type": "application/json",
                    'Authorization': `Bearer ${refreshToken}#${accessToken}`
                },
            });


            console.log(response);
            const json = await response.json();
            if (!response.ok) {
                // 결제 실패 비즈니스 로직을 구현하세요.
                goto(`/fail?message=${json.message}&code=${json.code}`);
                return;
            }

            // 결제 성공 비즈니스 로직을 구현하세요.
        } catch (error) {
            console.error("Error in payment confirmation:", error);
        }
    });
</script>

<div class="result wrapper">
    <div class="box_section">
        <h2>결제 성공</h2>
        <p>{`주문번호: ${orderId}`}</p>
        <p>{`결제 금액: ${Number(amount).toLocaleString()}원`}</p>
        <p>{`paymentKey: ${paymentKey}`}</p>
    </div>
</div>
