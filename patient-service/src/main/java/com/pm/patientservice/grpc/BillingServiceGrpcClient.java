package com.pm.patientservice.grpc;

import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;
    public BillingServiceGrpcClient(BillingServiceGrpc.BillingServiceBlockingStub blockingStub) {
        this.blockingStub = blockingStub;
    }
}
