package com.project.demo.logic.entity.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.project.demo.logic.entity.order.Order;
import com.project.demo.logic.entity.order.OrderRepository;
import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import com.project.demo.logic.entity.userBrand.UserBrand;
import com.project.demo.logic.entity.userBuyer.UserBuyer;
import com.project.demo.logic.entity.userBuyer.UserBuyerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaypalService {

    @Autowired
    private APIContext apiContext;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserBuyerRepository userBuyerRepository;

    public Payment createPayment(List<ItemDto> items, String baseUrl, Long userId, String currency) throws PayPalRESTException {
        double subtotal = 0;

        ItemList itemList = new ItemList();
        List<Item> itemListItems = new ArrayList<>();

        for (ItemDto itemDto : items) {
            subtotal += itemDto.getPrice() * itemDto.getQuantity();
            Item item = new Item();
            item.setName(itemDto.getName());
            item.setCurrency(currency);
            item.setPrice(String.format(Locale.US, "%.2f", itemDto.getPrice()));
            item.setQuantity(String.valueOf(itemDto.getQuantity()));
            itemListItems.add(item);
        }

        itemList.setItems(itemListItems);

        double shipping = 0;
        double tax = 0;

        Amount amount = new Amount();
        amount.setCurrency(currency);
        Details details = new Details();
        details.setShipping(String.format(Locale.US, "%.2f", shipping));
        details.setTax(String.format(Locale.US, "%.2f", tax));
        details.setSubtotal(String.format(Locale.US, "%.2f", subtotal));
        amount.setDetails(details);
        amount.setTotal(String.format(Locale.US, "%.2f", (shipping + tax + subtotal)));

        Transaction transaction = new Transaction();
        transaction.setDescription("Shopping Cart purchase");
        transaction.setItemList(itemList);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(baseUrl + "/");
        redirectUrls.setReturnUrl(baseUrl + "/executePayment");

        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment = payment.create(apiContext);

        UserBuyer userBuyer = userBuyerRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("UserBuyer not found"));

        createOrder(items, userBuyer, subtotal, shipping, tax, shipping + tax + subtotal, "Pending", userBuyer.getDeliveryLocation());

        return createdPayment;
    }

    private void createOrder(List<ItemDto> items, UserBuyer userBuyer, double subtotal, double shipping, double tax, double total, String status, String deliveryLocation) {
        for (ItemDto itemDto : items) {
            Order order = new Order();
            order.setUserBuyer(userBuyer);
            order.setProduct(getProductFromItemDto(itemDto));
            order.setQuantity(itemDto.getQuantity());
            order.setSubTotal(subtotal);
            order.setShippingCost(shipping);
            order.setTotal(total);
            order.setStatus(status);
            order.setDeliveryLocation(deliveryLocation);
            order.setCurrentLocation(getProductBrandFromItemDto(itemDto).getMainLocationAddress());

            orderRepository.save(order);
        }
    }

    private Product getProductFromItemDto(ItemDto itemDto) {
        return productRepository.findByName(itemDto.getName())
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    private UserBrand getProductBrandFromItemDto(ItemDto itemDto) {
        return productRepository.findByName(itemDto.getName())
                .orElseThrow(() -> new RuntimeException("Product not found"))
                .getUserBrand();
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }

}
