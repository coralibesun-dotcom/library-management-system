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

## 3.LoginInterceptor,UserContext,JwUtil
#### (1)
用户请求接口
↓
前端把 token 放入请求头(Header)
↓
LoginInterceptor 拦截请求
↓
JwtUtil 解析并验证 token
↓
得到 userId、role 等信息
↓
UserContext 使用 ThreadLocal 保存当前请求用户信息
↓
放行进入 Controller
↓
Service 可以通过 UserContext 获取当前用户
↓
请求结束
↓
UserContext.clear() 清除当前线程数据
#### (2)
完整职责区分：
组件	                职责
JwtUtil	            处理 JWT 格式：生成 token、解析 token
LoginInterceptor	处理 HTTP 请求流程：拿 token、调用 JwtUtil、初始化用户身份
UserContext	        保存当前请求用户信息
权限控制	            判断这个用户能不能执行某操作




## 4.传值
### (1)url路径传值
GET /borrow/user/5

这个 5 来源：

浏览器/前端
↓
HTTP请求URL
↓
Spring解析@PathVariable
↓
userId=5
↓
Service

### (2)UserContext传值
UserContext 也不是凭空产生的。

它的来源链：

用户登录
↓
JWT生成
↓
token保存userId
↓
请求携带token
↓
Interceptor解析
↓
UserContext保存


所以核心区别：

### (3)路径传id	UserContext
来源	                      用户请求参数  	登录身份
是否用户可修改	          可以	        不能直接改
可信程度	                  低	         高
用途	查询别人资料（管理员）	  查询自己的资料


## 5.LoginInterceptor和WebConfig的关系
#### (1)
小区：
物业规定：
1号门需要保安检查
2号门不用检查
这是 WebConfig。

然后：
保安：
请出示门禁卡
验证身份
这是 LoginInterceptor。
