package com.capg.bookingservice;

import com.capg.dto.BookingDetailsDTO;
import com.capg.entity.BookingDetails;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceApplicationTests {

	@Test
	void testBookingDetailsConstructor_ShouldCreateBookingDetailsObjectWithCorrectValues() {
		// Arrange
		BookingDetailsDTO bookingDetailsDTO = new BookingDetailsDTO();
		bookingDetailsDTO.setBookingId(1);
		bookingDetailsDTO.setFirstName("John");
		bookingDetailsDTO.setLastName("Doe");
		bookingDetailsDTO.setGender("Male");
		bookingDetailsDTO.setEmail("john.doe@example.com");
		bookingDetailsDTO.setPhoneNo("1234567890");
		bookingDetailsDTO.setRequiredSeats(2);
		bookingDetailsDTO.setFlightId(123);
		bookingDetailsDTO.setFlights(null);
		LocalDateTime bookedOn = LocalDateTime.now();
		LocalDateTime updatedOn = LocalDateTime.now();
		bookingDetailsDTO.setBookedOn(bookedOn);
		bookingDetailsDTO.setUpdatedOn(updatedOn);

		// Act
		BookingDetails bookingDetails = new BookingDetails(bookingDetailsDTO);

		// Assert
		assertEquals(1, bookingDetails.getBookingId());
		assertEquals("John", bookingDetails.getFirstName());
		assertEquals("Doe", bookingDetails.getLastName());
		assertEquals("Male", bookingDetails.getGender());
		assertEquals("john.doe@example.com", bookingDetails.getEmail());
		assertEquals("1234567890", bookingDetails.getPhoneNo());
		assertEquals(2, bookingDetails.getRequiredSeats());
		assertEquals(123, bookingDetails.getFlightId());
		assertNull(bookingDetails.getFlights());
		assertEquals(bookedOn, bookingDetails.getBookedOn());
		assertEquals(updatedOn, bookingDetails.getUpdatedOn());
	}

	@Test
	void testBookedTime_ShouldSetBookedOnToCurrentDateTime() {
		// Arrange
		BookingDetails bookingDetails = new BookingDetails();

		// Act
		bookingDetails.bookedTime();
		LocalDateTime bookedOn = bookingDetails.getBookedOn();

		// Assert
		assertNotNull(bookedOn);
	}

	@Test
	void testUpdatedTime_ShouldSetUpdatedOnToCurrentDateTime() {
		// Arrange
		BookingDetails bookingDetails = new BookingDetails();

		// Act
		bookingDetails.updatedTime();
		LocalDateTime updatedOn = bookingDetails.getUpdatedOn();

		// Assert
		assertNotNull(updatedOn);
	}

	@Test
	void testSettersAndGetters_ShouldSetAndRetrieveFieldValuesCorrectly() {
		// Arrange
		BookingDetails bookingDetails = new BookingDetails();

		// Act
		bookingDetails.setBookingId(1);
		bookingDetails.setFirstName("John");
		bookingDetails.setLastName("Doe");
		bookingDetails.setGender("Male");
		bookingDetails.setEmail("john.doe@example.com");
		bookingDetails.setPhoneNo("1234567890");
		bookingDetails.setRequiredSeats(2);
		bookingDetails.setFlightId(123);
		bookingDetails.setFlights(null);
		LocalDateTime bookedOn = LocalDateTime.now();
		bookingDetails.setBookedOn(bookedOn);
		LocalDateTime updatedOn = LocalDateTime.now();
		bookingDetails.setUpdatedOn(updatedOn);

		// Assert
		assertEquals(1, bookingDetails.getBookingId());
		assertEquals("John", bookingDetails.getFirstName());
		assertEquals("Doe", bookingDetails.getLastName());
		assertEquals("Male", bookingDetails.getGender());
		assertEquals("john.doe@example.com", bookingDetails.getEmail());
		assertEquals("1234567890", bookingDetails.getPhoneNo());
		assertEquals(2, bookingDetails.getRequiredSeats());
		assertEquals(123, bookingDetails.getFlightId());
		assertNull(bookingDetails.getFlights());
		assertEquals(bookedOn, bookingDetails.getBookedOn());
		assertEquals(updatedOn, bookingDetails.getUpdatedOn());
	}
}
