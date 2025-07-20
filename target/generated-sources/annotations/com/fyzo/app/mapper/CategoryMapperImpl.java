package com.fyzo.app.mapper;

import com.fyzo.app.dto.CategoryCreateDTO;
import com.fyzo.app.dto.CategoryResponseDTO;
import com.fyzo.app.dto.CategoryUpdateDTO;
import com.fyzo.app.entities.Category;
import com.fyzo.app.enums.TransactionType;
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
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toEntity(CategoryCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.color( dto.color() );
        category.name( dto.name() );
        category.type( dto.type() );

        return category.build();
    }

    @Override
    public CategoryResponseDTO toResponse(Category entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        TransactionType type = null;
        String color = null;

        id = entity.getId();
        name = entity.getName();
        type = entity.getType();
        color = entity.getColor();

        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO( id, name, type, color );

        return categoryResponseDTO;
    }

    @Override
    public List<CategoryResponseDTO> categoriesFromCategoriesDTO(List<Category> catgories) {
        if ( catgories == null ) {
            return null;
        }

        List<CategoryResponseDTO> list = new ArrayList<CategoryResponseDTO>( catgories.size() );
        for ( Category category : catgories ) {
            list.add( toResponse( category ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(CategoryUpdateDTO dto, Category entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.color() != null ) {
            entity.setColor( dto.color() );
        }
        if ( dto.name() != null ) {
            entity.setName( dto.name() );
        }
        if ( dto.type() != null ) {
            entity.setType( dto.type() );
        }
    }
}
