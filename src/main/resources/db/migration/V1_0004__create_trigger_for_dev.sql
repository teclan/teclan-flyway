
DELIMITER //

drop trigger IF EXISTS tr_devinfo_insert//

CREATE  TRIGGER tr_devinfo_insert 
 AFTER INSERT ON imm_devinfo 
 FOR EACH ROW 
 update identifier_info set devUsed=1 where id=new.devId//



CREATE  or replace TRIGGER tr_devinfo_update
 AFTER UPDATE ON imm_devinfo 
 FOR EACH ROW
 BEGIN
 UPDATE identifier_info SET devUsed=0 WHERE id=old.devId;
 UPDATE identifier_info SET devUsed=1 WHERE id=new.devId;
 END//
 
 
 drop trigger IF EXISTS tr_devinfo_delete//

CREATE  TRIGGER tr_devinfo_delete 
BEFORE DELETE ON imm_devinfo 
FOR EACH ROW 
update identifier_info set devUsed=0 where id=old.devId//

DELIMITER ;
