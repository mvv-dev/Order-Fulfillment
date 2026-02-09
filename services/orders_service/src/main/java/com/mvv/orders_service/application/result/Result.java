package com.mvv.orders_service.application.result;

public record Result<T>(
        boolean ok,
        String outName,
        T payload
) {

    public static <T> Result<T> success(String outName, T payload) {
        return new Result<>(true, outName, payload);
    }

    public static <T> Result<T> error(String outName, T payload) {
        return new Result<>(false, outName, payload);
    }

}
