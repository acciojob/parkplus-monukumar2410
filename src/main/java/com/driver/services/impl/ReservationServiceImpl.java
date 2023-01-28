package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
      Reservation reservation = new Reservation();
      ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId).get();
      User user = userRepository3.findById(userId).get();

      if(user == null || parkingLot == null){
        throw new Exception("Cannot make reservation");
      }

      List<Spot> spots = parkingLot.getSpotList();
      Spot spot = null;
      for(Spot spot1: spots){
         if(spot1.getSpotType()==SpotType.TWO_WHEELER){
            if(numberOfWheels <= 2 && spot1.getOccupied()==Boolean.FALSE){
                if(spot==null || spot.getPricePerHour()>spot1.getPricePerHour()){
                    spot = spot1;
                }
            }
         }
         else if(spot1.getSpotType()==SpotType.FOUR_WHEELER){
            if(numberOfWheels <= 4 && spot1.getOccupied()==Boolean.FALSE){
                if(spot==null || spot.getPricePerHour()>spot1.getPricePerHour()){
                    spot = spot1;
                }
            }
         }
         else 
            if(spot1.getOccupied()==Boolean.FALSE){
                if(spot==null || spot.getPricePerHour()>spot1.getPricePerHour()){
                    spot = spot1;
                }
            }
         }

         if(spot==null){
            throw new Exception("Cannot make reservation");
         }
       
         
         spot.setOccupied(Boolean.TRUE);
         Payment payment = new Payment();

         reservation.setNumberOfHours(timeInHours);
         reservation.setSpot(spot);
         reservation.setUser(user);
         reservation.setPayment(payment);

         user.getReservationList().add(reservation);
         userRepository3.save(user);

         parkingLot.getSpotList().add(spot);
         parkingLotRepository3.save(parkingLot);

         spotRepository3.save(spot);


      return reservation;
    }
}
