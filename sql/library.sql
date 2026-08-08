create database library_management
    default character set utf8mb4
    default collate utf8mb4_unicode_ci;
use library_management;
CREATE TABLE user (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',

                      username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',

                      password VARCHAR(100) NOT NULL COMMENT '密码',

                      role VARCHAR(20) NOT NULL COMMENT '角色 ADMIN/USER',

                      status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0禁用 1正常',

                      create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

                      update_time DATETIME DEFAULT CURRENT_TIMESTAMP
                          ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);
CREATE TABLE category (

                          id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',

                          name VARCHAR(50) NOT NULL UNIQUE COMMENT '分类名称',

                          create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

                          update_time DATETIME DEFAULT CURRENT_TIMESTAMP
                              ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
);CREATE TABLE book (

                        id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '图书ID',

                        name VARCHAR(100) NOT NULL COMMENT '书名',

                        author VARCHAR(50) COMMENT '作者',

                        isbn VARCHAR(30) UNIQUE COMMENT 'ISBN编号',

                        category_id BIGINT NOT NULL COMMENT '分类ID',

                        publisher VARCHAR(100) COMMENT '出版社',

                        stock INT NOT NULL DEFAULT 0 COMMENT '库存',

                        description TEXT COMMENT '图书简介',

                        status TINYINT NOT NULL DEFAULT 1 COMMENT '状态 0下架 1上架',

                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP,

                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP
                            ON UPDATE CURRENT_TIMESTAMP,

                        CONSTRAINT fk_book_category
                            FOREIGN KEY(category_id)
                                REFERENCES category(id)

  );
CREATE TABLE borrow_record (

                               id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '借阅记录ID',

                               user_id BIGINT NOT NULL COMMENT '用户ID',

                               book_id BIGINT NOT NULL COMMENT '图书ID',

                               borrow_time DATETIME NOT NULL COMMENT '借阅时间',

                               return_time DATETIME COMMENT '归还时间',

                               status TINYINT NOT NULL DEFAULT 0
                                   COMMENT '状态 0借阅中 1已归还 2逾期',

                               create_time DATETIME DEFAULT CURRENT_TIMESTAMP,

                               update_time DATETIME DEFAULT CURRENT_TIMESTAMP
                                   ON UPDATE CURRENT_TIMESTAMP,


                               CONSTRAINT fk_record_user
                                   FOREIGN KEY(user_id)
                                       REFERENCES user(id),


                               CONSTRAINT fk_record_book
                                   FOREIGN KEY(book_id)
                                       REFERENCES book(id)

);
CREATE TABLE operation_log (

                               id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',

                               user_id BIGINT NOT NULL COMMENT '操作用户',

                               operation VARCHAR(50) NOT NULL COMMENT '操作类型',

                               target VARCHAR(200) COMMENT '操作对象',

                               create_time DATETIME DEFAULT CURRENT_TIMESTAMP,


                               CONSTRAINT fk_log_user
                                   FOREIGN KEY(user_id)
                                       REFERENCES user(id)

);

INSERT INTO user
(
    username,
    password,
    role,
    status,
    create_time,
    update_time
)
VALUES
    (
        'admin',
        '$2a$10$JijHNy6vf1Q5xPT2uYXg.O3zMtEmdk9ko3jyK17FwR.duXUo68O.W',
        'ADMIN',
        1,
        NOW(),
        NOW()
    );

INSERT INTO user
(
    username,
    password,
    role,
    status,
    create_time,
    update_time
)
VALUES
    (
        'cora',
        '$2a$10$JijHNy6vf1Q5xPT2uYXg.O3zMtEmdk9ko3jyK17FwR.duXUo68O.W',
        'USER',
        1,
        NOW(),
        NOW()
    );
