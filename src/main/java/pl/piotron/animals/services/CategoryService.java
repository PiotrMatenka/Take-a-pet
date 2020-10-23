package pl.piotron.animals.services;

import org.springframework.stereotype.Service;
import pl.piotron.animals.model.Category;
import pl.piotron.animals.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll ()
    {
        return new ArrayList<>(categoryRepository.findAll());
    }
    public List<String> getAllNames ()
    {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }
}
