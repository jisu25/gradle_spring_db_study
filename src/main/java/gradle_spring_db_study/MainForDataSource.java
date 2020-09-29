package gradle_spring_db_study;

import java.time.LocalDateTime;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import gradle_spring_db_study.config.AppCtx;
import gradle_spring_db_study.spring.ChangePasswordService;
import gradle_spring_db_study.spring.Member;
import gradle_spring_db_study.spring.MemberDao;
import gradle_spring_db_study.spring.MemberNotFoundException;
import gradle_spring_db_study.spring.WrongIdPasswordException;

public class MainForDataSource {

	private static MemberDao memberDao;
	
	public static void main(String[] args) {
		try(AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class)) {
			DataSource ds = ctx.getBean(DataSource.class);
			memberDao = ctx.getBean(MemberDao.class);
			
			memberDao.insert(new Member("banana@gmail.com", "1234", "banana", LocalDateTime.now()));
			System.out.println("회원을 추가했습니다.\n");
			
			ChangePasswordService cps = ctx.getBean(ChangePasswordService.class);
			cps.changePassword("banana@gmail.com", "1234", "1111");
			System.out.println("암호를 변경했습니다.\n");
			
			Member member = memberDao.selectByEmail("banana@gmail.com");
			memberDao.delete(member);
			System.out.println("회원을 삭제 했습니다.");
			
//			selectByEmail();
//			selectAll();
		} catch (MemberNotFoundException e) {
			System.err.println("존재하지 않는 이메일입니다.");
		} catch (WrongIdPasswordException e) {
			System.err.println("이메일과 암호가 일치하지 않습니다.");
		}
	}

	private static void selectAll() {
		System.out.println("selectAll()");
		int total = memberDao.count();
		System.out.println("전체 데이터 : " + total + "건");
		List<Member> members = memberDao.selectAll();
		for(Member member : members) {
			System.out.printf("%d : %s : %s\n", member.getId(), member.getEmail(), member.getName());
		}
	}

	private static void selectByEmail() {
		System.out.println("selectByEmail()");
		Member findMem = memberDao.selectByEmail("test@test.co.kr");
		System.out.printf("%d : %s : %s", findMem.getId(), findMem.getEmail(), findMem.getName());
	}
}
