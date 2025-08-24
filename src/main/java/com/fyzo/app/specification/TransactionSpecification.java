package com.fyzo.app.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.fyzo.app.dto.transaction.TransactionFilterDTO;
import com.fyzo.app.entities.Transaction;
import com.fyzo.app.entities.User;

import jakarta.persistence.criteria.Predicate;

public class TransactionSpecification {
	
	public static Specification<Transaction> withFilters(TransactionFilterDTO filter, User user){
		return(root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			predicates.add(builder.equal(root.get("user"), user));
			
			if(filter.type() != null) {
				predicates.add(builder.equal(root.get("type"), filter.type()));
			}
			
			if(filter.categoryId() != null) {
				predicates.add(builder.equal(root.get("category").get("id"), filter.categoryId()));
			}
			
			
			if(filter.accountId() != null) {
				predicates.add(builder.equal(root.get("account").get("id"), filter.accountId()));
			}
			
			if (filter.year() != null && filter.month() != null) {
			    LocalDate startDate = LocalDate.of(filter.year(), filter.month(), 1);
			    LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

			    predicates.add(builder.between(root.get("date"), startDate, endDate));
			}

			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
		
		
	}
}
