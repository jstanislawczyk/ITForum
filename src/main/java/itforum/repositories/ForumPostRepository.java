package itforum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import itforum.entities.ForumPost;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long>{

}
