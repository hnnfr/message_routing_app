package com.hnn.msg.routing.repository;

import com.hnn.msg.routing.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

    @Query("SELECT p FROM Partner p WHERE LOWER(p.alias) LIKE LOWER(CONCAT('%', :alias, '%'))")
    List<Partner> findByAliasContainingIgnoreCase(@Param("alias") String alias);
}
