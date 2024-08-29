package com.system.Library.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.system.Library.application.entity.Address;
import com.system.Library.application.model.request.AddressReqModel;
import com.system.Library.application.model.response.AddressResModel;

@Mapper(componentModel = "spring")
public interface AddressMapper {

	AddressResModel mapFromAdressToAddressResModel(Address address);

	Address mapFromAdressToAddressResModel(AddressReqModel addressReqModel);

	void updateContactInfoFromRequestModel(AddressReqModel addressReqModel, @MappingTarget Address address);
}
