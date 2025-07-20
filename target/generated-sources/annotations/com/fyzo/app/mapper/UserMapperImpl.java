package com.fyzo.app.mapper;

import com.fyzo.app.dto.UserCreateDTO;
import com.fyzo.app.dto.UserResponseDTO;
import com.fyzo.app.dto.UserUpdateDTO;
import com.fyzo.app.entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-20T09:00:32-0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20250213-1140, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.email() );
        user.password( dto.password() );
        user.username( dto.username() );

        return user.build();
    }

    @Override
    public UserResponseDTO toResponse(User entity) {
        if ( entity == null ) {
            return null;
        }

        String username = null;
        String email = null;

        username = entity.getUsername();
        email = entity.getEmail();

        UserResponseDTO userResponseDTO = new UserResponseDTO( username, email );

        return userResponseDTO;
    }

    @Override
    public List<UserResponseDTO> usersFromUsersDTO(List<User> goals) {
        if ( goals == null ) {
            return null;
        }

        List<UserResponseDTO> list = new ArrayList<UserResponseDTO>( goals.size() );
        for ( User user : goals ) {
            list.add( toResponse( user ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(UserUpdateDTO dto, User entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.email() != null ) {
            entity.setEmail( dto.email() );
        }
        if ( dto.password() != null ) {
            entity.setPassword( dto.password() );
        }
        if ( dto.username() != null ) {
            entity.setUsername( dto.username() );
        }
    }
}
