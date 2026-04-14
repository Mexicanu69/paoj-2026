package com.pao.laboratory07.exercise1;

public enum OrderState {
    PLACED,
    PROCESSED,
    SHIPPED,
    DELIVERED,
    CANCELED;

    public boolean isFinal() {
        return this == DELIVERED || this == CANCELED;
    }

    public OrderState next() {
        switch (this) {
            case PLACED: return PROCESSED;
            case PROCESSED: return SHIPPED;
            case SHIPPED: return DELIVERED;
            default: return this;
        }
    }
}