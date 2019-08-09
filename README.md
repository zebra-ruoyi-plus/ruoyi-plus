# RuoYi-plus
### 数据库脚本请加QQ群，在群文件下载。
【技术支持群】687672649<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5eBNzMW"><img border="0" src="https://images.gitee.com/uploads/images/2019/0808/111020_23a5e7c3_2038874.png" alt="ruoyi-plus" title="ruoyi-plus"></a>【技术交流群】751872263<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5wYOaQe
"><img border="0" src="https://images.gitee.com/uploads/images/2019/0808/111020_23a5e7c3_2038874.png" alt="ruoyi-plus" title="ruoyi-plus"></a>


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

 **后台框架介绍：** 
|  序号  |  核心技术  |  框架   | 阐述 |
| --- | --- | --- | --- |
|1|核心框架|Spring Boot2.x，springcloud G版本|springBoot 为核心框架，springcloud 为保留核心框架，目前仅使用cloudConfig(可选)。|
|2|安全框架|Apache Shiro|core模块|
|3|模板引擎|Thymeleaf|smp模块|
|4|持久层框架|MyBatis|实现：dbean模块 配置：core模块|
|5|定时任务|Quartz|quartz模块|
|6|数据库连接池|Druid|实现：dbean模块 配置：core模块|
|7|代码生成|Velocity|generator模块    |
|8|项目管理|Maven|缺省  |
|9|缓存技术|Redis，Shiro自带缓存| （实现：redis模块， 配置：core模块），core模块   |
|10|其他插件|tk.mybatis，lombok等|（dben模块，core模块），全局  |

 **前端框架：**
|  序号  |  核心技术  |  框架   | 阐述 |
| --- | --- | --- | --- |
|1|核心框架|JQuery、Bootstrap|缺省|
|2|table表格|bootstrap-table|缺省|
|3|表格树插件|bootstrap-treetable|缺省|
|4|表单导出|bootstrap-table-export、tableExport|缺省|
|5|遮罩层弹出框|layui、layer|缺省|
|6|ztree树插件|jquery.ztree|缺省|
|7|下拉框插件|select2.min、bootstrap-select|缺省|
|8|时间插件|bootstrap-datetimepicker|缺省|
|9|富文本编辑|summernote|缺省|
|10|文件上传|bootstrap-fileinput|缺省|
|11|统计报表|echarts-all、jquery.peity、jquery.sparkline|缺省|
|12|表单验证|jquery.validate|缺省|

#### 原版RuoYi升级调整介绍

1.  **取消项目聚合：** 本人多接触快速搭建管理平台的需求，评估后感觉聚合项目不太适合，所以改使用普通父类子类集成，有新项目时直接继承统一父类，保障快速开发，版本统一。 
2.  **模块调整：** 整合system持久化模块，为通用的zebra-yritsz-commons-dbean可以供其他服务（如：微端服务、APP接口服务）使用。整合common通用模块，framework核心模块为zebra-yritsz-smp-core核心模块，对于中小项目来说，原项目模块太繁琐，各个模块分工不明确，整合为一个模块当有新项目需求事可以快速搭建。
3.  **增加模块：** 增加redis通用模块（可选），config-servser配置中心模块（可选）。
4.  **框架管理：** 增加tk.mybatis插件，原项目虽然可以用代码生成器直接生成增删改查语句，但是繁琐业务下，需要在xml写sql映射过于繁琐，使用tk.mybatis插件可以直接使用封装快速方法，极高的保障了开发的效率。
5.  **配置管理：** 增加cloud config配置中心，当项目生态系统不仅限于管理平台，繁琐的配置成为增加工作量和出现问题的关键，所以增加配置中心，统一管理配置文件。
6.  **其他修改：** 增加Redis存储系统、java代码神器lombok、消息转换器HttpMessageConverter...
7.  **有待升级：** sso单点登录下个版本更新，该版本会增加spring cloud生态中注册中心Eureka、熔断机制等。
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
2. 发布项目到maven仓库，依次为zebra-yritsz-smp-parent->zebra-yritsz-commons-dbean->zebra-yritsz-smp-core/generator/quartz。
4. 把配置中心文件放入项目resources目录下，启动zebra-yritsz-smp-smp。
![输入图片说明](https://images.gitee.com/uploads/images/2019/0809/095055_bf4c3fd1_2038874.jpeg "1565315429(1).jpg")

#### 技术交流
- 官方技术QQ交流号：1579927646
- 官方QQ技术交流群：751872263<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5wYOaQe
"><img border="0" src="https://images.gitee.com/uploads/images/2019/0808/111020_23a5e7c3_2038874.png" alt="ruoyi-plus" title="ruoyi-plus"></a> **免费入群**，数据库脚本和配置中心文件在群文件 
- 官方QQ技术支持群：687672649<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5eBNzMW"><img border="0" src="https://images.gitee.com/uploads/images/2019/0808/111020_23a5e7c3_2038874.png" alt="ruoyi-plus" title="ruoyi-plus"></a> **免费入群**

#### 演示地址
- 地址：http://www.yritsz.com/ruoyi-plus
- 账号：tecom 
- 密码：123456

#### 源码地址
- Gitee(主)：https://gitee.com/aimeng2017/RuoYi-plus
- Github(辅)：https://github.com/zebra-ruoyi-plus/ruoyi-plus