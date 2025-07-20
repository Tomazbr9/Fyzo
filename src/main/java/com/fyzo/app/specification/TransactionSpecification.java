package com.fyzo.app.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.fyzo.app.dto.TransactionFilterDTO;
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
			
			if(filter.startDate() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("date"), filter.startDate()));
			}
			
			if(filter.endDate() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("date"), filter.endDate()));
			}
			
			if(filter.maxAmount() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("amount"), filter.maxAmount()));
			}
			
			if(filter.minAmount() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("amount"), filter.minAmount()));
			}
			
			if(filter.accountId() != null) {
				predicates.add(builder.equal(root.get("account").get("id"), filter.accountId()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
		
		
	}
}
