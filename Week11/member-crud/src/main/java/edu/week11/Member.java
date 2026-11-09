package edu.week11;

// DTO: 요청 계층과 DB 계층 사이에서 회원 정보를 전달합니다.
public class Member {
    private long id;
    private String name;
    private String email;
    private String phone;

    public Member(long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
