package com.example.subject_management.controller;

import com.example.subject_management.entity.Subject;
import com.example.subject_management.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        List<Subject> subjects = subjectService.getAllSubjects();
        if (subjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubject(@PathVariable int id) {
        if (id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Subject> subject = subjectService.getSubjectByID(id);
        return subject.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<String> addSubject(@RequestBody Subject subject) {
        if (subject == null || subject.getSubject() == null || subject.getSubject().isEmpty() || subject.getCredits().isEmpty()) {
            return new ResponseEntity<>("Invalid subject data", HttpStatus.BAD_REQUEST);
        }
        subjectService.saveSubject(subject);
        return new ResponseEntity<>("Subject created successfully", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSubject(@PathVariable int id, @RequestBody Subject subjectDetails) {
        if (id <= 0 || subjectDetails == null || subjectDetails.getSubject().isEmpty() || subjectDetails.getCredits().isEmpty()) {
            return new ResponseEntity<>("Invalid subject data or ID", HttpStatus.BAD_REQUEST);
        }

        Optional<Subject> subjectOptional = subjectService.getSubjectByID(id);
        if (subjectOptional.isEmpty()) {
            return new ResponseEntity<>("Subject not found", HttpStatus.NOT_FOUND);
        }

        Subject subject = subjectOptional.get();
        subject.setSubject(subjectDetails.getSubject());
        subject.setCredits(subjectDetails.getCredits());
        subjectService.saveSubject(subject);

        return new ResponseEntity<>("Subject updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubject(@PathVariable int id) {
        if (id <= 0) {
            return new ResponseEntity<>("Invalid subject ID", HttpStatus.BAD_REQUEST);
        }

        Optional<Subject> subjectOptional = subjectService.getSubjectByID(id);
        if (subjectOptional.isEmpty()) {
            return new ResponseEntity<>("Subject not found", HttpStatus.NOT_FOUND);
        }

        subjectService.deleteSubject(id);
        return new ResponseEntity<>("Subject deleted successfully", HttpStatus.OK);
    }
}