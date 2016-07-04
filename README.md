
数据库迁移简单使用（flyway）

 关于Activejdbc,所有的表必须有 id 字段,并且是自增的,例如mysql数据库表结构应该如下:
 
 create table worker (
  id bigint not null AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(56) NOT NULL,
  ...
  ...
  entry_time DATETIME,
  created_at DATETIME,
  updated_at DATETIME
  )
  
  
  则应该有 
  
  @Table("worker")
  class Worker extends Model {  }
  
 如果表 worker 不存上述 id 字段,则在调用 Worker.create()或 Worker.createIt()
 或者 set()方法都不生效(save()之后也不报错)
 
 另外表中的 created_at 和 updated_at 是不允许手动设值的