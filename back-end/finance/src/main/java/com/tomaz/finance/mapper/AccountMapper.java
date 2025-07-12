package com.tomaz.finance.mapper;

import java.util.List; 

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.tomaz.finance.dto.AccountCreateDTO;
import com.tomaz.finance.dto.AccountResponseDTO;
import com.tomaz.finance.dto.AccountUpdateDTO;
import com.tomaz.finance.entities.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	
	Account toEntity(AccountCreateDTO dto);
	
	AccountResponseDTO toResponse(Account entity);
	
	List<AccountResponseDTO> accountFromAccountDTO(List<Account> accounts);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AccountUpdateDTO dto, @MappingTarget Account entity);
}
