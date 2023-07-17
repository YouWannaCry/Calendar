package com.youwannacry.ywc.test.repository;

import com.youwannacry.ywc.test.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends JpaRepository<UserEntity, Object> {

    @Query(value = "SELECT secret FROM private.\"messages\" WHERE identifier = :identifier", nativeQuery = true)
    String checkMessage(@Param("identifier") String identifier);

    @Query(value = "SELECT * FROM public.\"users\" WHERE usuario = :usuario", nativeQuery = true)
    UserEntity findUser(@Param("usuario") String usuario);

    @Query(value = "SELECT ID FROM public.\"users\" WHERE usuario = :usuario",nativeQuery = true)
    Integer findUsername(@Param("usuario")String usuario);
}