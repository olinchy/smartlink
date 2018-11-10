-   问题描述
    部署集成UME的OTCP成功，但是显示其中一个组建umebn-unified-servicecenter失败
-   解决方案    
    1.  部署集成UME的OTCP时出现      
        ```
        [services_install] ===================  99% [fail]                          
        success count : 83
        fail count : 1
        failed product [umebn-unified-servicecenter] , reason = service unified-servicecenter : Deploy service timeout over 5 Min, Error: ImagePullBackOff
        /home/ubuntu/paasdata/oki-tools/tools/basetools/bin/ume/deploy_service.sh time_consuming: 0:10:42
        docs_path not found ,docs_path=/home/ubuntu/paasdata/docs
        /home/ubuntu/paasdata/oki-tools/tools/basetools/bin/uploaddocument/doc_upload.sh time_consuming: 0:0:0
        ===== install component services finish =====
        ```  
        查看otcp的包，发现在  
        ```/app/umebn-unified-servicecenter/umebn-unified-servicecenter-v12.19.10.b06.tar.gz/umebn-unified-servicecenter-v12.19.10.b06..tar/umebn-unified-servicecenter/```  
        中缺少```snctunnel-center-ui```的镜像，建议打补丁,补丁地址：  
        ```\\10.8.9.82\版本发布\测试版本\UMEBN\v12.19.10.b06-patch\umebn\umebn-unified-servicecenter```
