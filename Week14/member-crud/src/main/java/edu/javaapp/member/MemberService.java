package edu.javaapp.member;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberDao dao;

    public MemberService(MemberDao dao) {
        this.dao = dao;
    }

    public List<Member> list() {
        return dao.findAll();
    }

    public Member get(long id) {
        return dao.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."));
    }

    @Transactional
    public void create(MemberForm form) {
        normalize(form);
        dao.insert(form);
    }

    @Transactional
    public void update(long id, MemberForm form) {
        get(id);
        normalize(form);
        dao.update(id, form);
    }

    @Transactional
    public void delete(long id) {
        if (dao.delete(id) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다.");
        }
    }

    private void normalize(MemberForm form) {
        form.setName(form.getName().strip());
        form.setEmail(form.getEmail().strip().toLowerCase(java.util.Locale.ROOT));
        form.setPhone(form.getPhone().strip());
    }
}
