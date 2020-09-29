package gradle_spring_db_study.spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberDao {
	
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public MemberDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	
	
	public List<Member> selectAll() {
		String sql = "SELECT * FROM MEMBER";
		return jdbcTemplate.query(sql, new MemberRowMapper());
	}
	
	
	public Member selectByEmail(String email) {
		String sql = "SELECT ID, EMAIL, PASSWORD, NAME, REGDATE FROM MEMBER WHERE EMAIL = ?";
		
		List<Member> member = jdbcTemplate.query(sql, new MemberRowMapper(), email);
		return member.isEmpty() ? null : member.get(0);
	}
	
	
	public Member selectById(int id) {
		String sql = "SELECT ID, EMAIL, PASSWORD, NAME, REGDATE FROM MEMBER WHERE id = ?";
		
		Member member = jdbcTemplate.queryForObject(sql, new MemberRowMapper(), id);
		return member;
	}
	
	
	
	public void insert(Member member) {
		String sql = "INSERT INTO MEMBER(EMAIL, PASSWORD, NAME) VALUES(?, ?, ?)";
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(sql, new String[] {"id"});
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getPassword());
				pstmt.setString(3, member.getName());
//				pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
				
				return pstmt;
			}
		};
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(psc, keyHolder);
		Number keyValue = keyHolder.getKey();
		member.setId(keyValue.longValue());
	}
	
	
	public void update(Member member) {
		String sql = "UPDATE MEMBER SET NAME = ?, PASSWORD = ? WHERE EMAIL = ?";
		jdbcTemplate.update(sql, member.getName(), member.getPassword(), member.getEmail());
	}
	
	
	public void delete(Member member) {
		PreparedStatementCreator psc = new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				String sql = "DELETE MEMBER WHERE EMAIL = ?";
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member.getEmail());
				return pstmt;
			}
		};
		jdbcTemplate.update(psc);
	}

	
	
	public int count() {
		String sql = "SELECT COUNT(*) FROM MEMBER";
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
}
