package com.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.driver.model.Spot;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer>{

}
