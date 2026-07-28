## 1.分页

用户选择：
pageNum = 第几页
pageSize = 每页显示多少条

        ↓

Controller 接收两个参数

        ↓

Service 根据 pageNum 和 pageSize 计算 offset

        ↓

Mapper 接收 offset 和 pageSize

        ↓

SQL 使用 limit offset,pageSize 查询

        ↓

返回这一页的数据 + 总数量total

## 2.关键区别
空 List
[]

表示：

查询成功，只是没有数据。

比如：

分类表存在，但是目前没有分类
null
null

表示：

没有返回对象。

一般用于单个查询
