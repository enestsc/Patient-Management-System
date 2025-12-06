package com.enestsc.billingservice.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import com.enestsc.billingservice.model.Billing;
import com.enestsc.billingservice.repository.BillingRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    private final BillingRepository billingRepository;

    public BillingGrpcService(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseObserver) {
        log.info("createBillingAccount request received {}", billingRequest.toString());

        // Business logic

        BillingResponse response = BillingResponse.newBuilder()
                .setAccountId(billingRequest.getPatientId())
                .setStatus("ACTIVE")
                .build();

        Billing billing = new Billing();
        billing.setStatus("ACTIVE");
        billing.setFeeAmount(110.0);
        billing.setPatientId(UUID.fromString(billingRequest.getPatientId()));

        billingRepository.save(billing);

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }

}