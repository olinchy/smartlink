##  PICT PaaS Contacts
####   maintainer 
- **王国强** 10162373    
PaaS平台   
问题：1. Execute initial component: posd rear hook failed. 2.新建蓝图被移除。 3.不可上传镜像   
结果：1. 修改OSCA文件 2.清浏览器缓存 3.找张文杰
- 张文杰 10103717  
PaaS平台  
问题：不可上传镜像  
结果：找刘立国    
- **刘立国** 10244505  
PaaS平台  
问题：1.部署后状态为不可用。2.蓝图升级后，没有配置公共服务的问题。  
结果：1.修改导出方式 2.配置公共服务
- **孙薇** 00241582  
redis公共服务  
问题：节点漂移引起不可登录  
结果：使用cluter_recovery.sh脚本修复后发现不是节点漂移，后发现redis版本不支持ipv6导致，建议换新版本
- 张振兴 10072017  
版本对应  
问题：询问PaaS和redis版本对应关系  
结果：解决   
- 时金萍 00209491  
PaaS平台部署
####   CMO
- unkonwn     

####   Project Leader
- unkonwn     

## oki-tools
**姚成柱** 10191087
问题：oki-tools的版本替换，redis选择版本

##  OTCP PaaS Contacts
##### maintainer 
- 叶大明 10084519
- 唐渝 10144605

##### Project Leader
- 刘学生 10019830

##### Development Framework
- 胡锐 10071214 

##### CMO
- 刘敏 00032305     

## UME BN Contacts
##### maintainer 
- 蒋书轶 10045466 (PORTAL)
- 孟彬 10065029 (EM)
- 史长城 (TOPO)
- 周璇 (PM)
- 陈小云 成研 (PM检测点)
- 刘海瑞 (FM)
- 张辉 (LinkLcm)

##### Integrate Tester
- **张洪军** 00075157    
UME  
问题：集成UME的OTCP部署后，umebn-unified-servicecenter没有部署成功  
结果：联系刘伟
- 刘伟 10039910  
UME  
问题：集成UME的OTCP部署后，umebn-unified-servicecenter没有部署成功  
结果：联系周瑶
- 周瑶 00207654  
UME(umebn-unified-servicecenter组件团队)  
问题：集成UME的OTCP部署后，umebn-unified-servicecenter没有部署成功  
结果：缺少镜像，需要打补丁

##### CMO 
- 丁虹 00103784

##### Project Leader
- 张曰明 10017591 (PTN)

##### SA
- 张晓东 10033733  

  
## Version Local
以下描述的版本均可在ftp://10.86.96.6/a_个人使用路径__网管个人使用/qzp/paasdata 找到  
- PICT PaaS Offline  
  - PICTPaaS_offline_v1.18.20.11:裸PaaS，但在部署的时候发现缺少公共服务
- CPaaS Offline
  - CPaaS_offline_v1.17.20.03.p05:旧版本的CPaaS,与新版本的OTPC不兼容产生租户无法创建的问题
  - CPaaS_offline_v1.18.20.11_2449090_12:公共组件中的redis版本较低,不支持ipv6,无法登录ume portal
  - CPaaS_offline_v1.18.20.13.p01:公共组件中的redis版本较低,不支持ipv6,无法登录ume portal
  - CPaaS_offline_v1.18.20.13.p03_2525154_1:可用,但注意需要替换oki-tools,自带的oki-tools没有适应PaaS接口变化,持久化卷会出现问题
- oki-tools
  - oki-tools-v1.18.30.06.p02.tar.gz:因为无法对redis版本进行选择,没有redis-choose的配置文件
  - oki-tools-v1.18.30.06.p03.tar.gz:可用,但注意需要修改租户名称以及redis实例的密码  


## Version Privilege Request
- PICT PaaS Offline
    ```
    https://artnj.zte.com.cn/artifactory/webapp/#/artifacts/browse/simple/General/zxnp_pict-snapshot-generic/official_latest_pkg/v1.18.20.11/paas
    username : zxnp_pict-ci 
    password : bR0H94X1mnlb
    ```
- OTCP PaaS
    ```
    https://wiki.zte.com.cn/pages/viewpage.action?pageId=433987588
    https://artxa.zte.com.cn/artifactory/oes_tcp-release-generic/embpaas/both/
    ```
- UMEBN 
    ```
    https://artsz.zte.com.cn/artifactory/umebn-release-generic/release/v12.19.10.b06.sp001/
    ```