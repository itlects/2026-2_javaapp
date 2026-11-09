package edu.week15;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
public class TaskController {
    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home() { return "redirect:/tasks"; }

    @GetMapping("/tasks")
    public String list(@RequestParam(defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page, Model model) {
        if (page < 0 || page > 100000 || q.length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        model.addAttribute("tasks", repository.find(q, page));
        model.addAttribute("count", repository.count(q));
        model.addAttribute("q", q);
        model.addAttribute("page", page);
        return "list";
    }

    @GetMapping("/tasks/new")
    public String create(Model model) {
        model.addAttribute("taskForm", new TaskForm());
        model.addAttribute("id", null);
        return "form";
    }

    @GetMapping("/tasks/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        Task task = repository.get(id);
        TaskForm form = new TaskForm();
        form.setTitle(task.title());
        form.setStatus(task.status());
        model.addAttribute("taskForm", form);
        model.addAttribute("id", id);
        return "form";
    }

    @PostMapping("/tasks")
    public String insert(@Valid @ModelAttribute TaskForm taskForm,
            BindingResult errors, Model model) {
        return save(null, taskForm, errors, model);
    }

    @PostMapping("/tasks/{id}")
    public String update(@PathVariable long id,
            @Valid @ModelAttribute TaskForm taskForm,
            BindingResult errors, Model model) {
        repository.get(id);
        return save(id, taskForm, errors, model);
    }

    private String save(Long id, TaskForm form,
            BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("id", id);
            return "form";
        }
        repository.save(id, form);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/delete")
    public String delete(@PathVariable long id) {
        repository.delete(id);
        return "redirect:/tasks";
    }
}
