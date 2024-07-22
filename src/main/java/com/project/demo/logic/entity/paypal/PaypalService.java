package com.project.demo.logic.entity.paypal;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.project.demo.rest.paypal.ItemDto;
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

    public Payment createPayment(List<ItemDto> items, String baseUrl) throws PayPalRESTException {
        double subtotal = 0;

        ItemList itemList = new ItemList();
        List<Item> itemListItems = new ArrayList<>();

        for (ItemDto itemDto : items) {
            subtotal += itemDto.getPrice() * itemDto.getQuantity();
            Item item = new Item();
            item.setName(itemDto.getName());
            item.setCurrency("USD");
            item.setPrice(String.format(Locale.US, "%.2f", itemDto.getPrice()));
            item.setQuantity(String.valueOf(itemDto.getQuantity()));
            itemListItems.add(item);
        }

        itemList.setItems(itemListItems);

        double shipping = 0;
        double tax = 0;

        Amount amount = new Amount();
        amount.setCurrency("USD");
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
        redirectUrls.setCancelUrl(baseUrl + "/cancel");
        redirectUrls.setReturnUrl(baseUrl + "/execute");

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public  Payment excecutePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }

}
