### 记录部署过程中出现的问题
1.  部署一个新的镜像时，注意是将镜像导出，而非容器，使用命令  
    ```docker save [OPTIONS] IMAGE```  
    导出.tar的docker镜像，如果使用  
    ```docker export [OPTIONS] CONTAINER```  
    会导致在创建微服务的时候无法上传镜像
2.  成功上传镜像，正常的状态为“远端部署”或者“可用”，当出现不可用的时候，点击不可用如果错误信息为toolbox.swr.bin.failReason,具体信息显示"MANIFEST_UNKNOWN"。考虑是打包的方式不对
    > 此处没有理解具体原理，但经过实践发现确实如此。
      
    在镜像导出的时候，无论使用  
    ```docker export -o IMAGE_ID```  
    还是  
    ```docker export -o REPOSITORY:TAG```  
    的方式其均为了确定一个镜像，然而实践发现如果采用第一种方式导出的镜像tar包上传会出现不可用的状态，在日志中发现具体的原因是这个镜像没有TAG，所以使用第二种方式进行镜像的导出可以解决这个问题。
3.  在升级应用的时候，需要注意不仅仅要选择新的版本，如果蓝图中存在公共服务，在升级页面中逻辑公共服务名称中后面的绑定公共服务名称里应该选择绑定相应的公共服务。否则会出现下述问题
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
4.  在PaaS平台中msb中无法登录portal的问题，用户明和密码均正确，很有可能是由于redis的问题导致的，首先看是否是节点飘逸，可以执行脚本 [cluster_recovery.sh](resource/cluster_recovery.sh)  
    查询redis的容器  
    ```docker ps | grep commsrvredis```  
    在其中不含有POD的容器中任选一个执行上述脚本。
5.  如果出现Could not connect to Redis at IPV6ADDRESS: Connection refused，那查询redis是否支持Ipv6，在portaladmin的地址下-公共服务-redis可以查询版本，如果版本号3.04.06.01.03之前，那么是不支持ipv6的。
6.  在部署PaaS的时候如果出现了  
    ```
    EEROR - getting no node info will cause deploying error, please check posd and the uniview
    ERROR - deploy_baremetal_node_os error
    ERROR - Execute initial component: posd rear hook failed!
    ```
    的问题的话，查询是否存在OSCA的配置文件。
7.  如果在创建蓝图，点击新建后就出现了“被移除项目”的问题的话，尝试使用Chrome浏览器并清空缓存
8.  部署集成UME的OTCP时出现  
    ```failed product [umebn-unified-servicecenter], reason - service unfie```  
    的问题，查看otcp的包，发现在/app/umebn-unified-servicecenter/umebn-unified-servicecenter-v12.19.10.b06.tar.gz/umebn-unified-servicecenter-v12.19.10.b06..tar/umebn-unified-servicecenter/中缺少snctunnel-center-ui的镜像，建议打补丁
9.  注意镜像的版本号可以自定义，但蓝图的版本号需要由v开头，**最多只有一个“.”** 否则在部署的时候会出现非法的版本号的问题，而且如果出现这种问题只能重新构建蓝图，没有办法修改已存在蓝图的版本号。
10. 在部署OTCP的过程中出现存储集群错误，具体Log如下
    ````
    modify quota success
    post create persistentvolume [home-apds-type-repository]  to PaaS fail, cause by {"message":"Storage Cluster is not available, Please connect Administrator!"}
    post create persistentvolume [home-apds-type-repository]  to PaaS fail, cause by {"message":"Storage Cluster is not available, Please connect Administrator!"}
    Traceback (most recent call last):
      File "/home/ubuntu/paasdata/oki-tools/tools/basetools/volume/persistentvolume_create.py", line 369, in main
        createOrUpdatePersistentVolumeFuntion(createOrUpdateQueue)
      File "/home/ubuntu/paasdata/oki-tools/tools/basetools/volume/persistentvolume_create.py", line 264, in createOrUpdatePersistentVolumeFuntion
        createPersistentVolume(controllerip, accesstoken, tenantid, volumeName, volumeNum)
      File "/home/ubuntu/paasdata/oki-tools/tools/basetools/volume/persistentvolume_create.py", line 89, in createPersistentVolume
        raise Exception(e.message)
    Exception: post create persistentvolume [home-apds-type-repository]  to PaaS fail, cause by {"message":"Storage Cluster is not available, Please connect Administrator!"}
    ````