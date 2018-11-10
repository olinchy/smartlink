-   问题描述  
    在msb中找到portal，但是无法登录，页面可以显示，但用户名和密码部正确。
-   解决方案  
    1.  在PaaS平台中msb中无法登录portal的问题，用户明和密码均正确，很有可能是由于redis的问题导致的。如果存在节点漂移的问题，导致redis无法连接到redis-server，这种情况执行脚本[cluster_recovery.sh](resource/cluster_recovery.sh)  
        查询redis的容器```docker ps | grep commsrvredis```，找到其中不含有POD的容器中任选一个执行上述脚本。
    2.  如果执行上述脚本的过程中出现
        ```
        Could not connect to Redis at IPV6ADDRESS: Connection refused
        ```
        可能出现的情况为那查询redis是否支持Ipv6，在portaladmin的地址下-公共服务-redis可以查询版本，如果版本号3.04.06.01.03之前，那么是不支持ipv6的。  
        建议使用集成的redis版本高于3.04.06.01.03的PaaS平台，已知paas-offline-v1.18.20.13.p03版本满足要求。
