package com.example.webforumapp.repositories;

import com.example.webforumapp.models.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface LogRepository extends JpaRepository<Log,Integer> {


}
