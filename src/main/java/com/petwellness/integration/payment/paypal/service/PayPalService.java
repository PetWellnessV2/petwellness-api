package com.petwellness.integration.payment.paypal.service;

import com.petwellness.exception.ResourceNotFoundException;
import com.petwellness.integration.payment.paypal.dto.*;
import com.petwellness.model.entity.DetallePedido;
import com.petwellness.repository.DetallePedidoRepository;
import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PayPalService {
    @Value("${paypal.client-id}")
    private String clientId;

    @Value("${paypal.client-secret}")
    private String clientSecret;

    @Value("${paypal.api-base}")
    private String apiBase;

    @NonNull
    private DetallePedidoRepository purchaseRepository;
    private RestClient paypalClient;

    @PostConstruct
    public void init(){
        paypalClient = RestClient
                .builder()
                .baseUrl(apiBase)
                .build();

    }

    public String getAccessToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        // Enviar la solicitud POST a PayPal para obtener el token de acceso
        return Objects.requireNonNull(
                        paypalClient.post()
                                .uri("/v1/oauth2/token")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .header(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder()
                                        .encodeToString((clientId + ":" + clientSecret).getBytes()))
                                .body(body)
                                .retrieve()
                                .toEntity(TokenResponse.class).getBody())
                .getAccessToken();
    }


    public OrderResponse createOrder(Integer purchaseId, String returnUrl, String cancelUrl){
        DetallePedido purchase= purchaseRepository.findById(purchaseId)
                .orElseThrow(ResourceNotFoundException::new);

        //Construcción de la solicitud de Pedido de Pago
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setIntent("CAPTURE");

        Amount amount = new Amount();
        amount.setCurrencyCode("USD");
        amount.setValue(purchase.getPrecioTotal().toString());

        PurchaseUnit purchaseUnit = new PurchaseUnit();
        purchaseUnit.setReferenceId(purchase.getIdDetalle().toString());
        purchaseUnit.setAmount(amount);

        orderRequest.setPurchaseUnits(Collections.singletonList(purchaseUnit));

        ApplicationContext applicationContext = ApplicationContext.builder()
                .brandName("Petwellness")
                .returnUrl(returnUrl)
                .cancelUrl(cancelUrl)
                .build();

        orderRequest.setApplicationContext(applicationContext);

        return paypalClient.post()
                .uri("/v2/checkout/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .body(orderRequest)
                .retrieve()
                .toEntity(OrderResponse.class)
                .getBody();

    }

    public OrderCaptureResponse captureOrder(String orderId){
        return paypalClient.post()
                .uri("/v2/checkout/orders/{order_id}/capture", orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .retrieve()
                .toEntity(OrderCaptureResponse.class)
                .getBody();


    }




}
