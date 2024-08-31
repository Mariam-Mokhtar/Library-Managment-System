package com.system.Library.application.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.Library.application.entity.Patron;
import com.system.Library.application.mapper.PatronMapper;
import com.system.Library.application.model.request.PatronReqModel;
import com.system.Library.application.model.response.PatronResModel;
import com.system.Library.application.repository.BorrowingRecordsRepository;
import com.system.Library.application.repository.PatronRepository;
import com.system.Library.application.service.PatronService;
import com.system.Library.utils.exception.enums.ApiErrorMessageEnum;
import com.system.Library.utils.exception.impl.BusinessLogicViolationException;

@Service
public class PatronServiceImpl implements PatronService {

	@Autowired
	PatronRepository patronRepository;

	@Autowired
	PatronMapper patronMapper;

	@Autowired
	BorrowingRecordsRepository borrowingRecordsRepository;

	@Override
	public List<PatronResModel> getAllPatrons() {
		List<Patron> patrons = patronRepository.findAll();
		return patronMapper.mapFromPatronToPatronResModel(patrons);
	}

	@Override
	public PatronResModel getPatronById(int id) {
		Optional<Patron> patron = patronRepository.findById(id);
		if (!patron.isPresent())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_ID_NOT_FOUND.name());
		return patronMapper.mapFromPatronToPatronResModel(patron.get());
	}

	@Override
	public void createPatron(PatronReqModel patronReqModel) {
		validateUniqueness(patronReqModel, 0);
		Patron patron = patronMapper.mapFromPatronReqModelToPatron(patronReqModel);
		patronRepository.save(patron);

	}

	@Override
	public PatronResModel updatePatronById(PatronReqModel patronReqModel, int id) {
		Optional<Patron> optionalPatron = patronRepository.findById(id);
		if (!optionalPatron.isPresent())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_ID_NOT_FOUND.name());
		validateUniqueness(patronReqModel, id);
		Patron patron = optionalPatron.get();
		patronMapper.updatePatronFromRequestModel(patronReqModel, patron);
		patronRepository.save(patron);
		return patronMapper.mapFromPatronToPatronResModel(patron);
	}

	private void validateUniqueness(PatronReqModel patronReqModel, int id) {
		if (patronRepository.existsByContactInfoEmailAndIdNot(patronReqModel.getContactInfo().getEmail(), id)) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_EMAIL_ALREADY_EXISTS.name());
		}
	}

	@Override
	@Transactional
	public void deletePatron(int id) {
		Optional<Patron> patron = patronRepository.findById(id);
		if (!patron.isPresent())
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_ID_NOT_FOUND.name());
		validatePatronNotBorrowingBook(id);
		borrowingRecordsRepository.deleteAllByPatronId(id);
		patronRepository.delete(patron.get());
	}

	private void validatePatronNotBorrowingBook(int id) {
		if (borrowingRecordsRepository.existsByPatronIdAndReturnDateIsNull(id)) {
			throw new BusinessLogicViolationException(ApiErrorMessageEnum.BCV_PATRON_IS_BORROWING_BOOK.name());
		}
	}
}
