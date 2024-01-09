<script>
    import { onMount, onDestroy } from 'svelte';
    import { nanoid } from 'nanoid';
    import { loadPaymentWidget, ANONYMOUS } from '@tosspayments/payment-widget-sdk';
    import rq from "../../../util/rq.svelte.ts";

    const widgetClientKey = "test_ck_oEjb0gm23PbqZpdm6Wk6rpGwBJn5";
    const customerKey = "mqrbFB9hAax-qSL06QnSi";

    let paymentWidget;
    let price = 2000;

    onMount(async () => {
        rq.initAuth();
        try {
            paymentWidget = await loadPaymentWidget(widgetClientKey, customerKey);
            updatePaymentMethods();
        } catch (error) {
            console.error("Error fetching payment widget:", error);
        }

        return () => {
            // 필요한 경우 컴포넌트 해제 시의 로직
        };
    });

    // price가 변경될 때마다 결제 수단 위젯을 업데이트합니다.
    $: if (paymentWidget) {
        updatePaymentMethods();
    }

    function updatePaymentMethods() {
        paymentWidget.renderPaymentMethods(
            "#payment-widget",
            { value: price },
            { variantKey: "DEFAULT" }
        );

        paymentWidget.renderAgreement("#agreement", { variantKey: "AGREEMENT" });
    }

    async function handlePaymentRequest() {
        try {

            await paymentWidget.requestPayment({
                orderId: nanoid(),
                orderName: "유료 멤버쉽",
                customerName: rq.member.username,
                customerEmail: "customer123@gmail.com",
                customerMobilePhone: "01012341234",
                successUrl: `${window.location.origin}/member/membership/success`,
                failUrl: `${window.location.origin}/member/membership/fail`,
                _skipAuth: "FORCE_SUCCESS",
            });
        } catch (error) {
            console.error("Error requesting payment:", error);
        }
    }

</script>

<div>

    <!-- 결제 UI, 이용약관 UI 영역 -->
    <div id="payment-widget" />
    <div id="agreement" />
    <!-- 결제하기 버튼 -->
    <button on:click={handlePaymentRequest}>결제하기</button>
</div>
