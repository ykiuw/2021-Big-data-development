-- ���²�ѯ�������۶�
select A.month,count(A.month) from (
	select time,EXTRACT(MONTH FROM time)as month from data_homework.orders group by time
)as A
group by A.month

-- ���²�ѯ����ע������
select A.month,count(A.month) as person_num from (
	select register_time,EXTRACT(MONTH FROM register_time)as month from data_homework.user_register group by register_time
)as A
group by A.month

--���²鿴��������۶�
select A.month,count(A.month) as person_num from (
	select register_time,EXTRACT(MONTH FROM register_time)as month from data_homework.user_register group by register_time
)as A
group by A.month

--���²鿴���е����۶�
select A.city,A.month ,count(A.month)
from (select time,city,EXTRACT(MONTH FROM time)as month from data_homework.orders group by time,city) as A
group by A.city,A.month

--���²鿴���С���Ʒ��������۶�(����γ�ȣ����С���Ʒ���)
select D.city,D.good_type,count(D.month)
from
	(select  C.city,EXTRACT(MONTH FROM C.time)as month,C.good_type
	from 
		(SELECT  A.city, A."time", B.good_type 
		FROM data_homework.orders as A INNER JOIN data_homework.goods as B
		ON A.order_id=B.goods_id) as C
	group by C.city,C.time,C.good_type) as D
group by D.city,D.good_type

--���²鿴�Ա���Ʒ���͵����۶����ά�ȣ��Ա���Ʒ���
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