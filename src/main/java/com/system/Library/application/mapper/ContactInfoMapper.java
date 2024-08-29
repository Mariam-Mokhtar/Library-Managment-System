package com.system.Library.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.system.Library.application.entity.ContactInfo;
import com.system.Library.application.model.request.ContactInfoReqModel;
import com.system.Library.application.model.response.ContactInfoResModel;

@Mapper(componentModel = "spring", uses = { AddressMapper.class })

public interface ContactInfoMapper {
	ContactInfoResModel mapFromContactInfoToContactInfoResModel(ContactInfo contactInfo);

	ContactInfo mapFromContactInfoReqModelToContactInfo(ContactInfoReqModel contactInfoReqModel);

	void updateContactInfoFromRequestModel(ContactInfoReqModel contactInfoReqModel,
			@MappingTarget ContactInfo contactInfo);
}
