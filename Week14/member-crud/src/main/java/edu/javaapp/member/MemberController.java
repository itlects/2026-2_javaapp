package edu.javaapp.member;

import jakarta.validation.Valid;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
public class MemberController {
    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("members", service.list());
        return "members/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        model.addAttribute("action", "/members");
        return "members/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute MemberForm memberForm,
            BindingResult result, Model model, RedirectAttributes redirect) {
        if (!result.hasErrors()) {
            try {
                service.create(memberForm);
            } catch (DuplicateKeyException ex) {
                result.rejectValue("email", "duplicate", "이미 등록된 이메일입니다.");
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("action", "/members");
            return "members/form";
        }
        redirect.addFlashAttribute("message", "회원이 등록되었습니다.");
        return "redirect:/members";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable long id, Model model) {
        Member member = service.get(id);
        MemberForm form = new MemberForm();
        form.setName(member.name());
        form.setEmail(member.email());
        form.setPhone(member.phone());
        model.addAttribute("memberForm", form);
        model.addAttribute("action", "/members/" + id);
        return "members/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable long id,
            @Valid @ModelAttribute MemberForm memberForm,
            BindingResult result, Model model, RedirectAttributes redirect) {
        service.get(id);
        if (!result.hasErrors()) {
            try {
                service.update(id, memberForm);
            } catch (DuplicateKeyException ex) {
                result.rejectValue("email", "duplicate", "이미 등록된 이메일입니다.");
            }
        }
        if (result.hasErrors()) {
            model.addAttribute("action", "/members/" + id);
            return "members/form";
        }
        redirect.addFlashAttribute("message", "회원이 수정되었습니다.");
        return "redirect:/members";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id, RedirectAttributes redirect) {
        service.delete(id);
        redirect.addFlashAttribute("message", "회원이 삭제되었습니다.");
        return "redirect:/members";
    }
}
