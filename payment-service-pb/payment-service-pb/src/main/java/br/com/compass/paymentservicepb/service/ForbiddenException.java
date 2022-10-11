package br.com.compass.paymentservicepb.service;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String msg) {
        super(msg);
    }
}
