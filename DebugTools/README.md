#### 代码获取
可以多种方式，一个是找承载相关人员要某个功能的代码库，然后申请权限，但是一般承载的一个ie功能涉及多个库，很分散，开发人员可能给不全
所以可以从安装好的网管里反编译

0. 登录 https://10.86.110.251/portal/  umebn 111111
0. Software Repository ->Image，搜索涉及的镜像，点进去后Export出镜像到本地
0. 本地导入镜像
` docker load --input imagexxx.tar `
0. 找到镜像id
` docker images `
0. 启动容器
` docker run imageid `
0. 找到容器的id
` docker ps -a `
0. 进入容器的sh
` docker exex -it containerid /bin/sh `
0. 找出需要拷贝出的文件的路径
0. 把文件拷贝到本地 ` docker cp containerid：filepath  localpath `

#### 代码工程导入
导入代码时，maven的settings文件，用这里附带的

#### 如何调试容器内的代码
[how to debug docker](HowToDebugDocker.md)

#### 微服务调试
启动时间要求：https://wiki.zte.com.cn/pages/viewpage.action?pageId=191660057
抓包：https://wiki.zte.com.cn/pages/viewpage.action?pageId=386302068
公共服务大网使用：https://wiki.zte.com.cn/pages/viewpage.action?pageId=676005015
Log位置：https://wiki.zte.com.cn/pages/viewpage.action?pageId=649495603
执行一次就退出的微服务处理：https://wiki.zte.com.cn/pages/viewpage.action?pageId=686364251
查看数据库：https://wiki.zte.com.cn/pages/viewpage.action?pageId=526026045

微服务调试去鉴权
使用Postman工具调用部署在paas平台的后端微服务接口时，会出现鉴权问题，而部署在paas上的前端微服务去访问后端微服务就不会有该问题。其原因是因为平台进行了鉴权操作，即每个发向部署在paas平台微服务的请求都要带有用户名密码信息，没有该信息平台就会返回401错误。

现提供如下2种方法来绕开鉴权：

一、对paas平台上后端微服务进行配置
     把需要去掉鉴权的微服务的conf目录下加上auth-config.prop文件，重新部署该微服务。原理就是屏蔽该类消息的鉴权，允许调用该微服务的接口。
     auth-config.properties

二、使用浏览器插件调试
    此种方法不需要添加去鉴权的配置文件，需要在浏览器上装上测试rest接口的插件。
    先用账号密码登陆ume（鉴权），然后使用插件调试rest接口。（此时该浏览的会话里已保存鉴权信息）
Restlet-Client-chrome.crx

注意：以上方法均需要  请求rest接口时添加csrftoken的请求参数。