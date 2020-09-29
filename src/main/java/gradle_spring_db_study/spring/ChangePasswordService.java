package gradle_spring_db_study.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class ChangePasswordService {
	@Autowired
	private MemberDao memberDao;
	
	public void changePassword(String email, String oldPwd, String newPwd) {
		try {
			Member member = memberDao.selectByEmail(email);
			member.changePassword(oldPwd, newPwd);
			memberDao.update(member);
		} catch (EmptyResultDataAccessException e) {
			throw new MemberNotFoundException();
		}
	}

	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}
}
