
delete  from task_tag;
delete  from tag;
delete from task;

DBCC CHECKIDENT (task_tag, RESEED, 0);
DBCC CHECKIDENT (tag, RESEED, 0);
DBCC CHECKIDENT (task, RESEED, 0);
