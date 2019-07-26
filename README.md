# RuoYi-plus
### 数据库脚本请加QQ群687672649<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5eBNzMW"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="ruoyi-plus" title="ruoyi-plus"></a>在群文件下载

#### 前言
我们的终极目标是打造出一款由java开发的、快速搭建、高代码质量、简洁美观的开源商城系统！！！！！！

#### 介绍
RuoYi-plus开源后台管理系统由java开发基于SpringBoot2.x， springcloud G的全新版本，架构更清晰、代码更整洁、页面更美观， 可用于OA系统、 CRM系统、 PDM系统等二次开发，非常适用于公司管理平台旧版本升级、新平台搭建快速整合、同时也是接私活利器。
**特别注意： 本项目是在开源项目RuoYi（若依） 的基础上进行升级调整，感谢诺依大神。** 

#### 软件架构
- zebra-yritsz-smp-parent模块：所有项目父类，负责jar的依赖和版本管理。
- zebra-yritsz-config-servser：cloud配置中心服务端。
- zebra-yritsz-smp-smp模块：管理平台，主要为controller层和视图文件。
- zebra-yritsz-smp-generator模块：代码生成器。
- zebra-yritsz-smp-quartz模块：定时任务。
- zebra-yritsz-smp-core模块：核心模块，包过权限处理、持久化操作、工具类、配置中心客户端、数据源等。
- zebra-yritsz-commons-dbean模块：通用db和bean。
- zebra-yritsz-commons-redis模块：通用redis。

1. 核心框架：Spring Boot2.x，springcloud G版本。
2. 安全框架：Apache Shiro。
3. 模板引擎：Thymeleaf。
4. 持久层框架：MyBatis。
5. 定时任务:Quartz。
6. 数据库连接池：Druid。
7. 代码生成器：Velocity。
8. 项目管理：maven。
9. 前端框架：Bootstrap，Laiui。
10. 缓存技术：Redis，Shiro自带缓存。
11. 其他插件：tk.mybatis，lombok等。

#### 原版RuoYi升级调整介绍
- 调整前提环境：中小型项目，并且可以快速搭建新的平台。
- maven管理：取消项目聚合，本人多接触快速搭建管理平台的需求，评估后感觉聚合项目不太适合，所以改使用普通父类子类集成，有新项目时直接继承统一父类，保障快速开发，版本统一。
- 项目管理：增加redis通用模块，整合system持久化模块，common通用模块，framework核心模块为zebra-yritsz-commons-dbean通用db-bean模块和zebra-yritsz-smp-core核心模块，对于中小项目来说，原项目模块太繁琐，而且各个模块分工不明确，整合为一个模块当有新项目需求事可以快速搭建。
- 框架管理：增加tk.mybatis插件，原项目虽然可以用代码生成器直接生成增删改查语句，但是繁琐业务下，需要在xml写sql等，使用tk.mybatis插件可以直接使用封装快速方法ql，极高的保障了开发的效率。
- 配置管理：增加cloud config配置中心，当项目生态系统不仅限于管理平台，繁琐的配置成为增加工作量和出现问题的关键，所以增加配置中心，统一管理配置文件。
- 其他修改：增加Redis存储系统、java代码神器lombok、消息转换器HttpMessageConverter...
- 有待升级：正在努力中...
![输入图片说明](https://images.gitee.com/uploads/images/2019/0714/135757_cbf2decc_2038874.jpeg "1562921075(1).jpg")

#### 项目部署要求
- JDK-v1.8
- MySql-v5.7.x（建议）
- Maven-v3.3.x（建议）
- SVN服务器（可选）如果使用配置中心则需要此项，反之 **需要调整代码不通过springCloud config获取配置信息** ，或者使用git。
- GIT服务器（可选）如果使用配置中心则需要此项，反之 **需要调整代码不通过springCloud config获取配置信息** ，或者使用svn。

#### 项目运行须知
1. 项目运行有两种模式，详细见项目运行步骤。
2. redis如果不需要，可以保留（不用搭建redis服务器也可以启动成功）。 

#### 项目运行步骤一： **使用配置中心** 
1. 创建数据库，执行数据库脚本;导入项目，导入完成后需要确保是maven项目，如果不是需要转换成maven项目。
2. 发布项目到maven仓库，依次为zebra-yritsz-smp-parent->zebra-yritsz-commons-dbean->zebra-yritsz-smp-core/generator/quartz 。
3. 启动zebra-yritsz-smp-config模块，该模块为springCloud config-server端 ，需要svn服务器或git服务器把配置中心文件放到对应svn/git上，并且需要配置bootstrap.properties文件。![输入图片说明](https://images.gitee.com/uploads/images/2019/0715/134215_78272869_2038874.jpeg "1563169313(1).jpg")
4. 启动zebra-yritsz-smp-smp，如果使用springCloud config需要配置bootstrap.properties文件。![输入图片说明](https://images.gitee.com/uploads/images/2019/0715/135500_380c9fc3_2038874.jpeg "1563170081(1).jpg")

#### 项目运行步骤二： **不使用配置中心** 
1. 创建数据库，执行数据库脚本;导入项目，导入完成后需要确保是maven项目，如果不是需要转换成maven项目。
2. 发布项目到maven仓库，依次为zebra-yritsz-smp-parent->zebra-yritsz-commons-dbean->zebra-yritsz-smp-core/generator/quartz 。
4. 启动zebra-yritsz-smp-smp。
![输入图片说明](https://images.gitee.com/uploads/images/2019/0716/092738_c32e9535_2038874.jpeg "1563239374(1).jpg")

#### 技术交流
- 官方技术QQ交流号：1579927646
- 官方QQ技术交流群：687672649<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5eBNzMW"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="ruoyi-plus" title="ruoyi-plus"></a> **免费入群**，数据库脚本和配置中心文件在群文件 
- 官方QQ技术支持群：751872263<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5wYOaQe
"><img border="0" src="//pub.idqqimg.com/wpa/images/group.png" alt="ruoyi-plus" title="ruoyi-plus"></a> **入群费一百元**，扫码下方支付宝/微信收款码，支付后入群（说明：支付昵称）。
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![输入图片说明](https://images.gitee.com/uploads/images/2019/0716/152104_06096d89_2038874.jpeg "微信图片_20190716151916.jpg")&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
![输入图片说明](https://images.gitee.com/uploads/images/2019/0716/152628_a5952a05_2038874.png "微信图片_20190716151912.png")

#### 演示地址
- 地址：http://www.yritsz.com/ruoyi-plus
- 账号：tecom 
- 密码：123456
