package com.project.demo.logic.entity.reportProblems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportProblemsRepository extends JpaRepository<ReportProblems, Long> {
    // Método para buscar reportes por área del problema
    @Query("SELECT rp FROM ReportProblems rp WHERE rp.problemArea = :problemArea")
    List<ReportProblems> findByProblemArea(@Param("problemArea") String problemArea);

    // Método para buscar reportes por estado
    @Query("SELECT rp FROM ReportProblems rp WHERE rp.status = :status")
    List<ReportProblems> findByStatus(@Param("status") String status);

    // Método para listar todos los reportes (heredado de JpaRepository)
}
