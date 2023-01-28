package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
        
        Reservation reservation = reservationRepository2.findById(reservationId).get();
        int bill =reservation.getNumberOfHours()*reservation.getSpot().getPricePerHour();

        if(bill>amountSent){
            throw new Exception("Insufficient Amount");
        }


        Payment payment = reservation.getPayment();

        if(mode.toUpperCase().equals("UPI")){
            payment.setPaymentMode(PaymentMode.UPI);
        }
        else if(mode.toUpperCase().equals("CASH")){
            payment.setPaymentMode(PaymentMode.CASH);
        }
        else if(mode.toUpperCase().equals("CARD")){
            payment.setPaymentMode(PaymentMode.CARD);
        }
        else{
            throw new Exception("Payment mode not detected");
        }
        
        payment.setReservation(reservation);
        payment.setPaymentCompleted(Boolean.TRUE);
        paymentRepository2.save(payment);

        return payment;

    }
}
