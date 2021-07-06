-- schema
--drop schema if exists data_homework;
--create schema data_homework;

-- 创建商城表
drop table if exists data_homework.MALL;
create table data_homework.MALL
(
  mall_id INT PRIMARY KEY,
  user_table INT,
  merchant_table   INT
);

-- Add comments to the table 
comment on table data_homework.MALL
  is '商城';
-- Add comments to the columns 
comment on column data_homework.MALL.mall_id
  is '商城id';
comment on column data_homework.MALL.user_table
  is '用户表注册id';
comment on column data_homework.MALL.merchant_table
  is '商家表注册id';
 
 
 
 
 -- 创建用户注册表
drop table if exists data_homework.USER_REGISTER;
create table data_homework.USER_REGISTER
(
  register_id INT PRIMARY KEY,
  user_id INT,
  register_time  timestamp
);

-- Add comments to the table 
comment on table data_homework.USER_REGISTER
  is '用户注册表';
-- Add comments to the columns 
comment on column data_homework.USER_REGISTER.register_id
  is '用户注册表id';
comment on column data_homework.USER_REGISTER.user_id
  is '用户id';
comment on column data_homework.USER_REGISTER.register_time
  is '注册时间';
 
 
 
 
 -- 创建商家入驻表
drop table if exists data_homework.MERCHANT_REGISTER;
create table data_homework.MERCHANT_REGISTER
(
  register_id INT PRIMARY KEY,
  merchant_id INT,
  register_time  timestamp
);

-- Add comments to the table 
comment on table data_homework.MERCHANT_REGISTER
  is '商家入驻表';
-- Add comments to the columns 
comment on column data_homework.MERCHANT_REGISTER.register_id
  is '商家入驻表id';
comment on column data_homework.MERCHANT_REGISTER.merchant_id
  is '商家id';
comment on column data_homework.MERCHANT_REGISTER.register_time
  is '入驻时间';
 
 
 
 
  -- 创建用户表
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
  is '用户表';
-- Add comments to the columns 
comment on column data_homework.USERS.user_id
  is '用户id';
comment on column data_homework.USERS.gender
  is '性别';
comment on column data_homework.USERS.age
  is '年龄';
 comment on column data_homework.USERS.phone_number
  is '手机号';
  comment on column data_homework.USERS.order_id
  is '订单号';
 
 
 
 
 
   -- 创建商家表
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
  is '商家表';
-- Add comments to the columns 
comment on column data_homework.MERCHANT.merchant_id
  is '商家id';
comment on column data_homework.MERCHANT.gender
  is '性别';
comment on column data_homework.MERCHANT.age
  is '年龄';
 comment on column data_homework.MERCHANT.phone_number
  is '手机号';
  comment on column data_homework.MERCHANT.goods_id
  is '货物号';

 
 
 
 
 
    -- 创建订单表
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
  is '订单表';
-- Add comments to the columns 
comment on column data_homework.ORDERS.order_id
  is '订单号';
comment on column data_homework.ORDERS.place
  is '收货地点';
comment on column data_homework.ORDERS.city
  is '收货城市';
 comment on column data_homework.ORDERS.time
  is '下单时间';
  
 
 
 
     -- 创建上架表
drop table if exists data_homework.SHOP;
create table data_homework.SHOP
(
  goods_id INT PRIMARY KEY,
  time  timestamp
);

-- Add comments to the table 
comment on table data_homework.SHOP
  is '上架表';
-- Add comments to the columns 
comment on column data_homework.SHOP.goods_id
  is '商品id';
 comment on column data_homework.SHOP.time
  is '上架时间';
  
 
 
 
      -- 创建商品表
drop table if exists data_homework.GOODS;
create table data_homework.GOODS
(
  goods_id INT PRIMARY KEY,
  good_type VARCHAR(30)
);

-- Add comments to the table 
comment on table data_homework.GOODS
  is '商品表';
-- Add comments to the columns 
comment on column data_homework.GOODS.goods_id
  is '商品id';
 comment on column data_homework.GOODS.good_type
  is '商品类型';