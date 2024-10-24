package com.petwellness.service;

import com.petwellness.dto.PaymentCaptureResponse;
import com.petwellness.dto.PaymentOrderResponse;

public interface CheckoutService {
    PaymentOrderResponse createPayment(Integer purchaseId, String returnUrl, String cancelUrl);

    PaymentCaptureResponse captureResponse(String orderId);
}
