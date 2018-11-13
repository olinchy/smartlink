由于上篇部署文档使用的版本较旧，使用新版本部署的时候出现种种问题，所以此处重新写部署文档。  
## 1. 版本描述
- PaaS版本：CPaaS v1.18.20.13.p03.tar.gz  
ftp://10.86.96.6/a_个人使用路径__网管个人使用/qzp/paasdata/            
- otcp&ume版本：otcp-v1.18.30.06.p01  
ftp://10.86.96.6/a_个人使用路径__网管个人使用/qzp/paasdata/otcp&ume/otcp-v1.18.30.06.p01.tar.gz
- oki-tools版本：oki-tools-v1.18.30.06.p02  
ftp://10.86.96.6/a_个人使用路径__网管个人使用/qzp/paasdata/oki-tools/oki-tools-v1.18.30.06.p02.tar.gz

## 2. PaaS平台搭建
### 2.1 描述
PaaS平台最终搭建OpenPalette。OpenPalette是公司基于Kubernetes搭建的PaaS平台。此处部署的为CPaaS(也称embPaaS)，为PaaS平台加上公共服务，例如redis等等。与之相对应的为裸PaaS平台(PICT-PaaS)，即不带任何公共服务的PaaS平台，而我们在后续搭建OTCP以及UME时需要部分公共服务，所以此处搭建CPaaS，也可搭建PICT-PaaS后手动搭建公共服务，但难度与工作量较大，所以不推荐。
### 2.2 部署基本系统
1. 将U盘插到刀片上，按del进入bios设置从U盘启动，然后重启自动安装系统，选择Install images
2. 安装完成后进入系统，默认帐号:ubuntu，默认密码:cloud，进入系统后切换为root，默认密码cloud
3. 配置临时网络
    ```
    ifconfig enp129s0f1 10.86.110.35 up
    route add -net 10.0.0.0/8 gw 10.86.110.1
    ```
    > 注：这里10.86.110.35为临时IP地址，可以根据自己需要进行配置，在安装完CPaaS后这个地址不可用。  
4. 此时可以远程访问```ssh ubuntu@10.86.110.35```
5. 添加新的网段  
    ```
    ifconfig enp129s0f0 88.88.1.2/24 up
    route add -net 10.67.18.0/24 gw 10.86.110.1
    ```

### 2.3 CPaaS复制解压
1. 根据文档第1部分下载PaaS，并将文件拷贝至/home/ubuntu/paasdata下  
2. 拷贝至相应的位置，执行下述内容  
    ```
    mkdir -p /paasdata/offline/paas
    mv /home/ubuntu/paasdata/paas* /paasdata/offline/paas    
    ```
3. 解压，执行下述内容  
    ```
    cd /paasdata/offline/paas
    cat paas*.tar.gz* | tar -xzf - && cd pdm-cli && ./install.sh
    ```

### 2.4 修改配置文件
1. 修改网络配置文件/etc/pdm/conf/vnm_network.conf，可以使用[vnm_network.conf](resource/vnm_network.conf)对其覆盖    
2. 修改pdm配置文件/etc/pdm/conf/conf.json，可以使用[conf.json](resource/conf.json)对其覆盖
3. 修改部署网络配置/etc/network/Inet_deploy.conf.tmpl，可以使用[Inet_deploy.conf.temp](resource/Inet_deploy.conf.tmpl)对其覆盖
4. 新建PaaS控制器配置文件/etc/pdm/OCSA_VM.conf，可以使用[OCSA_VM.conf](resource/OCSA_VM.conf)直接复制

### 2.5 进行安装
1. 执行```pdm-cli deploy --offline```进行安装，部署成功控制台的日志可以参考[部署PaaS成功日志](../log/deploy_paas_success.md)，也可以登录[OpenPalette客户端](http://10.86.110.251/portaladmin)观察结果
2. 登录的用户名为admin，密码为111111

## 3. otcp安装配置
### 3.1 描述
这里所安装的otcp其实是otcp+ume的安装方式，在早些版本这两部分会分开安装，但在v1.18及以后推荐使用集成ume的otcp进行部署安装
### 3.2 网络配置
1. 登录[http://10.86.110.251/portaladmin](http://10.86.110.251/portaladmin)，用户名admin，密码111111
2. 点击“资源-网络”进行配置
3. 配置net_hmf，点击添加网络，配置参数如下
    ```
    Name:net_hmf
    CIDR:108.28.170.0/24
    Public:yes
    Provider Netword:yes
    Physicl Network Name:physnet1
    Network Type:vlan
    Vlan Transparent:no
    Vlan ID:1026
    ```
4. 配置net_ne，点击添加网络，配置参数如下
    ```
    Name:net_ne
    CIDR:108.28.170.0/24
    Public:yes
    Provider Netword:yes
    Physicl Network Name:physnet1
    Network Type:vlan
    Vlan Transparent:no
    Vlan ID:1027
    ```

### 3.3 节点扩容
1. 因为默认的pods是110个，然而配置otcp大概有150个pods，所以需要扩容，修改配置文件/etc/kubernetes/kubelet，在KUBELET_ARGS中添加--max-pods=200  
2. 重启查看是否生效，执行
    ```
    systemctl restart kubelet
    kubectl describe node
    ```

### 3.4 替换oki-tools
1. 因为otcp的版本为v1.18.30.06.p01，而这个版本没有针对PaaS接口变化进行改变，所以要替换别的版本的oki-tools
2. 在文档1中下载otcp，并进行解压
3. 在文档1中下载oki-tools，并进行解压
4. oki-tools替换otcp下的oki-tools
5. 修改tenant.conf中的租户ID为“umebn”
    > 注:这里如果部修改也无妨，但是umebn更清除体现为集成ume的otcp  

### 3.5 禁用gbase
1. 在/home/ubuntu/paasdata/oki-tools/tools/bin目录下执行```chmod a+x oki-cli```
2. 禁用gbase，执行```./oki-cli component set --gbase=false```

### 3.6 安装otcp
1. 执行```./oki-cli install -m all -t otcp```进行安装，部署成功控制台的日志可以参考[部署otcp&ume成功日志](../log/deploy_otcp_ume.md)  
    > 注:可以使用```./oki-cli install -m step -t otcp```分步的安装，这样可以更清除的看到部署的步骤