package com.gabor.partypeps.repositories;

import com.gabor.partypeps.models.dao.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Modifying
    @Query("DELETE FROM Follow f WHERE f.id=:id")
    int deleteFollow(@Param("id") Long id);

}
