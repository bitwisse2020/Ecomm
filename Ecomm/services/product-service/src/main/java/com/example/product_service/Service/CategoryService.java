package com.example.product_service.Service;

import com.example.common.Mapper.CategoryMapper;
import com.example.product_service.DTO.CategoryRequest;
import com.example.common.DTO.CategoryResponse;
import com.example.product_service.DTO.UpdateCategoryRequest;
import com.example.product_service.Exception.CategoryNotFoundException;
import com.example.product_service.Exception.ResourceConflictException;
import com.example.product_service.Models.Category;
import com.example.product_service.Repository.CategoryRepository;
import com.example.product_service.Utility.SlugGenerator;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.product_service.Utility.CategoryMapperUtils.getCategoryEntity;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public CategoryResponse createCategory(@Valid CategoryRequest categoryRequest) throws CategoryNotFoundException, ResourceConflictException {
        logger.info("Attempting to create Category with name: {}", categoryRequest.getName());
        Category parentCategory = null;
        Long parentCategoryId = categoryRequest.getParentCategoryId();
        if (parentCategoryId != null) {
            parentCategory = categoryRepository.findById(parentCategoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("No such parent category exists with Id: " + parentCategoryId));
        }
        String baseSlug = SlugGenerator.generateSlug(categoryRequest.getName());
        String finalSlug = ensureUniqueCategorySlug(baseSlug, null);
        Category categoryEntity = getCategoryEntity(categoryRequest, parentCategory, finalSlug);
        Category category = categoryRepository.save(categoryEntity);
        return categoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) throws CategoryNotFoundException, ResourceConflictException {
        logger.info("Attempting to update category with ID: {}", id);
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category" + id));

        boolean slugNeedsUpdate = false;
        String newSlug = categoryToUpdate.getSlug();

        // Update name and potentially slug if name is provided and changed
        if (request.getName() != null && !request.getName().isBlank() && !request.getName().equals(categoryToUpdate.getName())) {
            logger.debug("Category name change detected for ID: {}. Old: '{}', New: '{}'", id, categoryToUpdate.getName(), request.getName());
            categoryToUpdate.setName(request.getName());
            newSlug = SlugGenerator.generateSlug(request.getName());
            slugNeedsUpdate = true;
        }

        // Update description if provided
        if (request.getDescription() != null) {
            categoryToUpdate.setDescription(request.getDescription());
        }
        // --- Parent Category Update Logic ---
        Category currentParent = categoryToUpdate.getParentCategory();
        Long currentParentId = (currentParent != null) ? currentParent.getId() : null;
        Long requestedParentId = request.getParentCategoryId(); // Can be null

        // Check if parent assignment is actually changing
        boolean parentChangeRequested = !java.util.Objects.equals(currentParentId, requestedParentId);

        if (parentChangeRequested) {
            logger.debug("Parent category change requested for category ID: {}. Current Parent ID: {}, Requested Parent ID: {}", id, currentParentId, requestedParentId);
            if (requestedParentId != null) {
                // Prevent setting category as its own parent
                if (id.equals(requestedParentId)) {
                    throw new ResourceConflictException("Category cannot be its own parent.");
                }

                // Fetch the potential new parent
                Category newParentCategory = categoryRepository.findById(requestedParentId)
                        .orElseThrow(() -> new CategoryNotFoundException("Parent Category: " + requestedParentId));

                // *** Check for Circular Dependency ***
                if (isCircularDependency(categoryToUpdate, newParentCategory)) {
                    throw new ResourceConflictException("Setting this parent category would create a circular dependency.");
                }

                // If checks pass, set the new parent
                categoryToUpdate.setParentCategory(newParentCategory);
                logger.debug("Set new parent (ID: {}) for category ID: {}", requestedParentId, id);

            } else {
                // Request is to make this a top-level category (remove parent)
                categoryToUpdate.setParentCategory(null);
                logger.debug("Removed parent for category ID: {}", id);
            }
        }
        // --- End Parent Category Update Logic ---

        // Ensure slug uniqueness if it was changed
        if (slugNeedsUpdate) {
            categoryToUpdate.setSlug(ensureUniqueCategorySlug(newSlug, id)); // Check uniqueness excluding self
        }

        Category updatedCategory = categoryRepository.save(categoryToUpdate);
        logger.info("Successfully updated category with ID: {}", updatedCategory.getId());
        return categoryMapper.toResponse(updatedCategory);
    }

    @Transactional
    public void deleteCategory(Long id) throws CategoryNotFoundException {
        logger.warn("Attempting to delete Category with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("No such category exists with Id: " + id));
        categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getSubCategories(Long parentId,Pageable pageable) throws CategoryNotFoundException {
        logger.debug("Attempting to fetch Category by parentCategoryId: {}", parentId);
        // Check if parent exists first
        if (!categoryRepository.existsById(parentId)) {
            throw new CategoryNotFoundException("Parent Category " +parentId);
        }
        return categoryMapper.toResponsePage(categoryRepository.findByParentCategoryId(parentId,pageable));
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryBySlug(String categorySlug) throws CategoryNotFoundException {
        logger.debug("Attempting to fetch Category by slug: {}", categorySlug);
        Category category = categoryRepository.findBySlug(categorySlug)
                .orElseThrow(() -> new CategoryNotFoundException("No such category exists with categorySlug: " + categorySlug));
        return categoryMapper.toResponse(category);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) throws CategoryNotFoundException {
        logger.debug("Attempting to fetch Category by id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("No such category exists with id: " + id));
        return categoryMapper.toResponse(category);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getTopLevelCategories(Pageable pageable) {
        logger.debug("Attempting to fetch top level Categories by page: {}", pageable);
        Page<Category> topLevelCategories = categoryRepository.findByParentCategoryIsNull(pageable);
        return categoryMapper.toResponsePage(topLevelCategories);
    }

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAllCategories(Pageable pageable) {
        logger.debug("Attempting to fetch all Categories by page: {}", pageable);
        Page<Category> categories = categoryRepository.findAll(pageable);
        return categoryMapper.toResponsePage(categories);
    }

    private String ensureUniqueCategorySlug(String baseSlug, Long currentId) throws ResourceConflictException {
        String finalSlug = baseSlug;
        int counter = 1;
        while (true) {
            boolean exists;
            if (currentId == null) {
                // Creating new: Check if slug exists at all
                exists = categoryRepository.findBySlug(finalSlug).isPresent();
            } else {
                // Updating: Check if slug exists for *another* category
                exists = categoryRepository.existsBySlugAndIdNot(finalSlug, currentId);
            }

            if (!exists) {
                break; // Slug is unique
            }

            // Slug exists, append counter and try again
            logger.warn("Slug collision detected for '{}'. Appending suffix.", finalSlug);
            finalSlug = SlugGenerator.appendSuffix(baseSlug, counter++);
            // Add a safety break to prevent infinite loops in unexpected scenarios
            if (counter > 100) {
                logger.error("Could not find unique slug for base '{}' after {} attempts.", baseSlug, counter);
                throw new ResourceConflictException("Could not generate unique slug for category name.");
            }
        }
        return finalSlug;
    }

    private boolean isCircularDependency(Category categoryToUpdate, Category potentialParent) {
        if (potentialParent == null) {
            return false; // Cannot have a cycle with a null parent
        }
        if (categoryToUpdate.getId() == null) {
            // Should not happen if categoryToUpdate is fetched correctly, but safety check
            return false;
        }

        logger.debug("Checking for circular dependency: Does {} exist in the ancestry of {}?", categoryToUpdate.getId(), potentialParent.getId());
        Set<Long> visited = new HashSet<>(); // To detect cycles within the check itself (though less likely with DB constraints)
        Category current = potentialParent;

        while (current != null) {
            // Check if the current node in the upward traversal is the category we are trying to update
            if (categoryToUpdate.getId().equals(current.getId())) {
                logger.warn("Circular dependency detected! Category ID {} is an ancestor of its proposed parent ID {}", categoryToUpdate.getId(), potentialParent.getId());
                return true; // Found the original category in the parent's ancestry - cycle!
            }

            // Prevent infinite loops in the check itself if data somehow has a cycle already
            if (!visited.add(current.getId())) {
                logger.error("Cycle detected during circular dependency check itself at category ID: {}. Data might be inconsistent.", current.getId());
                // Treat this as a potential problem - preventing the update is safer.
                return true;
            }

            // Move up to the next parent
            // Note: This might trigger lazy loading if parentCategory wasn't eagerly fetched.
            // Consider fetching the hierarchy more efficiently if performance becomes an issue.
            try {
                current = current.getParentCategory();
                // Eagerly load the next parent if needed (less efficient, but ensures data is available)
                // if (current != null && !org.hibernate.Hibernate.isInitialized(current.getParentCategory())) {
                //     org.hibernate.Hibernate.initialize(current.getParentCategory());
                // }
            } catch (org.hibernate.LazyInitializationException e) {
                // If lazy loading fails, we might need to fetch the parent explicitly
                logger.warn("LazyInitializationException while traversing category hierarchy. Fetching parent explicitly for ID: {}", current.getId());
                current = categoryRepository.findById(current.getId()).map(Category::getParentCategory).orElse(null);
            }

        }

        logger.debug("No circular dependency found for category ID {} with potential parent ID {}", categoryToUpdate.getId(), potentialParent.getId());
        return false; // Reached the top of the hierarchy without finding the original category
    }

}
