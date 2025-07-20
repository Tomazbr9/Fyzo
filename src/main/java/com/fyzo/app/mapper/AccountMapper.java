package com.fyzo.app.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fyzo.app.dto.AccountCreateDTO;
import com.fyzo.app.dto.AccountResponseDTO;
import com.fyzo.app.dto.AccountUpdateDTO;
import com.fyzo.app.entities.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	
	Account toEntity(AccountCreateDTO dto);
	
	AccountResponseDTO toResponse(Account entity);
	
	List<AccountResponseDTO> accountFromAccountDTO(List<Account> accounts);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AccountUpdateDTO dto, @MappingTarget Account entity);
}
