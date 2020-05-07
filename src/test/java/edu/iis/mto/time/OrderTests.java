package edu.iis.mto.time;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderTests {

    @Test
    public void testIfAfterAddItemStateIsCREATED() {
        Order order = new OrderBuilder().withAddItem(new OrderItem()).build();

        assertThat(Order.State.CREATED, is(order.getOrderState()));
    }

    @Test
    public void testIfAfterSubmitStateIsSUBMITTED() {
        Order order = new OrderBuilder().withAddItem(new OrderItem()).withSubmit().build();
        
        assertThat(Order.State.SUBMITTED, is(order.getOrderState()));
    }

    @Test
    public void testIfAfterOneMinuteConfirmStateIsCONFIRMED() {
        Order order = new OrderBuilder().withAddItem(new OrderItem()).withSubmit().withConfirmGoodTime().build();

        assertThat(Order.State.CONFIRMED, is(order.getOrderState()));
    }

    @Test
    public void testIfAfterTwoDaysConfirmStateIsCANCELLED() {
        Order order = new Order();

        try {
            order = new OrderBuilder().withAddItem(new OrderItem()).withSubmit().withConfirmBadTime().build();
        } catch (OrderExpiredException e) {
        } finally {
            assertThat(Order.State.CANCELLED, is(order.getOrderState()));
        }
    }
}
