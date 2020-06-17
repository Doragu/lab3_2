package edu.iis.mto.time;

public class OrderBuilder {

    private Order order;

    public OrderBuilder(TimeSource timeSource) {
        order = new Order(timeSource);
    }

    public OrderBuilder withAddItem(OrderItem item) {
        order.addItem(item);
        return this;
    }

    public OrderBuilder withSubmit() {
        order.submit();
        return this;
    }

    public OrderBuilder withConfirm() {
        try {
            order.confirm();
        } catch (OrderExpiredException e) {
        }

        return this;
    }

    public OrderBuilder withConfirmWithoutCatchException() {
        order.confirm();
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
