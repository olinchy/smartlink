-   问题描述
    替换oki-tools后部署OTCP出现公共服务找不到的错误。
-   解决方案    
    1.  替换oki-tools为新的版本以解决无法创建卷后，部署otcp，创建公共服务出现  
        ```
        *********************************
        (1) create tenant
        (2) create persistent volumes
        (3) create common service and instance
        (4) tcp image build
        (5) oki image build
        (6) spd file upload
        (7) deploy oki
        (8) install services
        (0) Exit Menu
        *********************************
        please enter your choice : 3
        ===========begin oprerate commonservice===========
        getCommonServiceVersion fail,name=commsrv_zenap_ftpv3_bp
        getCommonServiceVersion fail,name=commsrv_kafka_vnpm_bp
        getCommonServiceVersion fail,name=commsrv_pg_bp
        getCommonServiceVersion fail,name=commsrv_internal_redis_bp
        getCommonServiceVersion fail,name=commsrv_elasticsearch_vnpm_bp
        getCommonServiceVersion fail,name=commsrv_pgcache_bp
        getCommonServiceVersion fail,name=commsrv_activemq_vnpm_bp
        getCommonServiceVersion fail,name=commsrv_zookeeper_vnpm_bp
        ("'NoneType' object has no attribute '__getitem__'",)
        Traceback (most recent call last):
          File "/home/ubuntu/paasdata/oki-tools/tools/basetools/commonservice/commonserviceinstance_create.py", line 482, in <module>
            logger.error('create commonservice fail,{}'.format(a_commonservice_result.get()))
          File "/usr/lib64/python2.7/multiprocessing/pool.py", line 554, in get
            raise self._value
        Exception: ("'NoneType' object has no attribute '__getitem__'",)
        create common service or instance fail (("'NoneType' object has no attribute '__getitem__'",),)
        ```
    2.  注意不要使用PICT PaaS，虽然otcp中也带有公共服务，但应该这部分公共服务和CPaaS的公共服务并不重叠，使用PICT PaaS会出现无法找到服务的错误。