package com.system.Library.application.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.system.Library.application.entity.Address;
import com.system.Library.application.entity.ContactInfo;
import com.system.Library.application.entity.Patron;
import com.system.Library.application.mapper.PatronMapper;
import com.system.Library.application.model.request.AddressReqModel;
import com.system.Library.application.model.request.ContactInfoReqModel;
import com.system.Library.application.model.request.PatronReqModel;
import com.system.Library.application.model.response.AddressResModel;
import com.system.Library.application.model.response.ContactInfoResModel;
import com.system.Library.application.model.response.PatronResModel;
import com.system.Library.application.repository.PatronRepository;
import com.system.Library.application.service.impl.PatronServiceImpl;
import com.system.Library.utils.exception.impl.BusinessLogicViolationException;

@ExtendWith(MockitoExtension.class)
public class PatronServiceImplTest {

	@InjectMocks
	private PatronServiceImpl patronService;

	@Mock
	private PatronRepository patronRepository;

	@Mock
	private PatronMapper patronMapper;

	private Patron patron;
	private PatronReqModel patronReqModel;
	private PatronResModel patronResModel;
	private ContactInfoResModel contactInfoResModel;
	private ContactInfoReqModel contactInfoReqModel;
	private AddressReqModel addressReqModel;
	private AddressResModel addressResModel;
	private ContactInfo contactInfo;
	private Address address;

	@BeforeEach
	void setUp() {
		// -------------Patron-------------
		address = new Address();
		address.setStreet("123 Main St");
		address.setCity("Anytown");
		address.setState("Anystate");
		address.setPostalCode("12345");

		contactInfo = new ContactInfo();
		contactInfo.setPhoneNum("12345678901");
		contactInfo.setEmail("test@example.com");
		contactInfo.setAddress(address);
		contactInfo.setWorkAddress(null);

		patron = new Patron();
		patron.setId(1);
		patron.setName("Test Patron");
		patron.setContactInfo(contactInfo);
		// -------------Patron Req Model-------------
		addressReqModel = new AddressReqModel();
		addressReqModel.setStreet("123 Main St");
		addressReqModel.setCity("Anytown");
		addressReqModel.setState("Anystate");
		addressReqModel.setPostalCode("12345");

		contactInfoReqModel = new ContactInfoReqModel();
		contactInfoReqModel.setPhoneNum("12345678901");
		contactInfoReqModel.setEmail("test@example.com");
		contactInfoReqModel.setAddress(addressReqModel);
		contactInfoReqModel.setWorkAddress(null);

		patronReqModel = new PatronReqModel();
		patronReqModel.setName("Test Patron");
		patronReqModel.setContactInfo(contactInfoReqModel);

		// -------------Patron Res Model------------
		addressResModel = new AddressResModel();
		addressResModel.setStreet("123 Main St");
		addressResModel.setCity("Anytown");
		addressResModel.setState("Anystate");
		addressResModel.setPostalCode("12345");

		contactInfoResModel = new ContactInfoResModel();
		contactInfoResModel.setPhoneNum("12345678901");
		contactInfoResModel.setEmail("test@example.com");
		contactInfoResModel.setAddress(addressResModel);
		contactInfoResModel.setWorkAddress(null);
		patronResModel = new PatronResModel();
		patronResModel.setName("Test Patron");

		patronResModel.setContactInfo(contactInfoResModel);
	}

	@Test
	void testGetAllPatrons() {
		List<Patron> patrons = List.of(patron);
		when(patronRepository.findAll()).thenReturn(patrons);
		when(patronMapper.mapFromPatronToPatronResModel(patrons)).thenReturn(List.of(patronResModel));

		List<PatronResModel> result = patronService.getAllPatrons();

		assertEquals(1, result.size());
		assertEquals("Test Patron", result.get(0).getName());
		assertEquals("test@example.com", result.get(0).getContactInfo().getEmail());
		verify(patronRepository, times(1)).findAll();
		verify(patronMapper, times(1)).mapFromPatronToPatronResModel(patrons);
	}

	@Test
	void testGetPatronById_Found() {
		when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
		when(patronMapper.mapFromPatronToPatronResModel(patron)).thenReturn(patronResModel);

		PatronResModel result = patronService.getPatronById(1);

		assertNotNull(result);
		assertEquals("Test Patron", result.getName());
		assertEquals("test@example.com", result.getContactInfo().getEmail());
		verify(patronRepository, times(1)).findById(1);
		verify(patronMapper, times(1)).mapFromPatronToPatronResModel(patron);
	}

	@Test
	void testGetPatronById_NotFound() {
		when(patronRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
			patronService.getPatronById(1);
		});

		assertEquals("BCV_PATRON_ID_NOT_FOUND", exception.getMessage());
		verify(patronRepository, times(1)).findById(1);
		verify(patronMapper, never()).mapFromPatronToPatronResModel(any(Patron.class));
	}

	@Test
	void testCreatePatron() {
		when(patronMapper.mapFromPatronReqModelToPatron(patronReqModel)).thenReturn(patron);
		when(patronRepository.existsByContactInfoEmailAndIdNot(anyString(), anyInt())).thenReturn(false);

		patronService.createPatron(patronReqModel);

		verify(patronRepository, times(1)).save(patron);
		verify(patronMapper, times(1)).mapFromPatronReqModelToPatron(patronReqModel);
	}

	@Test
	void testUpdatePatronById() {
		when(patronRepository.findById(1)).thenReturn(Optional.of(patron));
		when(patronRepository.existsByContactInfoEmailAndIdNot(anyString(), anyInt())).thenReturn(false);

		PatronResModel updatedPatron = new PatronResModel();
		updatedPatron.setName("Updated Patron");

		ContactInfoResModel updatedContactInfo = new ContactInfoResModel();
		updatedContactInfo.setEmail("updated@example.com"); // Updated email
		updatedContactInfo.setPhoneNum("12345678901");
		updatedContactInfo.setAddress(new AddressResModel());
		updatedContactInfo.setWorkAddress(null);

		updatedPatron.setContactInfo(updatedContactInfo);
		when(patronMapper.mapFromPatronToPatronResModel(patron)).thenReturn(updatedPatron);

		PatronResModel result = patronService.updatePatronById(patronReqModel, 1);

		assertEquals("Updated Patron", result.getName());
		assertEquals("updated@example.com", result.getContactInfo().getEmail());
		verify(patronRepository, times(1)).save(patron);
		verify(patronMapper, times(1)).updatePatronFromRequestModel(patronReqModel, patron);
		verify(patronMapper, times(1)).mapFromPatronToPatronResModel(patron);
	}

	@Test
	void testDeletePatron_Found() {
		when(patronRepository.findById(1)).thenReturn(Optional.of(patron));

		patronService.deletePatron(1);

		verify(patronRepository, times(1)).delete(patron);
	}

	@Test
	void testDeletePatron_NotFound() {
		when(patronRepository.findById(1)).thenReturn(Optional.empty());

		BusinessLogicViolationException exception = assertThrows(BusinessLogicViolationException.class, () -> {
			patronService.deletePatron(1);
		});

		assertEquals("BCV_PATRON_ID_NOT_FOUND", exception.getMessage());
		verify(patronRepository, never()).delete(any(Patron.class));
	}
}
