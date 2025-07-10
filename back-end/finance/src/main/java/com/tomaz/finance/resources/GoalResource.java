package com.tomaz.finance.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomaz.finance.dto.GoalCreateDTO;
import com.tomaz.finance.dto.GoalResponseDTO;
import com.tomaz.finance.dto.GoalUpdateDTO;
import com.tomaz.finance.entities.Goal;
import com.tomaz.finance.security.entities.UserDetailsImpl;
import com.tomaz.finance.services.GoalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/goals")
public class GoalResource {
	
	@Autowired
	private GoalService service;
	
	@GetMapping("/me")
	public ResponseEntity<List<GoalResponseDTO>> findAll(@AuthenticationPrincipal UserDetailsImpl userDetails){
		List<GoalResponseDTO> goals = service.findAll(userDetails.getUsername());
		return ResponseEntity.ok().body(goals);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Goal> create(@Valid @RequestBody GoalCreateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
	    Goal obj = service.create(dto, userDetails.getUsername());
	    return ResponseEntity.ok(obj);
	}
	
	@PatchMapping("update/{id}")
	public ResponseEntity<Goal> update(@PathVariable Long id, @RequestBody  GoalUpdateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		Goal obj = service.update(id, dto, userDetails.getUsername());
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
		service.delete(id, userDetails.getUsername());
		return ResponseEntity.noContent().build();
	}

}
