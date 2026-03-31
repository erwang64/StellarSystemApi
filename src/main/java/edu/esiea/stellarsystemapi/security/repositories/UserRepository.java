package edu.esiea.stellarsystemapi.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.esiea.stellarsystemapi.security.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    

    Optional<User> findByLogin(String login);
}
