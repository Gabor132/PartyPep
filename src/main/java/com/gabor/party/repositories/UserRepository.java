package com.gabor.party.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.gabor.party.main.models.dao.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * The repository for the User data model
 *
 * @author dragos.gabor
 */

@Repository()
public interface UserRepository extends JpaRepository<User, Long>{}
