package Dao;

import entity.Category;
import entity.user;

import java.util.List;

public interface CategoryDao {
    void addCategory(Category category);
    Category getCategoryByName(String name);
    public Category findCategoryByUserAndType(user username, String categoryType);
}
