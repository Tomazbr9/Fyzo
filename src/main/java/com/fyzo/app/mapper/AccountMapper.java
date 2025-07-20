package com.fyzo.app.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fyzo.app.dto.account.AccountRequestDTO;
import com.fyzo.app.dto.account.AccountResponseDTO;
import com.fyzo.app.dto.account.AccountUpdateDTO;
import com.fyzo.app.entities.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	
	Account toEntity(AccountRequestDTO dto);
	
	AccountResponseDTO toResponse(Account entity);
	
	List<AccountResponseDTO> accountFromAccountDTO(List<Account> accounts);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(AccountUpdateDTO dto, @MappingTarget Account entity);
}
