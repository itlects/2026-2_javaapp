package com.example.members.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.members.model.Member;
import com.example.members.service.MemberService;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping
    public String list(
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model) {
        List<Member> members = service.list(keyword);
        model.addAttribute("members", members);
        model.addAttribute("keyword", keyword.trim());
        model.addAttribute("count", members.size());
        return "members/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        model.addAttribute("member", service.detail(id));
        return "members/detail";
    }
}
