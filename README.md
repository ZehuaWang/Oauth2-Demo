# Oauth2-Demo
A demo project for OAuth2.0

## Session Based Authentication and Authorization

什么是认证: 输入账号密码 登陆应用的过程就是认证

为什么要认证: 保护系统的隐私数据与资源 用户的身份合法才可以访问系统的资源

常见的用户身份认证方式: 用户名密码登陆 二维码登陆 手机短信登录 指纹认证

OAtuth2.0 用于分布式系统认证授权

认证 系统判断身份是否合法的过程

什么是会话

用户认证通过后 为了避免用户的每次操作都要进行认证 将用户的信息保存在回话中 会话就是系统为了保持当前用户的登录状态所提供的机制 常见的有基于Session, token的方式

基于session的认证方式

用户认证成功后 在服务端生成用户相关的数据保存在session中 发送给客户端的session_id保存在cookie中 这样客户端请求时带上session_id就可以验证服务器端是否存在session的数据

以此完成用户的合法校验 当用户退出系统或者session过期销毁时 客户端的session_id无效

基于token的存储方式

交互流程 -> 用户认证成功后 服务端生成一个token发送给客户端 客户端可以放到cookie或者localStorage等存储中 每次请求时带上token 客户端收到token后即可以确认用户身份

区别:

基于session的认证方式由Servlet规范制定 服务端需要存储session信息 要占用内存的资源 客户端需要支持cookie 基于token的方式则一般不需要服务端存储token, 并且不限制客户端的存储方式 

移动互联网的客户端需要接入系统 采用前后端分离的架构进行实现 所以机遇token的方式更适合

什么是授权:

根据用户的权限来控制用户使用资源的过程就是授权

为什么要授权:

认证是为了保证用户身份的合法性 授权是为了更细粒度的对隐私数据进行划分 授权是在认证通过之后发生的 控制不同的用户能够访问不同的资源

授权 -> 用户认证通过根据用户的权限来控制用户访问资源的过程 拥有资源的访问权限则正常访问 没有权限则拒绝访问

授权的数据模型

授权可以理解为Who对What(Which)进行How操作

角色是对权限的打包

通常企业开发将资源表和权限表合并为一张权限表 (权限id 权限标识 权限名称 资源名称 资源访问地址 ...)

修改后的数据模型表 用户表 用户角色关系表 角色表 角色权限关系表 权限表

RBAC -> Role Based Access Control 基于角色进行授权

if(主体.hasRole("manager")) {
  findSalary();
}

如果需要查询工资的角色变化为经理和HR 此时就需要修改判断逻辑 -> 可扩展性差

基于资源的访问控制 按照资源或者权限进行授权 -> 用户必须具有查询工资的权限才可以查询信息

if(user.hasPermision("pay")) {
  findSalray();
}

优点: 系统的可扩展性强

基于Session的认证方式

用户认证成功以后 在服务端生成用户相关的数据保存在session 发给客户端的session_id保存在cookie中, 这样客户端请求时带上session_id就可以验证服务端是否存在
session数据 以此完成用户的合法校验. 当用户退出系统或者session过期销毁时 客户端的session_id也就无效了.

基于Session的认证机制由Servlet的规范定制 Servlet容器已经实现 用户通过HttpSession的操作方法即可实现

实现会话功能: 会话是指用户登入系统后, 系统会记住用户的登录状态 可以在系统连续操作直到退出系统的过程

认证的目的是对系统资源的保护, 每次对资源访问, 系统必须知道是谁在访问资源, 才能对该请求进行合法性拦截. 因此在认证成功后, 一般会把认证成功的用户信息放入Session中
在后续的请求中 系统能够从Session中获取到当前的用户 用这样的方式来实现会话机制

增加权限数据 需要在UserDto里增加权限属性 用于表示该登录用户所拥有的权限

## Spring Security

Spring Security 提供认证授权拦截的功能 不需要独立定义拦截器

Spring Security 提供了用户密码登陆 退出 会话管理等认证功能 只需要配置即可使用

在config包下定义WebSecurityConfig 安全配置的内容包括 用户信息 密码编码器 安全拦截机制

实现授权需要对用户的访问进行拦截校验 校验用户的权限是否可以操作指定的资源 Spring Security默认提供授权实现方法

## SpringBoot 集成

使用spring-boot-starter-security用于开发Spring Security 应用
