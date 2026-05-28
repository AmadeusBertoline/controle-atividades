package com.example.controleAtividades.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.controleAtividades.entity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}