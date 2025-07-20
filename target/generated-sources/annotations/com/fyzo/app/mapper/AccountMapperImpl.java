package com.fyzo.app.mapper;

import com.fyzo.app.dto.AccountCreateDTO;
import com.fyzo.app.dto.AccountResponseDTO;
import com.fyzo.app.dto.AccountUpdateDTO;
import com.fyzo.app.entities.Account;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-20T09:00:31-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class AccountMapperImpl implements AccountMapper {

    @Override
    public Account toEntity(AccountCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Account.AccountBuilder account = Account.builder();

        account.name( dto.name() );

        return account.build();
    }

    @Override
    public AccountResponseDTO toResponse(Account entity) {
        if ( entity == null ) {
            return null;
        }

        String name = null;
        BigDecimal balance = null;

        name = entity.getName();
        balance = entity.getBalance();

        AccountResponseDTO accountResponseDTO = new AccountResponseDTO( name, balance );

        return accountResponseDTO;
    }

    @Override
    public List<AccountResponseDTO> accountFromAccountDTO(List<Account> accounts) {
        if ( accounts == null ) {
            return null;
        }

        List<AccountResponseDTO> list = new ArrayList<AccountResponseDTO>( accounts.size() );
        for ( Account account : accounts ) {
            list.add( toResponse( account ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(AccountUpdateDTO dto, Account entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.name() != null ) {
            entity.setName( dto.name() );
        }
    }
}
