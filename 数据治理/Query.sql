-- 按月查询总体销售额
select A.month,count(A.month) from (
	select time,EXTRACT(MONTH FROM time)as month from data_homework.orders group by time
)as A
group by A.month

-- 按月查询新增注册人数
select A.month,count(A.month) as person_num from (
	select register_time,EXTRACT(MONTH FROM register_time)as month from data_homework.user_register group by register_time
)as A
group by A.month

--按月查看总体的销售额
select A.month,count(A.month) as person_num from (
	select register_time,EXTRACT(MONTH FROM register_time)as month from data_homework.user_register group by register_time
)as A
group by A.month

--按月查看城市的销售额
select A.city,A.month ,count(A.month)
from (select time,city,EXTRACT(MONTH FROM time)as month from data_homework.orders group by time,city) as A
group by A.city,A.month

--按月查看城市、商品的类别销售额(两个纬度：城市、商品类别)
select D.city,D.good_type,count(D.month)
from
	(select  C.city,EXTRACT(MONTH FROM C.time)as month,C.good_type
	from 
		(SELECT  A.city, A."time", B.good_type 
		FROM data_homework.orders as A INNER JOIN data_homework.goods as B
		ON A.order_id=B.goods_id) as C
	group by C.city,C.time,C.good_type) as D
group by D.city,D.good_type

--按月查看性别、商品类型的销售额（两个维度：性别、商品类别）
select E.gender,E.good_type,count(E.month)
from 
	(select D.gender,EXTRACT(MONTH FROM D.time)as month,D.good_type
	from
		(SELECT  C.gender , A."time",B.good_type 
		FROM data_homework.orders as A 
			INNER JOIN data_homework.goods as B 
			ON A.order_id=B.goods_id 
			INNER JOIN data_homework.users as C
			ON A.order_id =C.order_id ) as D
	group by D.gender,D.time,D.good_type ) as E
group by E.gender,E.good_type