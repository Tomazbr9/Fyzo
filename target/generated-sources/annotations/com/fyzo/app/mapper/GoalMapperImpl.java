package com.fyzo.app.mapper;

import com.fyzo.app.dto.GoalCreateDTO;
import com.fyzo.app.dto.GoalResponseDTO;
import com.fyzo.app.dto.GoalUpdateDTO;
import com.fyzo.app.entities.Goal;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class GoalMapperImpl implements GoalMapper {

    @Override
    public Goal toEntity(GoalCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Goal.GoalBuilder goal = Goal.builder();

        goal.name( dto.name() );
        goal.targetAmount( dto.targetAmount() );
        goal.targetDate( dto.targetDate() );

        return goal.build();
    }

    @Override
    public GoalResponseDTO toResponse(Goal entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        BigDecimal targetAmount = null;
        BigDecimal savedAmount = null;
        LocalDate targetDate = null;
        boolean completed = false;

        id = entity.getId();
        name = entity.getName();
        targetAmount = entity.getTargetAmount();
        savedAmount = entity.getSavedAmount();
        targetDate = entity.getTargetDate();
        completed = entity.isCompleted();

        GoalResponseDTO goalResponseDTO = new GoalResponseDTO( id, name, targetAmount, savedAmount, targetDate, completed );

        return goalResponseDTO;
    }

    @Override
    public List<GoalResponseDTO> goalsFromGoalsDTO(List<Goal> goals) {
        if ( goals == null ) {
            return null;
        }

        List<GoalResponseDTO> list = new ArrayList<GoalResponseDTO>( goals.size() );
        for ( Goal goal : goals ) {
            list.add( toResponse( goal ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(GoalUpdateDTO dto, Goal entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.name() != null ) {
            entity.setName( dto.name() );
        }
        if ( dto.savedAmount() != null ) {
            entity.setSavedAmount( dto.savedAmount() );
        }
        if ( dto.targetAmount() != null ) {
            entity.setTargetAmount( dto.targetAmount() );
        }
        if ( dto.targetDate() != null ) {
            entity.setTargetDate( dto.targetDate() );
        }
    }
}
