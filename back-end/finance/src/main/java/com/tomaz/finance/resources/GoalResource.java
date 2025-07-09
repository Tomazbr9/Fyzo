package com.tomaz.finance.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomaz.finance.dto.GoalCreateDTO;
import com.tomaz.finance.entities.Goal;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.GoalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/goals")
public class GoalResource {
	
	@Autowired
	private GoalService service;
	
	@PostMapping("/create")
	public ResponseEntity<Goal> create(@Valid @RequestBody GoalCreateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
	    Goal goal = service.create(dto, userDetails.getUsername());
	    return ResponseEntity.ok(goal);
	}

}
