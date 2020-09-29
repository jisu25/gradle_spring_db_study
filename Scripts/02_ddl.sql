/* 회원 */
CREATE TABLE MEMBER (
	id NUMBER(20) PRIMARY KEY, /* 회원아이디 */
	email VARCHAR2(40), /* 회원이메일 */
	PASSWORD VARCHAR2(20), /* 회원암호 */
	name VARCHAR2(40), /* 회원이름 */
	regdate DATE DEFAULT sysdate /* 가입일 */
);

