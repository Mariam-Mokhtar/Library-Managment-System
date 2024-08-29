package com.system.Library.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.system.Library.application.entity.BorrowingRecords;
import com.system.Library.application.model.response.BorrowingRecordsResModel;

@Mapper(componentModel = "spring")
public interface BorrowingRecordsMapper {
	@Mappings({ @Mapping(source = "book.id", target = "bookId"), @Mapping(source = "patron.id", target = "patronId") })
	BorrowingRecordsResModel mapBorrowingRecordsToResModel(BorrowingRecords borrowingRecords);
}
