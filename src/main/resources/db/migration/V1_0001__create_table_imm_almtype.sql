CREATE TABLE `imm_almtype` (
  `almType` int(11) NOT NULL COMMENT '警情类型编号',
  `almTypeName` varchar(32) CHARACTER SET gbk DEFAULT NULL COMMENT '警情类型名称',
  PRIMARY KEY (`almType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




