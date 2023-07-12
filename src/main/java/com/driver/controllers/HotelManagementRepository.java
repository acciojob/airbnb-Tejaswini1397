package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class HotelManagementRepository {
    private final Map<String, Hotel> hotelDb = new HashMap<>();
    private final Map<Integer,User>userMap=new HashMap<>();
    private final Map<String,Booking>bookingMap=new HashMap<>();
    private final Map<String,Integer>userRent=new HashMap<>();


    public String addHotel( Hotel hotel) {
        if (hotel == null || hotel.getHotelName() == null) {
            return "FAILURE";
        }
        if (hotelDb.containsKey(hotel.getHotelName())) {
            return "FAILURE";
        }

        hotelDb.put(hotel.getHotelName(), hotel);
        return "SUCCESS";

}
    public Integer addUser( User user) {
        Integer aadharCardNo = user.getaadharCardNo();
      userMap.put(aadharCardNo,user);
        // Add user to the database or perform any necessary operations
        return aadharCardNo;
    }

    public String getHotelWithMostFacilities() {
//        String hotelWithMostFacilities = "";
//        int maxFacilities = 0;
//
//        for (Hotel hotel : hotelDb.values()) {
//            int numFacilities = hotel.getFacilities().size();
//            if (numFacilities > maxFacilities) {
//                maxFacilities = numFacilities;
//                hotelWithMostFacilities = hotel.getHotelName();
//            } else if (numFacilities == maxFacilities && hotel.getHotelName().compareTo(hotelWithMostFacilities) < 0) {
//                hotelWithMostFacilities = hotel.getHotelName();
//            }
//        }
//
//        return hotelWithMostFacilities;
        int maxFacility=0;
        List<String> hotelNames = new ArrayList<>();

        for(String key:hotelDb.keySet()){
            List<Facility>facilities=hotelDb.get(key).getFacilities();
            maxFacility=Math.max(maxFacility,facilities.size());
        }
        if(maxFacility==0) return "";
        for(String key:hotelDb.keySet()){
            List<Facility>facilities=hotelDb.get(key).getFacilities();
            if(maxFacility==facilities.size()){
                hotelNames.add(key);
            }
        }
        Collections.sort(hotelNames);
        return hotelNames.get(0);
    }
    public int bookARoom( Booking booking) {

        //The booking object coming from postman will have all the attributes except bookingId and amountToBePaid;
        //Have bookingId as a random UUID generated String
        //save the booking Entity and keep the bookingId as a primary key
        //Calculate the total amount paid by the person based on no. of rooms booked and price of the room per night.
        //If there arent enough rooms available in the hotel that we are trying to book return -1
        //in other case return total amount paid
        String bookingId = UUID.randomUUID().toString();
        booking.setBookingId(bookingId);
//
//        // Calculate the total amount paid by the person based on the number of rooms booked and price of the room per night
      //  int totalAmountPaid = booking.getNoOfRooms() * booking.getAmountToBePaid();
      //  bookingMap.put(bookingId,booking);

//        // Check if there are enough rooms available in the hotel
        Hotel hotel = hotelDb.get(booking.getHotelName());
        if (hotel != null && hotel.getAvailableRooms() >= booking.getNoOfRooms()) {
            // Update the number of available rooms in the hotel
            hotel.setAvailableRooms(hotel.getAvailableRooms() - booking.getNoOfRooms());
           int amountTobePaid=hotel.getPricePerNight()*booking.getNoOfRooms();
            bookingMap.put(bookingId,booking);
            return amountTobePaid;
        } else {
            return -1; // Not enough rooms available
        }
//        String hotelName=booking.getHotelName();
//        if(!hotelDb.containsKey(hotelName))return -1;
//        if(hotelDb.get(hotelName).getAvailableRooms()>=booking.getNoOfRooms()){
//            Hotel hotel=hotelDb.get(hotelName);
//            int totalAvailable=hotel.getAvailableRooms();
//            totalAvailable-=booking.getNoOfRooms();
//            hotel.setAvailableRooms(totalAvailable);
//            hotelDb.put(hotelName,hotel);
//            String bookinId= String.valueOf(UUID.randomUUID());
//            int amountTobePaid=hotel.getPricePerNight()*booking.getNoOfRooms();
//            bookingMap.put(bookinId,booking);
//            userRent.put(bookinId,amountTobePaid);
//            return amountTobePaid;
//        }
//        return -1;

    }
    public int getBookings(Integer aadharCard) {
        // Get bookings done by a person based on aadharCard
        // Return the count of bookings
        int count=0;
        for(String key:bookingMap.keySet()){
            if(aadharCard.equals(bookingMap.get(key).getBookingAadharCard())){
                count++;
            }
        }
        return count;
    }
    public Hotel updateFacilities(List<Facility> newFacilities, String hotelName) {
        Hotel hotel = hotelDb.get(hotelName);
        if (hotel == null) {
            return null;
        }

        Set<Facility> facilities = new HashSet<>(hotel.getFacilities());
        for (Facility facility : newFacilities) {
            facilities.add(facility);
        }

        hotel.setFacilities(new ArrayList<>(facilities));
        hotelDb.put(hotelName, hotel);
        return hotel;
    }



}
