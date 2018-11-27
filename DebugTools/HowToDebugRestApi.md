####微服务调试去鉴权

使用Postman工具调用部署在paas平台的后端微服务接口时，会出现鉴权问题，而部署在paas上的前端微服务去访问后端微服务就不会有该问题。其原因是因为平台进行了鉴权操作，即每个发向部署在paas平台微服务的请求都要带有用户名密码信息，没有该信息平台就会返回401错误。

现提供如下2种方法来绕开鉴权：

一、对paas平台上后端微服务进行配置
     把需要去掉鉴权的微服务的conf目录下加上auth-config.prop文件，重新部署该微服务。原理就是屏蔽该类消息的鉴权，允许调用该微服务的接口。
     [auth-config.properties](auth-config.properties)

二、使用浏览器插件调试
    此种方法不需要添加去鉴权的配置文件，需要在浏览器上装上测试rest接口的插件。
    先用账号密码登陆ume（鉴权），然后使用插件调试rest接口。（此时该浏览的会话里已保存鉴权信息）
Restlet-Client-chrome.crx

注意：以上方法均需要  请求rest接口时添加csrftoken的请求参数。

实际操作时，只需要安步骤一操作即可在msb上使用rest接口，例如访问网址https://10.86.110.251/iui/microservices/iui-route/routeDetail.html?publishport=28002&routetype=api&routename=umebn-inv-basic&routeversion=v1
postman 可以使用非安全通道http://10.86.110.251：28003