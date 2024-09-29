package com.example.subject_management.service;

import com.example.subject_management.entity.Subject;
import com.example.subject_management.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public Optional<Subject> getSubjectByID(int id) {
        if (id <= 0) {
            return Optional.empty();
        }
        return subjectRepository.findById(id);
    }

    @Transactional
    public void saveSubject(Subject subject) {
        if (subject == null || subject.getSubject() == null || subject.getSubject().isEmpty() || subject.getCredits().isEmpty()) {
            throw new IllegalArgumentException("Invalid subject data");
        }
        subjectRepository.save(subject);
    }

    @Transactional
    public void deleteSubject(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid subject ID");
        }
        subjectRepository.deleteById(id);
    }
}