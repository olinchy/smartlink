-   问题描述
    在上传完镜像后部署蓝图失败，包括蓝图本身错误或者蓝图不可用，或者点击部署后发现其无法部署成为应用
-   解决方案
    1.  首先，如果在创建蓝图，点击新建后就出现了“被移除项目”的问题的话，没有进入绘图界面，尝试使用Chrome浏览器并清空缓存。
    2.  如果蓝图创建完成，但是无法部署的情况，可能由于需要选择蓝图版本时出现错误。  
        注意镜像的版本号可以自定义，但蓝图的版本号需要由v开头，**最多只有一个“.”** 否则在部署的时候会出现非法的版本号的问题。  
        而且如果出现这种问题只能重新构建蓝图，没有办法修改已存在蓝图的版本号。
    3.  在部署新的应用或者在升级应用的时候，需要注意不仅仅要选择新的版本，出现下述问题
        ```
        get status of common resource,
        response: {
          'data': 'Wed, 31 Oct 2018 07:09:28 GMT',
          'status': '404',
          'content-length': '233',
          'content-type': 'text/html',
          'connection': 'keep-alive'
        },
        content:
        <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2Final//EN">
        <title>404 Not Found </title>
        <h1>Not Found</h1>
        <p>The requested URL was not found on the server. If you entered the URL manually please check your speeling and try again.</p>
        ```
       那么需要注意，如果蓝图中存在公共服务，在部署/升级页面中逻辑公共服务名称中后面的绑定公共服务名称里应该选择绑定相应的公共服务。            