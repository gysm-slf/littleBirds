何为共享锁？
写事务时添加共享锁，其他多个事务只能读数据不能改数据。
何为排他锁？
如果一个事务对数据行添加了排他锁（InnoDB引擎的update、delete、insert操作都会对数据行添加排他锁），其他事务不能再在其上添加其他锁。select 。。 for update就是排他锁。select ..lock share mode是共享锁。
写事务提交前其它事务仍然能读

read no comm
读时不加锁；写时加行级共享锁；
脏读
salary=10000
t1              t2
start           start
s+1000          
                读取s 为11000
rollback
                s为无效值
                commit
                
不可重复读
salary=10000
t1              t2
start           start
                读取s 为10000
s+1000          读取s 为11000
s=150           读取s 为150
                每次读取值都不同 因为被其它事务更改了
commit(roll)
                commit
                
幻读
查询 明明更新所有 但是t2又新增了一条数据 t1更新后再次查询却发现好像没有全部更新一样
t1              t2
start           start
all s =1.5w
(所有行加锁,但是
可以添加新的行)          
                insert s = 8000
commit
                commit
                
                
read commit
写时加行级排他锁；事务结束才释放；读事务会加行级共享锁，但是读完后立即释放，而不是事务结束释放。读事务第一次读取数据结束后释放共享锁，此时其他事务可以更新数据，该事务第二次读取时发现与第一次数据值不同。
何为排他锁？

解决脏读
salary=10000
t1              t2
start           start
s+1000          
                读取s 为10000
rollback
                读取s 为10000
                commit
                
t1              t2
start           start
s+1000          
                读取s 为10000
commit
                读取s 为11000
                读取的均为有效数据
                commit
                
不可重复读
salary=10000
t1              t2
start           start
                读取s 为10000
s+1000          读取s 为10000
s=150           读取s 为10000
commit
                读取s 为150 ？？？？  确认下这里需不需要commit才能读取t1 commit的值
                读取值不同 因为被其它事务更改了
                commit
                
幻读
查询 明明更新所有 但是t2又新增了一条数据 t1更新后再次查询却发现好像没有全部更新一样
t1              t2
start           start
all s =1.5w          
                insert s = 8000
                commit
select
明明update all
但是有一个没生效
的感觉
commit

                
repeat read
<!--  
    读不加锁，写加行级锁，读已提交的
-->
读事务和写事务加的锁都持续到事务结束才释放。读或写事务只是对其所读的行加了行级锁，此时其他事务不能更新这些行，但是可以添加新的行。
解决脏读
salary=10000
t1              t2
start           start
s+1000
读取s 为11000
                读取s 为10000
commit
                读取s 为10000(但是实际已经更新为 11000了)
                当t1事务提交后,t2事务进行s+1000(写的时候会使用最新值而读的时候[如果t2事务不进行更新等操作]仍然为之前的值 这就是可重复读)
                读取s为12000(可以看到当前刷新值)
                commit
                读取s 为11000
解决不可重复读
salary=10000
t1              t2
start           start
                读取s 为10000
s+1000          读取s 为10000
s=150           读取s 为10000
commit
line a          s+1000
line b          读取s 为1150
                commit
                读取s 为1150(删除line a、b读取s为150)
                
幻读
查询 明明更新所有 但是t2又新增了一条数据 t1更新后再次查询却发现好像没有全部更新一样
t1              t2
start           start
all s =1.5w          
                insert s = 8000
commit
                commit
                
Serializable
t1读id=206的
t2读id=206的
然后无论谁对206更新都会被阻塞;
t1更新206的;
t1可以读取206;
t2读取206会被阻塞;
t1 select * from table_a;
t2 insert into会被阻塞;
t1 select * from table_a where id = '206';
t2 可以insert成功;
t1 select * from table_a where id > '206';
t2 insert into会被阻塞;

脏读
salary=10000
t1              t2
start           start
读取s 为10000    
                读取s 为10000
s+1000会被阻塞    s+1000会被阻塞
                读取s 为10000
commit;
                读取s 为10000
                commit
                
不可重复读
salary=10000
t1              t2
start           start
                读取s 为10000
s+1000          读取s 为11000
s=150           读取s 为150
                每次读取值都不同 因为被其它事务更改了
commit(roll)
                commit
                
幻读
查询 明明更新所有 但是t2又新增了一条数据 t1更新后再次查询却发现好像没有全部更新一样
t1              t2
start           start
all s =1.5w          
                insert s = 8000
commit
                commit