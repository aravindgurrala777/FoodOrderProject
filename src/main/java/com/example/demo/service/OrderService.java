package com.example.demo.service;


import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.model.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {
    private final List<Order> orders = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public OrderService() {
        orders.add(new Order(idGenerator.getAndIncrement(), "Ravi", "Biryani", 2, 350.0));
        orders.add(new Order(idGenerator.getAndIncrement(), "Priya", "Pizza", 1, 499.0));
        orders.add(new Order(idGenerator.getAndIncrement(), "Kiran", "Burger", 3, 450.0));
    }

    public List<Order> getAllOrders() {
        return orders;
    }

    public Order getOrderById(Long id) {
        return orders.stream()
                .filter(o -> o.getOrderId().equals(id))
                .findFirst()
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));
    }

    public Order createOrder(Order order) {
        order.setOrderId(idGenerator.getAndIncrement());
        orders.add(order);
        return order;
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existing = getOrderById(id);
        existing.setCustomerName(updatedOrder.getCustomerName());
        existing.setFoodItem(updatedOrder.getFoodItem());
        existing.setQuantity(updatedOrder.getQuantity());
        existing.setPrice(updatedOrder.getPrice());
        return existing;
    }

    public Order patchOrder(Long id, Order patchOrder) {
        Order existing = getOrderById(id);
        if (patchOrder.getCustomerName() != null) existing.setCustomerName(patchOrder.getCustomerName());
        if (patchOrder.getFoodItem() != null) existing.setFoodItem(patchOrder.getFoodItem());
        if (patchOrder.getQuantity() != null) existing.setQuantity(patchOrder.getQuantity());
        if (patchOrder.getPrice() != null) existing.setPrice(patchOrder.getPrice());
        return existing;
    }

    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        orders.remove(order);
    }
}