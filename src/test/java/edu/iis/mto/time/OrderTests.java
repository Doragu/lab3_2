package edu.iis.mto.time;

import org.joda.time.DateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderTests {

    @Mock
    private TimeSource timeSource;

    @Test
    public void testIfAfterAddItemStateIsCREATED() {
        Order order = new OrderBuilder(timeSource).withAddItem(new OrderItem()).build();

        assertThat(Order.State.CREATED, is(order.getOrderState()));
    }

    @Test
    public void testIfAfterSubmitStateIsSUBMITTED() {
        Order order = new OrderBuilder(timeSource).withAddItem(new OrderItem()).withSubmit().build();

        assertThat(Order.State.SUBMITTED, is(order.getOrderState()));
    }

    @Test
    public void testIfAfterOneMinuteConfirmStateIsCONFIRMED() {
        when(timeSource.getActualDate()).thenReturn(new DateTime().plusMinutes(1));
        Order order = new OrderBuilder(timeSource).withAddItem(new OrderItem()).withSubmit().withConfirm().build();

        assertThat(Order.State.CONFIRMED, is(order.getOrderState()));
    }

    @Test
    public void testIfAfterTwoDaysConfirmStateIsCANCELLED() {
        Order order = new Order();

        try {
            when(timeSource.getActualDate()).thenReturn(new DateTime().plusHours(25).plusMinutes(1));
            order = new OrderBuilder(timeSource).withAddItem(new OrderItem()).withSubmit().withConfirm().build();

        } catch (OrderExpiredException e) {
        }
        finally {
            assertThat(Order.State.CANCELLED, is(order.getOrderState()));
        }
    }

    @Test
    public void testIfAfterRealizeStateIsREALIZED() {
        when(timeSource.getActualDate()).thenReturn(new DateTime());
        Order order = new OrderBuilder(timeSource).withAddItem(new OrderItem()).withSubmit().withConfirm().withRealize().build();

        assertThat(Order.State.REALIZED, is(order.getOrderState()));
    }

    @Test
    public void testIfThrowsExceptionWithWrongOrder() {
        assertThrows(OrderStateException.class,
                () -> new OrderBuilder(timeSource).withRealize());
    }

    @Test
    public void testIfThrowsExceptionWithWrongDate() {
        when(timeSource.getActualDate()).thenReturn(new DateTime().plusHours(25).plusMinutes(1));

        assertThrows(OrderExpiredException.class,
                () ->new OrderBuilder(timeSource).withAddItem(new OrderItem()).withSubmit().withConfirmWithoutCatchException().build());
    }
}
