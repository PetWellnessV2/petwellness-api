package com.petwellness.service.impl;

import com.petwellness.dto.DetallePedidoDTO;
import com.petwellness.dto.PaymentCaptureResponse;
import com.petwellness.dto.PaymentOrderResponse;
import com.petwellness.dto.PedidoDTO;
import com.petwellness.integration.payment.paypal.dto.OrderCaptureResponse;
import com.petwellness.integration.payment.paypal.dto.OrderResponse;
import com.petwellness.integration.payment.paypal.service.PayPalService;
import com.petwellness.service.CheckoutService;
import com.petwellness.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final PayPalService payPalService;
    private final PedidoService purchaseService;

    @Override
    public PaymentOrderResponse createPayment(Integer purchaseId, String returnUrl, String cancelUrl) {
        OrderResponse orderResponse = payPalService.createOrder(purchaseId, returnUrl, cancelUrl);

        String paypalUrl = orderResponse
                .getLinks()
                .stream()
                .filter(link -> link.getRel().equals("approve"))
                .findFirst()
                .orElseThrow(RuntimeException::new)
                .getHref();

        return new PaymentOrderResponse(paypalUrl);
    }

    @Override
    public PaymentCaptureResponse captureResponse(String orderId) {
        OrderCaptureResponse orderCaptureResponse = payPalService.captureOrder(orderId);
        boolean completed = orderCaptureResponse.getStatus().equals("COMPLETED");

        PaymentCaptureResponse paypalCaptureResponse = new PaymentCaptureResponse();
        paypalCaptureResponse.setCompleted(completed);

        if(completed) {
            String purchaseIdStr = orderCaptureResponse.getPurchaseUnits().get(0).getReferenceId();
            PedidoDTO purchaseDTO = purchaseService.confirmarPedido(Integer.parseInt(purchaseIdStr));
            paypalCaptureResponse.setPurchaseId(purchaseDTO.getIdPedido());
        }

        return paypalCaptureResponse;
    }
}
