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