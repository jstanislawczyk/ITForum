package itforum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import itforum.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
