package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderTests {

    private Order order;

    @Before
    public void initialize() {
        order = new Order();
    }

    @Test
    public void testIfAfterAddItemStateIsCREATED() {
        order.addItem(new OrderItem());

        assertThat(Order.State.CREATED, is(order.getOrderState()));
    }

    @Test
    public void testIfAfterSubmitStateIsSUBMITTED() {
        order.addItem(new OrderItem());
        order.submit();

        assertThat(Order.State.SUBMITTED, is(order.getOrderState()));
    }
}
