package br.com.compass.paymentservicepb.service;

import br.com.compass.paymentservicepb.form.ItemForm;
import br.com.compass.paymentservicepb.form.OrderForm;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    public BigDecimal getTotal(List<ItemForm> itens, OrderForm order) {
        BigDecimal total = BigDecimal.ZERO;

            if(itens.isEmpty()) {
                return total;
            }

            for (int i = 0; i < itens.size(); i++) {
                total = total.add(itens.get(i).getValue().multiply(BigDecimal.valueOf(itens.get(i).getQty())));
            }
            total = total.subtract(order.getDiscount()).add(order.getShipping());

        return total;
    }

}
