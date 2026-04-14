package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.CannotRevertInitialOrderStateException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private OrderState currentState;
    private final List<OrderState> history;

    public Order(OrderState initialState) {
        this.currentState = initialState;
        this.history = new ArrayList<>();
        history.add(initialState);
    }

    public void nextState() throws OrderIsAlreadyFinalException {
        if (currentState.isFinal()) {
            throw new OrderIsAlreadyFinalException("Order is already in a final state.");
        }
        OrderState newState = currentState.next();
        if (newState != currentState) {
            history.add(newState);
            currentState = newState;
        }
        System.out.println("Order state updated to: " + currentState);
    }

    public void cancel() throws CannotCancelFinalOrderException {
        if (currentState.isFinal()) {
            throw new CannotCancelFinalOrderException("Cannot cancel a final state order.");
        }
        history.add(OrderState.CANCELED);
        currentState = OrderState.CANCELED;
        System.out.println("Order has been canceled.");
    }

    public void undoState() throws CannotRevertInitialOrderStateException {
        if (history.size() <= 1) {
            throw new CannotRevertInitialOrderStateException("Nu există stare anterioară pentru undo.");
        }
        history.remove(history.size() - 1);
        currentState = history.get(history.size() - 1);
        System.out.println("Order state reverted to: " + currentState);
    }
}