package itforum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import itforum.entities.ForumCategory;

public interface ForumCategoryRepository extends JpaRepository<ForumCategory, Long>{

}
