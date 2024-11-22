package com.example.cvuser.repository;

import java.util.Optional;
import com.example.cvuser.model.UserModel;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface UserRepository extends JpaRepositoryImplementation<UserModel, Integer> 
{
    Optional<UserModel> findByLoginAndPassword(String login, String password);

    boolean existsByEmail(String email); // Checks if an email exists in the database
    boolean existsByLogin(String login); // Checks if a username (login) exists

}
