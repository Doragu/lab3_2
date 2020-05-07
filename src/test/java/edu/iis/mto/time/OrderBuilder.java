package edu.iis.mto.time;

public class OrderBuilder {
    private Order order = new Order();

    public OrderBuilder withAddItem(OrderItem item) {
        order.addItem(item);
        return this;
    }

    public OrderBuilder withSubmit() {
        order.submit();
        return this;
    }

    public OrderBuilder withConfirmBadTime() {
        try {
            order.confirm(order.getSubbmitionDate().plusHours(25));
        } catch (OrderExpiredException e) {
        } finally {
            return this;
        }
    }

    public OrderBuilder withConfirmGoodTime() {
        order.confirm(order.getSubbmitionDate().plusHours(1));
        return this;
    }

    public OrderBuilder withRealize() {
        order.realize();
        return this;
    }

    public Order build() {
        return order;
    }
}
