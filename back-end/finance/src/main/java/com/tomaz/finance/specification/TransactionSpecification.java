package com.tomaz.finance.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tomaz.finance.dto.TransactionFilterDTO;
import com.tomaz.finance.entities.Transaction;
import com.tomaz.finance.entities.User;

import jakarta.persistence.criteria.Predicate;

public class TransactionSpecification {
	
	public static Specification<Transaction> withFilters(TransactionFilterDTO filter, User user){
		return(root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			predicates.add(builder.equal(root.get("user"), user));
			
			if(filter.getType() != null) {
				predicates.add(builder.equal(root.get("type"), filter.getType()));
			}
			
			if(filter.getCategoryId() != null) {
				predicates.add(builder.equal(root.get("category").get("id"), filter.getCategoryId()));
			}
			
			if(filter.getStartDate() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("date"), filter.getStartDate()));
			}
			
			if(filter.getEndDate() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("date"), filter.getEndDate()));
			}
			
			if(filter.getMaxAmount() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("amount"), filter.getMaxAmount()));
			}
			
			if(filter.getMinAmount() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("amount"), filter.getMinAmount()));
			}
			
			if(filter.getAccountId() != null) {
				predicates.add(builder.equal(root.get("account").get("id"), filter.getAccountId()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
		
		
	}
}
