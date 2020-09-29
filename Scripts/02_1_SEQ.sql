/* MEMBER SEQ & TRIGGER */
DROP SEQUENCE MEMBER_ID_SEQ;

CREATE SEQUENCE MEMBER_ID_SEQ
	START WITH 1
	INCREMENT BY 1
	NOCACHE;


CREATE OR REPLACE TRIGGER TRI_MEMBER_ID_SEQ
BEFORE INSERT ON MEMBER
FOR EACH ROW 
BEGIN
	IF Inserting AND :NEW.ID IS NULL THEN
		SELECT MEMBER_ID_SEQ.NEXTVAL INTO :NEW.ID FROM DUAL;
END IF;
END;