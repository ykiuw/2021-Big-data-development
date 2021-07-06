-- schema
--drop schema if exists data_homework;
--create schema data_homework;

-- �����̳Ǳ�
drop table if exists data_homework.MALL;
create table data_homework.MALL
(
  mall_id INT PRIMARY KEY,
  user_table INT,
  merchant_table   INT
);

-- Add comments to the table 
comment on table data_homework.MALL
  is '�̳�';
-- Add comments to the columns 
comment on column data_homework.MALL.mall_id
  is '�̳�id';
comment on column data_homework.MALL.user_table
  is '�û���ע��id';
comment on column data_homework.MALL.merchant_table
  is '�̼ұ�ע��id';
 
 
 
 
 -- �����û�ע���
drop table if exists data_homework.USER_REGISTER;
create table data_homework.USER_REGISTER
(
  register_id INT PRIMARY KEY,
  user_id INT,
  register_time  timestamp
);

-- Add comments to the table 
comment on table data_homework.USER_REGISTER
  is '�û�ע���';
-- Add comments to the columns 
comment on column data_homework.USER_REGISTER.register_id
  is '�û�ע���id';
comment on column data_homework.USER_REGISTER.user_id
  is '�û�id';
comment on column data_homework.USER_REGISTER.register_time
  is 'ע��ʱ��';
 
 
 
 
 -- �����̼���פ��
drop table if exists data_homework.MERCHANT_REGISTER;
create table data_homework.MERCHANT_REGISTER
(
  register_id INT PRIMARY KEY,
  merchant_id INT,
  register_time  timestamp
);

-- Add comments to the table 
comment on table data_homework.MERCHANT_REGISTER
  is '�̼���פ��';
-- Add comments to the columns 
comment on column data_homework.MERCHANT_REGISTER.register_id
  is '�̼���פ��id';
comment on column data_homework.MERCHANT_REGISTER.merchant_id
  is '�̼�id';
comment on column data_homework.MERCHANT_REGISTER.register_time
  is '��פʱ��';
 
 
 
 
  -- �����û���
drop table if exists data_homework.USERS;
create table data_homework.USERS
(
  user_id INT PRIMARY KEY,
  gender VARCHAR(1),
  age  INT,
  phone_number VARCHAR(11),
  order_id INT
);

-- Add comments to the table 
comment on table data_homework.USERS
  is '�û���';
-- Add comments to the columns 
comment on column data_homework.USERS.user_id
  is '�û�id';
comment on column data_homework.USERS.gender
  is '�Ա�';
comment on column data_homework.USERS.age
  is '����';
 comment on column data_homework.USERS.phone_number
  is '�ֻ���';
  comment on column data_homework.USERS.order_id
  is '������';
 
 
 
 
 
   -- �����̼ұ�
drop table if exists data_homework.MERCHANT;
create table data_homework.MERCHANT
(
  merchant_id INT PRIMARY KEY,
  gender VARCHAR(1),
  age  INT,
  phone_number VARCHAR(11),
  goods_id INT
);

-- Add comments to the table 
comment on table data_homework.MERCHANT
  is '�̼ұ�';
-- Add comments to the columns 
comment on column data_homework.MERCHANT.merchant_id
  is '�̼�id';
comment on column data_homework.MERCHANT.gender
  is '�Ա�';
comment on column data_homework.MERCHANT.age
  is '����';
 comment on column data_homework.MERCHANT.phone_number
  is '�ֻ���';
  comment on column data_homework.MERCHANT.goods_id
  is '�����';

 
 
 
 
 
    -- ����������
drop table if exists data_homework.ORDERS;
create table data_homework.ORDERS
(
  order_id INT PRIMARY KEY,
  place VARCHAR(30),
  city VARCHAR(30),
  time  timestamp
);

-- Add comments to the table 
comment on table data_homework.ORDERS
  is '������';
-- Add comments to the columns 
comment on column data_homework.ORDERS.order_id
  is '������';
comment on column data_homework.ORDERS.place
  is '�ջ��ص�';
comment on column data_homework.ORDERS.city
  is '�ջ�����';
 comment on column data_homework.ORDERS.time
  is '�µ�ʱ��';
  
 
 
 
     -- �����ϼܱ�
drop table if exists data_homework.SHOP;
create table data_homework.SHOP
(
  goods_id INT PRIMARY KEY,
  time  timestamp
);

-- Add comments to the table 
comment on table data_homework.SHOP
  is '�ϼܱ�';
-- Add comments to the columns 
comment on column data_homework.SHOP.goods_id
  is '��Ʒid';
 comment on column data_homework.SHOP.time
  is '�ϼ�ʱ��';
  
 
 
 
      -- ������Ʒ��
drop table if exists data_homework.GOODS;
create table data_homework.GOODS
(
  goods_id INT PRIMARY KEY,
  good_type VARCHAR(30)
);

-- Add comments to the table 
comment on table data_homework.GOODS
  is '��Ʒ��';
-- Add comments to the columns 
comment on column data_homework.GOODS.goods_id
  is '��Ʒid';
 comment on column data_homework.GOODS.good_type
  is '��Ʒ����';