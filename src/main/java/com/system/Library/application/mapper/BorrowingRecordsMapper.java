package com.system.Library.application.mapper;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.system.Library.application.entity.Book;
import com.system.Library.application.entity.BorrowingRecords;
import com.system.Library.application.entity.Patron;
import com.system.Library.application.model.response.BorrowingRecordsResModel;

@Mapper(componentModel = "spring", uses = { PatronMapper.class, BookMapper.class })
public interface BorrowingRecordsMapper {
	BorrowingRecordsResModel mapBorrowingRecordsToResModel(BorrowingRecords borrowingRecords);

	@Mappings({ @Mapping(target = "id", ignore = true), @Mapping(target = "returnDate", ignore = true),
			@Mapping(target = "createdDate", ignore = true), @Mapping(target = "lastModifiedDate", ignore = true),
			@Mapping(target = "lastModifiedBy", ignore = true), @Mapping(target = "createdBy", ignore = true) })
	BorrowingRecords mapToBorrowingRecord(Book book, Patron patron, LocalDate borrowingDate);

}
