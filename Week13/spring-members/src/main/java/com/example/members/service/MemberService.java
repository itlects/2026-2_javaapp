package com.example.members.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.members.model.Member;
import com.example.members.repository.MemberRepository;

@Service
public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public List<Member> list(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        return repository.searchByName(keyword.trim());
    }

    public Member detail(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
