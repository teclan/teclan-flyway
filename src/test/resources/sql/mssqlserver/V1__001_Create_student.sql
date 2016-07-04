create table student (
  id bigint not null IDENTITY(1,1) PRIMARY KEY,
  name VARCHAR(56) NOT NULL,
  age int,
  sex varchar(10),
  enty_time DATETIME,
  created_at DATETIME,
  updated_at DATETIME
);



