package com.fyzo.app.resources;

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

import com.fyzo.app.dto.GoalCreateDTO;
import com.fyzo.app.dto.GoalResponseDTO;
import com.fyzo.app.dto.GoalUpdateDTO;
import com.fyzo.app.security.entities.UserDetailsImpl;
import com.fyzo.app.services.GoalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/goals")
public class GoalResource {
	
	@Autowired
	private GoalService service;
	
	@GetMapping("/me")
	public ResponseEntity<List<GoalResponseDTO>> findAll(@AuthenticationPrincipal UserDetailsImpl userDetails){
		List<GoalResponseDTO> goals = service.findAll(userDetails);
		return ResponseEntity.ok().body(goals);
	}
	
	@PostMapping("/create")
	public ResponseEntity<GoalResponseDTO> create(@Valid @RequestBody GoalCreateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
	    GoalResponseDTO obj = service.create(dto, userDetails);
	    return ResponseEntity.ok(obj);
	}
	
	@PatchMapping("update/{id}")
	public ResponseEntity<GoalResponseDTO> update(@PathVariable Long id, @RequestBody  GoalUpdateDTO dto, @AuthenticationPrincipal UserDetailsImpl userDetails){
		GoalResponseDTO obj = service.update(id, dto, userDetails);
		return ResponseEntity.ok(obj);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
		service.delete(id, userDetails);
		return ResponseEntity.noContent().build();
	}

}
