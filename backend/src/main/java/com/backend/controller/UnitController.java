package com.backend.controller;

import com.backend.model.Unit;
import com.backend.repository.UnitRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitController {
    private final UnitRepository unitRepository;

    public UnitController(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @GetMapping("/subject/{subjectId}")
    public List<Unit> getUnitsBySubjectId(@PathVariable Long subjectId) {
        return unitRepository.findBySubjectId(subjectId);
    }
} 