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
        Integer aadharCardNo = generateRandomAadharCardNo();
        user.setaadharCardNo(aadharCardNo);
        // Add user to the database or perform any necessary operations
        return aadharCardNo;
    }

    private Integer generateRandomAadharCardNo() {
        Random random = new Random();
        return 100000000 + random.nextInt(900000000);
    }
    public String getHotelWithMostFacilities() {
        String hotelWithMostFacilities = "";
        int maxFacilities = 0;

        for (Hotel hotel : hotelDb.values()) {
            int numFacilities = hotel.getFacilities().size();
            if (numFacilities > maxFacilities) {
                maxFacilities = numFacilities;
                hotelWithMostFacilities = hotel.getHotelName();
            } else if (numFacilities == maxFacilities && hotel.getHotelName().compareTo(hotelWithMostFacilities) < 0) {
                hotelWithMostFacilities = hotel.getHotelName();
            }
        }

        return hotelWithMostFacilities;
    }
    public void bookARoom( Booking booking) {
        UUID bookingId = UUID.randomUUID();
        booking.setBookingId(bookingId.toString());

    }
    public int getBookings(Integer aadharCard) {
        // Get bookings done by a person based on aadharCard
        // Return the count of bookings
        return 0;
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
        hotelDb.put(hotel.getHotelName(), hotel);
        return hotel;
    }



}
