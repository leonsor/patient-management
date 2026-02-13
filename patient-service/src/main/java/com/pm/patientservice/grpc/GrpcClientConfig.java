package com.pm.patientservice.grpc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
public class GrpcClientConfig {

    @Bean
    public BillingServiceGrpc.BillingServiceBlockingStub billingServiceBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();
        return BillingServiceGrpc.newBlockingStub(channel);
    }
}
