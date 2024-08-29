package com.system.Library.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.system.Library.application.entity.Patron;
import com.system.Library.application.model.request.PatronReqModel;
import com.system.Library.application.model.response.PatronResModel;

@Mapper(componentModel = "spring", uses = { ContactInfoMapper.class })
public interface PatronMapper {
	List<PatronResModel> mapFromPatronToPatronResModel(List<Patron> patron);

	PatronResModel mapFromPatronToPatronResModel(Patron patron);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "borrowingRecords", ignore = true) })
	Patron mapFromPatronReqModelToPatron(PatronReqModel patronReqModel);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "borrowingRecords", ignore = true) })
	void updatePatronFromRequestModel(PatronReqModel patronReqModel, @MappingTarget Patron patron);
}
