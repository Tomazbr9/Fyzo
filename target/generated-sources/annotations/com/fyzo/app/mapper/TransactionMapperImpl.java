package com.fyzo.app.mapper;

import com.fyzo.app.dto.TransactionCreateDTO;
import com.fyzo.app.dto.TransactionResponseDTO;
import com.fyzo.app.dto.TransactionUpdateDTO;
import com.fyzo.app.entities.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-20T09:00:31-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toEntity(TransactionCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction.TransactionBuilder transaction = Transaction.builder();

        transaction.amount( dto.amount() );
        transaction.date( dto.date() );
        transaction.description( dto.description() );
        transaction.title( dto.title() );
        transaction.type( dto.type() );

        return transaction.build();
    }

    @Override
    public TransactionResponseDTO toResponse(Transaction entity) {
        if ( entity == null ) {
            return null;
        }

        String title = null;
        String description = null;
        BigDecimal amount = null;
        LocalDate date = null;

        title = entity.getTitle();
        description = entity.getDescription();
        amount = entity.getAmount();
        date = entity.getDate();

        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO( title, description, amount, date );

        return transactionResponseDTO;
    }

    @Override
    public void updateFromDto(TransactionUpdateDTO dto, Transaction entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.amount() != null ) {
            entity.setAmount( dto.amount() );
        }
        if ( dto.date() != null ) {
            entity.setDate( dto.date() );
        }
        if ( dto.description() != null ) {
            entity.setDescription( dto.description() );
        }
        if ( dto.title() != null ) {
            entity.setTitle( dto.title() );
        }
        if ( dto.type() != null ) {
            entity.setType( dto.type() );
        }
    }
}
