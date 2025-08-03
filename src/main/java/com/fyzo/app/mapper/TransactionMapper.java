package com.fyzo.app.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fyzo.app.dto.transaction.TransactionRequestDTO;
import com.fyzo.app.dto.transaction.TransactionResponseDTO;
import com.fyzo.app.dto.transaction.TransactionUpdateDTO;
import com.fyzo.app.entities.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
	
	Transaction toEntity(TransactionRequestDTO dto);
	
	TransactionResponseDTO toResponse(Transaction entity);
	
	List<TransactionResponseDTO> transactionsToTransactionsDTO(List<Transaction> transactions);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(TransactionUpdateDTO dto, @MappingTarget Transaction entity);
}

