-   问题描述
    创建镜像时出现错误，包括无法上传镜像，镜像无法部署。
-   解决方案    
    1.  部署一个新的镜像时，如果出现无法上传的问题。  
        注意是将镜像导出，而非容器，使用命令  
        ```docker save [OPTIONS] IMAGE```  
        导出.tar的docker镜像，如果使用  
        ```docker export [OPTIONS] CONTAINER```      
    2.  成功上传镜像，正常的状态为“远端部署”或者“可用”，当出现不可用的时候，点击不可用如果错误信息为toolbox.swr.bin.failReason,具体信息显示"MANIFEST_UNKNOWN"。考虑是打包的方式不对
        在镜像导出的时候，无论使用  
        ```docker export -o IMAGE_ID```  
        还是  
        ```docker export -o REPOSITORY:TAG```  
        的方式其均为了确定一个镜像。  
        然而实践发现如果采用第一种方式导出的镜像tar包上传会出现不可用的状态，在日志中发现具体的原因是这个镜像没有TAG，所以使用第二种方式进行镜像的导出可以解决这个问题。
        > 出现这个问题不知是否为PaaS平台的问题，因为原理上这两种方式导出的镜像是完全一样的，使用ID导出的镜像再次导入也包含TAG信息，不知后续是否出现  
        > 当前版本：paas_offline_v1.18.20.13.p01
