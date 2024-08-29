package com.system.Library.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;

import com.system.Library.application.entity.Book;
import com.system.Library.application.model.request.BookReqModel;
import com.system.Library.application.model.response.BookResModel;

@Mapper(componentModel = "spring")
public interface BookMapper {

	@Mapping(source = "publish_year", target = "publishYear")
	BookResModel mapFromBookToBookResModel(Book book);

	List<BookResModel> mapFromBookToBookResModel(List<Book> book);

	List<BookResModel> mapFromBookToBookResModel(Page<Book> book);

	@Mappings({ @Mapping(source = "publishYear", target = "publish_year"), @Mapping(target = "id", ignore = true),
			@Mapping(target = "borrowingRecords", ignore = true) })
	Book mapFromBookReqModelToBook(BookReqModel bookReqModel);

	@Mappings({ @Mapping(source = "publishYear", target = "publish_year"), @Mapping(target = "id", ignore = true),
			@Mapping(target = "borrowingRecords", ignore = true) })
	void updateBookFromRequestModel(BookReqModel bookReqModel, @MappingTarget Book book);

}
