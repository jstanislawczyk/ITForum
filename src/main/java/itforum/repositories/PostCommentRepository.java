package itforum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import itforum.entities.PostComment;

public interface PostCommentRepository extends JpaRepository<PostComment, Long>{

}
