package com.gabor.party.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gabor.party.main.models.User;

/**
 * The repository for the User data model
 * @author dragos.gabor
 *
 */

@Repository
public interface UserRepository extends CrudRepository<User, Long>{}
