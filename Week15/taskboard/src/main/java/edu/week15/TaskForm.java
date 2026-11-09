package edu.week15;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TaskForm {
    @NotBlank(message = "제목을 입력하세요.")
    @Size(max = 100, message = "제목은 100자 이내입니다.")
    private String title = "";

    @NotBlank(message = "상태를 선택하세요.")
    @Pattern(regexp = "TODO|DOING|DONE", message = "올바른 상태를 선택하세요.")
    private String status = "TODO";

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
