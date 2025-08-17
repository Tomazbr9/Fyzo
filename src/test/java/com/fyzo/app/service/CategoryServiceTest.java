//package com.fyzo.app.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.fyzo.app.dto.category.CategoryRequestDTO;
//import com.fyzo.app.dto.category.CategoryResponseDTO;
//import com.fyzo.app.dto.category.CategoryUpdateDTO;
//import com.fyzo.app.entities.Category;
//import com.fyzo.app.entities.User;
//import com.fyzo.app.enums.TransactionType;
//import com.fyzo.app.mapper.CategoryMapper;
//import com.fyzo.app.repositories.CategoryRepository;
//import com.fyzo.app.security.entities.UserDetailsImpl;
//import com.fyzo.app.services.CategoryService;
//import com.fyzo.app.services.finder.CategoryFinder;
//import com.fyzo.app.services.finder.UserFinder;
//
//@ExtendWith(MockitoExtension.class)
//public class CategoryServiceTest {
//	
//	@InjectMocks
//	private CategoryService service;
//	
//	@Mock
//	private CategoryRepository categoryRepository;
//	
//	@Mock
//	private CategoryMapper categoryMapper;
//	
//	@Mock
//	private UserFinder userFinder;
//	
//	@Mock
//	private CategoryFinder categoryFinder;
//	
//	@Test
//	void shouldReturnAllCategoriesWithAuthenticatedUser() throws Exception {
//		
//		User mockUser = new User();
//		UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//		
//		List<Category> categoriesEntity = List.of(
//				new Category(1L, "Mercado", TransactionType.EXPENSE, "#000000", false, mockUser),
//				new Category(2L, "Salario", TransactionType.REVENUE, "#000000", false, mockUser)
//		);
//		
//		List<CategoryResponseDTO> expectedResponse = List.of(
//				new CategoryResponseDTO(1L, "Mercado", TransactionType.EXPENSE, "#000000"),
//				new CategoryResponseDTO(2L, "Salario", TransactionType.REVENUE, "#000000")
//		);
//		
//		when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(mockUser);
//		when(categoryRepository.findByUser(mockUser)).thenReturn(categoriesEntity);
//		when(categoryMapper.categoriesFromCategoriesDTO(categoriesEntity)).thenReturn(expectedResponse);
//		
//		List<CategoryResponseDTO> result = service.findAll(userDetails);
//		
//		assertNotNull(result);
//		assertEquals(expectedResponse, result);
//		
//		verify(userFinder).findByUsernameOrThrow(userDetails);
//		verify(categoryRepository).findByUser(mockUser);
//		verify(categoryMapper).categoriesFromCategoriesDTO(categoriesEntity);
//		
//	}
//	
//	@Test
//    void shouldCreateCategoryWithAuthenticatedUser() {
//       
//        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Aluguel", TransactionType.EXPENSE, "#000000");
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        User mockUser = new User();
//        
//        Category mockCategory = new Category();
//        mockCategory.setName("Aluguel");
//        mockCategory.setType(TransactionType.EXPENSE);
//        mockCategory.setColor("#000000");
//
//        CategoryResponseDTO expectedResponse = new CategoryResponseDTO(1L, "Aluguel", TransactionType.EXPENSE, "#000000");
//
//        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(mockUser);
//        when(categoryMapper.toEntity(requestDTO)).thenReturn(mockCategory);
//        when(categoryRepository.save(mockCategory)).thenReturn(mockCategory);
//        when(categoryMapper.toResponse(mockCategory)).thenReturn(expectedResponse);
//
//        CategoryResponseDTO response = service.create(requestDTO, userDetails);
//
//        
//        assertNotNull(response);
//        assertEquals(expectedResponse, response);
//     
//        verify(userFinder).findByUsernameOrThrow(userDetails);
//        verify(categoryMapper).toEntity(requestDTO);
//        verify(categoryRepository).save(mockCategory);
//        verify(categoryMapper).toResponse(mockCategory);
//    }
//	
//	@Test
//    void shouldUpdateCategoryWithAuthenticatedUser() {
//        // Arrange
//        Long categoryId = 1L;
//        CategoryUpdateDTO updateDTO = new CategoryUpdateDTO("Transporte", TransactionType.EXPENSE, "#123456");
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//
//        User user = new User();
//        Category existingCategory = new Category();
//        existingCategory.setId(categoryId);
//        existingCategory.setName("Antigo Nome");
//        existingCategory.setColor("#000000");
//
//        CategoryResponseDTO expectedResponse = new CategoryResponseDTO(
//                categoryId, "Transporte", TransactionType.EXPENSE, "#123456"
//        );
//
//        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
//        when(categoryFinder.findByIdAndUserOrThrow(categoryId, user)).thenReturn(existingCategory);
//        doNothing().when(categoryMapper).updateFromDto(updateDTO, existingCategory);
//        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);
//        when(categoryMapper.toResponse(existingCategory)).thenReturn(expectedResponse);
//
//        CategoryResponseDTO response = service.update(categoryId, updateDTO, userDetails);
//
//        assertNotNull(response);
//        assertEquals(expectedResponse, response);
//
//        verify(userFinder).findByUsernameOrThrow(userDetails);
//        verify(categoryFinder).findByIdAndUserOrThrow(categoryId, user);
//        verify(categoryMapper).updateFromDto(updateDTO, existingCategory);
//        verify(categoryRepository).save(existingCategory);
//        verify(categoryMapper).toResponse(existingCategory);
//    }
//	
//	@Test
//    void shouldDeleteCategoryWithAuthenticatedUser() {
//    
//        Long categoryId = 1L;
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        User user = new User();
//        Category category = new Category();
//        category.setId(categoryId);
//
//        when(userFinder.findByUsernameOrThrow(userDetails)).thenReturn(user);
//        when(categoryFinder.findByIdAndUserOrThrow(categoryId, user)).thenReturn(category);
//
//        service.delete(categoryId, userDetails);
//
//        verify(userFinder).findByUsernameOrThrow(userDetails);
//        verify(categoryFinder).findByIdAndUserOrThrow(categoryId, user);
//        verify(categoryRepository).delete(category);
//    }
//}
