package com.tomaz.finance.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import com.tomaz.finance.dto.TransactionCreateDTO;
import com.tomaz.finance.dto.TransactionResponseDTO;
import com.tomaz.finance.dto.TransactionUpdateDTO;
import com.tomaz.finance.entities.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
	
	Transaction toEntity(TransactionCreateDTO dto);
	
	TransactionResponseDTO toResponse(Transaction entity);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TransactionUpdateDTO dto, @MappingTarget Transaction entity);
}

