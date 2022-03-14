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

Spring Security 所解决的问题就是安全访问控制. 而安全访问控制功能其实就是对所有进入系统的请求进行拦截, 校验每个请求是否能访问它所期望的资源. 可以通过Filter或者AOP来实现.

Spring Security对Web资源的保护是靠Filter来实现的. Authentication Manager 校验身份 Access Decision 校验权限

Spring Security 认证流程

![image](https://github.com/ZehuaWang/Oauth2-Demo/blob/main/IMG/Spring-Security-Auth-Flow.png)

采用BCrypt加密算法 encoder 每次加密后的字符串不同 增加安全性

授权流程

SpringSecurity 通过http.authorizeRequest()对web请求进行授权保护. Spring Security 使用标准的Filter对Web请求进行拦截 最终实现对资源的授权访问

![image](https://github.com/ZehuaWang/Oauth2-Demo/blob/main/IMG/Spring-Security-Authorization.png)

授权的方式包括 web授权 方法授权 web授权是通过url拦截进行授权 方法授权是通过方法拦截进行授权 都会调用accessDecisionManager进行决策 

基于web的授权 -> 给 http.authorizeRequests()添加多个子节点来定制需求到url. 

http.authorizeRequests().anyMatchers("/r/r1").hasAuthority("p1")

方法授权 -> 在controller加注释 @PreAuthorize @PostAuthorize @Secured 三类注解

在方法上添加注解就会限制对该方法的访问. Spring Security 的原生注释支持为该方法定义了一组属性. 这些将被传递给AccessDecisionManager做决定

## 分布式系统认证方案

具有分布式架构的系统叫做分布式系统 分布式系统的运行通常依赖于网络 将单体的系统分为若干的服务 服务之间通过网络的交互来完成用户的业务的处理

分布式系统的特点:

1. 分布性 每个部分都可以独立部署 服务之间通过网络进行通信 比如 : 订单服务 商品服务
2. 伸缩性 每个部分都可以集群方式部署 并可以针对部分节点进行软件与硬件扩容 具有伸缩能力
3. 共享性 每个部分都可以作为共享资源对外提供服务 多个部分可能有操作共享资源的情况
4. 开放性 每个部分根据需求都可以对外发布共享资源的访问接口 并可以允许第三方系统访问

分布式认证需求

分布式系统的每个服务都会有认证 授权的需求 如果每个服务都实现一套认证授权的逻辑 会非常冗余 需要独立的认证服务处理系统认证授权的需求

考虑到分布式系统的开放性的特点 不仅对系统内部服务提供认证 对第三方系统也要提供认证, 分布式认证的需求如下

1. 统一认证授权

提供独立的认证服务 统一处理认证授权. 无论是不同类型的客户 还是不同类型的客户端(web H5 APP) 均采用一致的认证, 权限, 会话机制 实现统一的认证授权

要实现统一则认证方式必须是可扩展的 支持各种认证需求 比如: 用户名密码认证 短信认证 二维码

2. 应用接入认证

应提供扩展和开放能力 提供安全的系统对接机制 并可以开放部分API给第三方使用 一方应用(内部 系统服务) 和 三方应用 均采用统一机制接入.

分布式认证方案:

1. 基于session的认证方式

在分布式的环境下 基于session的认证会出现一个问题 每个应用都需要在session中存储用户的身份信息 通过负载均衡将本地的请求分配到另一个应用服务器需要将
session信息带过去 否则需要重新认证

![session auth](https://github.com/ZehuaWang/Oauth2-Demo/blob/main/IMG/session-based-auth.png)

Session 复制: 各台应用服务器之间同步session 使session保持一致 对外透明

Session 黏贴: 当用户访问集群中某台服务器后 强制指定后续的所有请求落到此台机器

Session 集中存储: 将session存入分布式的缓存 所有的服务器实例将统一从分布式缓存中提取session

总体来讲 基于session的认证方式 可以更好的在服务端对会话进行控制 且安全性高. 但是 session机制方式给予cookie 在移动端不能有效使用 且无法跨域.

2. 基于token的方式

基于token的认证方式 服务端不用存储认证数据 易维护 扩展性强 客户可以把token存储在任何地方 并且可以实现web与app统一认证. 缺点明显: token由于自包含信息

因此一般数据量较大 而且每次请求都需要传递 因此比较占带宽. 此外 token的签名验签操作也会给cpu带来额外的处理负担

token令牌可以存储客户的姓名 权限等信息

![session auth](https://github.com/ZehuaWang/Oauth2-Demo/blob/main/IMG/token-based-auth.png)

分布式系统认证技术方案:

![session auth](https://github.com/ZehuaWang/Oauth2-Demo/blob/main/IMG/auth-structure.png)

根据选型的分析 决定采用基于token的认证方式 优点是:

1. 适合统一的认证机制 客户端 一方应用 三方应用都遵循一直的认证机制
2. token认证方式对第三方应用接入更适合 因为它更开放 可以使用当前流行的Oauth2.0 JWT等
3. 一般情况下 客户端无需存储会话信息 减轻了服务端的压力

Oauth2.0 介绍

OAuth 是一个开放标准 允许用户授权第三方应用访问他们存储在另外的服务提供者上的信息 而不需要将用户名和密码提供给第三方应用或分享他们数据的所有内容

OAuth2.0 是OAuth协议的延续版本 但是不会向后兼容OAuth1.0 

例子:

用户借助微信认证登陆黑马 用户不需要在黑马注册 怎么算认证成功呢 ? 黑马需要成功从微信获取用户的身份信息则认为用户认证成功 那如何从微信获取用户的身份信息

用户信息的拥有者是用户本人 微信需要经过用户的同意方可为黑马程序员生成令牌 黑马程序员网站拿此令牌方可从微信获取用户的信息

![oauth-flow](https://github.com/ZehuaWang/Oauth2-Demo/blob/main/IMG/oauth-flow.png)

官方协议流程:

![oauth-flow](https://github.com/ZehuaWang/Oauth2-Demo/blob/main/IMG/official-flow.png)

OAauth2.0包括以下角色:

1. 客户端 本身不存储资源 需要资源的拥有者的授权去请求资源服务器的资源 例如: web客户端 微信客户端等
2. 资源拥有者: 通常为用户 也可以是应用程序 即该资源的拥有者
3. 授权服务器 也叫 认证服务器 用于服务提供商对资源拥有的身份进行认证, 对访问资源进行授权, 认证成功后会给客户端发放令牌(access_token) 作为客户端访问资源服务器的凭据 本例子为微信的认证服务器
4. 资源服务器 存储资源的服务器 本例子为微信存储的用户信息 

服务商不能随便允许一个客户端接入它的授权服务器 服务提供商会给准入的接入方一个身份 用于接入时的凭据

client_id 客户端标识

client_secret: 客户端密钥

因此准确来说 授权服务器对两种OAuth2.0中的两个角色进行认证授权 分别是资源拥有者 客户端

实战:

采用 Spring Cloud Security OAuth2框架

OAuth2.0的服务提供方涵盖两个服务 即授权服务和资源服务 使用Spring Security OAuth2的时候可以选择把他们在同一个应用程序中实现 也可以选择建立使用同一个授权服务的多个资源服务

授权服务: 应包含对接入端以及登入用户的合法性进行验证并颁发token等功能

AuthorizationEndpoint: 服务于认证请求 默认URL: /oauth/authorize

TokenEndpoint: 服务于访问令牌的请求 默认URL: /oauth/token

资源服务: 应包含对资源的保护功能 对非法请求进行拦截 对请求中的token进行解析鉴权等.

分别创建uaa授权服务和order订单资源服务

认证流程如下:

1. 客户端请求UAA授权服务进行认证
2. 认证通过后由UAA颁发令牌
3. 客户端携带令牌Token请求资源服务
4. 资源服务校验令牌的合法性 合法即返回资源信息

授权服务器的配置

令牌管理: AuthorizationServerTokenServices 接口定义了一些操作 可以对令牌进行必要的管理. 令牌可以被用来加载身份信息 里面包含了令牌的相关权限

Oauth2的四种模式

1. 授权码模式

![oauth-code](https://github.com/ZehuaWang/Oauth2-Demo/blob/main/IMG/auth-code.png)

资源拥有者打开客户端 客户端要求资源拥有者给予授权 它将浏览器重新定向到授权服务器, 重定向时会附加客户端的身份信息.

/uaa/oauth/authorize?client_id=c1&response_type=code&scopre=all&redirect_uri=http://www.baidu.com

client_id: 客户端准入标识

response_type： 授权码模式固定code

scope: 客户端权限

redirect_url: 跳转uri 当授权码申请成功后会跳转到此地址 并在后边带上code参数

浏览器出现向授权服务器授权页面 之后将用户同意授权

授权服务器将授权码转经过浏览器发送给client
