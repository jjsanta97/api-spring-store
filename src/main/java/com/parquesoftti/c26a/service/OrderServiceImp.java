package com.parquesoftti.c26a.service;

import com.parquesoftti.c26a.model.Customer;
import com.parquesoftti.c26a.model.Order;
import com.parquesoftti.c26a.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    final OrderRepository orderRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public Order update(Long id, Order order) {
        Order orderTmp = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        orderTmp.setOrderDate(order.getOrderDate());
        orderTmp.setCustomer(order.getCustomer());
        orderTmp.setProduct(order.getProduct());
        orderTmp.setQuantity(order.getQuantity());

        return orderRepository.save(orderTmp);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
