package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class OrderTests {

    private DateTime time;

    @Before
    public void initialize() {
        time = new DateTime();  
    }

    @Test
    public void testIfAfterAddItemStateIsCREATED() {
        Order order = new OrderBuilder(time).withAddItem(new OrderItem()).build();

        assertThat(Order.State.CREATED, is(order.getOrderState()));
    }

    @Test
    public void testIfAfterSubmitStateIsSUBMITTED() {
        Order order = new OrderBuilder(time).withAddItem(new OrderItem()).withSubmit().build();

        assertThat(Order.State.SUBMITTED, is(order.getOrderState()));
    }

    @Test
    public void testIfAfterOneMinuteConfirmStateIsCONFIRMED() {
        time = time.plusMinutes(1);
        Order order = new OrderBuilder(time).withAddItem(new OrderItem()).withSubmit().withConfirm().build();

        assertThat(Order.State.CONFIRMED, is(order.getOrderState()));
    }

    @Test
    public void testIfAfterTwoDaysConfirmStateIsCANCELLED() {
        Order order = new Order();

        try {
            time = time.plusHours(25).plusMinutes(1);
            order = new OrderBuilder(time).withAddItem(new OrderItem()).withSubmit().withConfirm().build();
        } catch (OrderExpiredException e) {
        }
        finally {
            assertThat(Order.State.CANCELLED, is(order.getOrderState()));
        }
    }

    @Test
    public void testIfAfterRealizeStateIsREALIZED() {
        Order order = new OrderBuilder(time).withAddItem(new OrderItem()).withSubmit().withConfirm().withRealize().build();

        assertThat(Order.State.REALIZED, is(order.getOrderState()));
    }

    @Test(expected = OrderStateException.class)
    public void testIfThrowsExceptionWithWrongOrder() {
        new OrderBuilder(time).withRealize();
    }

    @Test(expected = OrderExpiredException.class)
    public void testIfThrowsExceptionWithWrongDate() {
        time = time.plusHours(25).plusMinutes(1);
        new OrderBuilder(time).withAddItem(new OrderItem()).withSubmit().withConfirmWithoutCatchException().build();
    }
}
