package com.system.Library.application.service;

import java.util.List;

import com.system.Library.application.model.request.PatronReqModel;
import com.system.Library.application.model.response.PatronResModel;

public interface PatronService {

	List<PatronResModel> getAllPatrons();

	PatronResModel getPatronById(int id);

	void createPatron(PatronReqModel patronReqModel);

	PatronResModel updatePatronById(PatronReqModel patronReqModel, int id);

	void deletePatron(int id);

}
