-   问题描述
    部署集成UME的OTCP出现问题，无法开始部署，创建存储失败
-   解决方案    
    1.  在部署OTCP的过程中出现存储集群错误，具体Log如下
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
        具体原因是部署PaaS的方式为裸机N合1，这种方式是不支持部署glusterfs_server的，因为glusterfs_server至少部署三个节点。  
        可以在部署好的蓝图中修改持久化方式为hostPath(出现这个问题只是没有部署，但蓝图创建是成功的，可以找到)  
        修改位置：编辑蓝图，点击蓝图——高级参数配置——输入参数——挂载卷参数
        但蓝图数量较多，所以比较麻烦，可以使用新版的otcp，可以规避这个问题  
    2.  在部署otcp的时候，oki可以按照安装PaaS的场景进行自动的处理，不需要手动修改，出现这个问题可能因为使用了过程版本，PaaS接口变更，导致oki辨别出错。
    3.  最新的版本(2018-11-08)，当前最新的版本，所使用的PaaS平台为R9的20.11。
    4.  N合1的环境中，PaaS接口变化，在v1.18.30.06.p02中已经修正，版本地址  
        ```
        https://artxa.zte.com.cn/artifactory/webapp/#/artifacts/browse/tree/General/oes_tcp-snapshot-generic/components/oki-toos/v1.18.30.06.p02/normal/oki-tools-v1.18.30.06.p02.tar.gz        
        ```
        但在这个版本中是没有集成UME的相关内容的，建议将v1.18.30.06.p02中的oki-tools替换。同时要替换config中的内容        