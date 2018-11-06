# Paas的安装及配置
#### 准备：装系统的U盘一个，刀片服务器一个，三个未被分配的IP地址  
> 注：3个IP其中两个为连续IP，另一个IP是安装时使用的临时IP，安装后完成后会被释放。  

#### 安装步骤：  
-   将U盘插到刀片上，按del进入bios设置从U盘启动，然后重启自动安装系统，选择Install images  

    > 注:安装系统时如卡住出现Attached SCSI removable disk提示，大概率是连接刀片的数据线出现了问题，需要更换数据线重新安装。安装系统时如出现多次的链接，断开USB设备，建议更换数据线重试，大概率是数据线路出现了问题。  
    
-   系统安装完成后，首先进入系统配置网络设置  
    默认帐号:ubuntu  
    默认密码:cloud  
    登录成功后进入root用户进行网络配置  
-   输入以下命令  
    ```
    ## config temporary public ip
    ## ifconfig [PUBLIC CONNECTED INTERFACE] [TEMP IP] up
    ifconfig enp129s0f1 10.86.110.35 up
    
    ## admin connection from terminal to controller
    ## route add -net [xxx.xxx.xxx.xxx/xx] gw [local gateway]
    route add -net 10.0.0.0/8 gw 10.86.110.1
    ```  
-   接下来测试一下是否能ping通自己的电脑，然后就可以远程连接临时ip进行后续配置了，连接方式   
    ```
    ssh ubuntu@10.86.110.35
    ```  
-   远程连接后，还要进行两项网络配置，命令如下  
    ```
	## create admin net using 88.88.1.* in any interface
	ifconfig enp129s0f0 88.88.1.2/24 up
	
	## admin connection to PaaS repository
	## 10.67.18.8 is the address of PaaS repository
	## route add -net 10.67.18.0/24 gw [local gateway]
	route add -net 10.67.18.0/24 gw 10.86.110.1
    ```  
-   进行完网络配置后，从ftp上找到PaaS package文件拷贝到/home/ubuntu/paasdata目录下(此处使用的PaaS版本为18.20,可以在ftp://10.86.96.6/a_个人使用路径__网管个人使用/qzp/paasdata/paas_offline_1820下载）  
    
    > 注：拷贝完成后注意查看一下拷贝后的文件是否完整，大小是否一致  

-   然后运行如下命令  
    ```
	## cd [upload directory]
	cd /home/ubuntu/paasdata/
	mkdir -p /paasdata/offline/paas
	mv paas* /paasdata/offline/paas
	cd /paasdata/offline/paas
	cat paas*.tar.gz* | tar -xzf - && cd pdm-cli && ./install.sh
    ```  
-   等待大约3分钟后相关文件就安装好了，然后需要修改3个配置文件，新增1个配置文件  
    1.  修改/etc/pdm/conf/vnm_network.conf 如下  
        ```
	    [neutron]
	    bridge_mappings = physnet0:br-phy0,physnet1:br-phy1
        type_drivers = vlan,flat
        tenant_network_types = vlan
        flat_networks = physnet0
        network_vlan_ranges = physnet0:1000:1030,physnet1:1000:1030
        external_network_bridge = br-iapi
        l3agent_type=neutron
        ```  
        >注:在1830的版本里，vnm_network.conf的文件内容比这个要多一些，可以将多的配置删除，在这个版本的部署过程中暂未出现问题。  
    2.  修改/etc/pdm/conf/conf.json  
        >注:此文件修改部分较多，建议直接删除，然后拷贝现成配置好的文件进去。
        ```
        {
          "region": {
            "scenariotype": "baremetal",
            "region_detail": ""
          },
          "iaas": {
            "url": "http://10.62.97.112:5000/v2.0/",
            "tenantName": "paas1",
            "username": "paas1",
            "password": "paas1"
          },
          "nodepools": [
            {
              "name": "default_np",
              "min_num": 0,
              "max_num": 10,
              "step": 1,
              "upper_limit": 100,
              "lower_limit": 0,
              "hostname": "",
              "timezone": "",
              "vm_conf": {
                "flavor_name": "flavor_node",
                "image_name": "node",
                "boot_mode": "",
                "snapshot_id": "",
                "volume_size": "",
                "volume_az": "",
                "volume_type": "",
                "available_zone": ""
              },
              "storage_info": [
                {
                  "volume_size": null,
                  "volume_type": null,
                  "volume_az": null,
                  "guestformat": null,
                  "mountpoint": null
                }
              ]
            }
          ],
          "roles": [],
          "roles_relation": [
            {
              "name": "com_srv_share",
              "num": 1,
              "deploy_in_role": [
                "paas_controller"
              ]
            },
            {
              "name": "cf-srepo",
              "num": 1,
              "deploy_in_role": [
                "paas_controller"
              ]
            },
            {
              "name": "elk",
              "num": 0,
              "deploy_in_role": [
                "paas_controller"
              ]
            }
          ],
          "clusters": [
            {
              "name": "cluster1",
              "type": "kubernetes",
              "nodes": [
                {
                  "roles": [
                    "master",
                    "minion"
                  ],
                  "node_num": 1,
                  "node_source": {
                    "np_name": "",
                    "node_role": [
                      "paas_controller"
                    ]
                  },
                  "node_config": {
                    "app_exclusive_count": 0,
                    "hugepage_2m_total_size": 0
                  },
                  "labels": {
                  }
                }
              ]
            }
          ],
          "storage": {
            "storage": "local",
            "cluster": {
              "name": "glusterfs_server",
              "volume": "100"
            }
          },
          "networks": [
            {
              "name": "control",
              "public": true,
              "desc": "PaaS control network for tcfs",
              "gateway": "172.28.0.1",
              "cidr": "172.28.0.0/16"
            },
            {
              "name": "media",
              "public": true,
              "desc": "PaaS media network for tcfs",
              "gateway": "172.29.0.1",
              "cidr": "172.29.0.0/16"
            }
          ],
          "overlay_networks": [
            {
              "name": "control",
              "public": true,
              "desc": "PaaS control network for tcfs",
              "gateway": "172.28.0.1",
              "cidr": "172.28.0.0/16"
            },
            {
              "name": "media",
              "public": true,
              "desc": "PaaS media network for tcfs",
              "gateway": "172.29.0.1",
              "cidr": "172.29.0.0/16"
            },
            {
              "name": "net_api",
              "public": true,
              "desc": "PaaS network for pod",
              "gateway": "172.30.0.1",
              "cidr": "172.30.0.0/16"
            },
            {
              "name": "net_mgt",
              "public": false,
              "gateway": "172.31.0.1",
              "cidr": "172.31.0.0/16"
            }
          ],
          "pim": {
            "pim_address": "10.62.55.50",
            "pim_port": "2043"
          },
          "general_mode": {
            "api_ip": "",
            "man_ip": ""
          },
          "bm_network": {
            "default_physnet": "physnet1",
            "paas_controller_ip": [
              {
                "api_ip": "172.20.0.2",
                "man_ip": "172.21.0.2",
                "admin_ip": "88.88.1.2"
              }
            ],
            "api_vlan_id": "1001",
            "mgt_vlan_id": "1002",
            "net_api": {
              "subnet": [
                {
                  "name": "subnet_api",
                  "enable_dhcp": true,
                  "gateway_ip": "172.20.0.1",
                  "allocation_pools": [
                    {
                      "start": "172.20.0.2",
                      "end": "172.20.10.253"
                    }
                  ],
                  "ip_version": 4,
                  "cidr": "172.20.0.0/16"
                },
                {
                  "name": "subnet_api_v6",
                  "enable_dhcp": true,
                  "gateway_ip": "3ffe:ffff:0:f101::1",
                  "allocation_pools": [
                    {
                      "start": "3ffe:ffff:0:f101::100",
                      "end": "3ffe:ffff:0:f101::1ff"
                    }
                  ],
                  "ip_version": 6,
                  "cidr": "3ffe:ffff:0:f101::/112"
                }
              ],
              "provider:physical_network": "physnet1"
            },
            "net_mgt": {
              "subnet": {
                "name": "subnet_mgt",
                "enable_dhcp": true,
                "gateway_ip": null,
                "allocation_pools": [
                  {
                    "start": "172.21.0.1",
                    "end": "172.21.10.253"
                  }
                ],
                "ip_version": 4,
                "cidr": "172.21.0.0/16"
              },
              "provider:physical_network": "physnet1"
            },
            "net_iapi": {
              "subnet": [
                {
                  "name": "subnet_iapi",
                  "enable_dhcp": false,
                  "gateway_ip": "10.86.110.1",
                  "allocation_pools": [
                    {
                      "start": "10.86.110.251",
                      "end": "10.86.110.252"
                    }
                  ],
                  "ip_version": 4,
                  "cidr": "10.86.110.0/24"
                },
                {
                  "name": "subnet_iapi_v6",
                  "enable_dhcp": false,
                  "gateway_ip": "4ffe:ffff:0:f101::1",
                  "allocation_pools": [
                    {
                      "start": "4ffe:ffff:0:f101::10e",
                      "end": "4ffe:ffff:0:f101::10f"
                    }
                  ],
                  "ip_version": 6,
                  "cidr": "4ffe:ffff:0:f101::/112"
                }
              ],
              "provider:physical_network": "physnet0",
              "provider:network_type": "flat",
              "provider:segmentation_id": ""
            },
            "net_ctrl": {
              "provider_network": {
                "public": true,
                "name": "control",
                "desc": "PaaS control network for tcfs",
                "gateway": "172.28.0.1",
                "cidr": "172.28.0.0/16",
                "provider:network_type": "vlan",
                "provider:physical_network": "physnet1",
                "provider:segmentation_id": "1003"
              }
            },
            "net_media": {
              "provider_network": {
                "public": true,
                "name": "media",
                "desc": "PaaS media network for tcfs",
                "gateway": "172.29.0.1",
                "cidr": "172.29.0.0/16",
                "provider:network_type": "vlan",
                "provider:physical_network": "physnet1",
                "provider:segmentation_id": "1004"
              }
            }
          }
        }
        ```  
    3.  修改 /etc/network/Inet_deploy.conf.tmpl 如下  
        ```
        [default]
        version=1.1
        provider=ovs
    
        [bond]
    
        [ovs]
        vlan_splinters=off
        datamapping=[physnet0:br-phy0:enp129s0f1,physnet1:br-phy1:enp2s0f1]
        admin=enp2s0f1
        papi=enp2s0f1
        pmgmt=enp2s0f1
        iapi=enp129s0f1
        ```  
    4.  新建配置文件 /etc/pdm/OCSA_VM.conf 添加如下内容  
      
        > 注意，此处文件名不能写错，否则在部署的过程中回无法找到paas控制器的配置，在Deploy initial component: posd.时会发生ERROR - Execute initial component: posd rear hook failed!的错误  

        ```
        {"paas_controller":
             [
                {
                    "id": "289016500191",
                    "serialNo": "289016500191",
                    "podId": "None",
                    "cpuCore": 40,
                    "cpuFrequency": "2.3GHz",
                    "memory": 128,
                    "disk": 280,
                    "hardwareType": "OCSAServer: ZTE-MW-UME-001",
                    "hardwareStatus": "normal"
                }
            ]
        }
        ```  
-   都修改完成后就可以直接离线安装了，大约1-2个小时，命令如下  
    ```pdm-cli deploy --offline```  
-   安装完成后，此时可以打开浏览器输入http://[ip]/portal/#/login  
    验证一下，此ip是前面准备的两个连续ip，如果看见登录页面，就代表安装成功了。  
# otcp的安装及配置  
#### 权限控制描述  
在PaaS1820中，浏览器打开有两个模式，http://[ip]/portal打开的是用户模式，用户登录用户模式可以建立项目，上传镜像，建立蓝图等等。http://[ip]/portaladmin打开的是管理者模式，可以使用默认用户(用户名:admin,密码11111)登录，在管理者模式下可以对用户进行管理，但注意如果步进入项目中是无法进行构建蓝图等操作的。  

> admin/111111这个默认用户是无法登录http://[ip]/portal，如果使用admin对项目进行操作，在管理这模式下管理所有项目可以对项目进行管理。  

#### 配置步骤  
-   首先登录paas配置net_hmf和net_ne  
    打开浏览器输入http://10.86.110.251/portaladmin进行登录  
    帐号:admin 密码:111111  
    然后进入下面网址进行配置  
    http://10.86.110.251/portaladmin/#/main/resourcemanager/sharednetwork/list  
-   net_hmf配置过程如下：  
    ```
	##点击+Add进入配置页面
	Name:net_hmf
	CIDR:108.28.170.0/24
	Public:yes
	Provider Netword:yes
	Physicl Network Name:physnet1
	Network Type:vlan
	Vlan Transparent:no
	Vlan ID:1026 (在PaaS1730的版本中，这个配置名称是Segment ID)	
	##然后点击Add，配置成功
    ```  
-   net_ne配置过程如下：  
    ```
	##点击+Add进入配置页面
	Name:net_ne
	CIDR:108.28.170.0/24
	Public:yes
	Provider Netword:yes
	Physicl Network Name:physnet1
	Network Type:vlan
	Vlan Transparent:no
	Vlan ID:1027 (在PaaS1730的版本中，这个配置名称是Segment ID)	
	##然后点击Add，配置成功
    ```  
-   接下来修改pods，pods默认110个不够用，需要进行扩容，扩容命令如下  
    ```
	vi /etc/kubernetes/kubelet
	##找到KUBELET_ARGS，在KUBELET_ARGS中添加
	--max-pods=200
	##注意这个参数和前一个参数之间有一个空格隔开，根据自己需要自定义值。wq保存一下。
	##使用下列命令生效配置
	systemctl restart kubelet
	##使用下列命令查看是否生效成功
	kubectl describe node
    ```  

#### otcp安装步骤：  
注意在早版本的otcp中是不集成ume的相关内容的，假如部署后发现不存在umebn的项目，说明部署的版本没有集成ume的相关构件，建议重头开始。重新安装，如果发现在部署otcp的开始过程中出现无法创建租户的错误，说明PaaS版本没有和otcp版本匹配，建议重装PaaS平台以适应otcp的版本。  
-   接下来，从ftp上获取otcp文件(可在ftp://10.86.96.6/a_个人使用路径__网管个人使用/qzp/paasdata/otcp_1830下载)，将otcp文件拷贝到/home/ubuntu/paasdata目录下，然后解压缩，接下来进入到/home/ubuntu/paasdata/oki-tools/tools/bin目录下，执行如下命令  
    ```
	chmod a+x oki-cli
	# disable gbase
	./oki-cli component set --gbase=false

	# these components depends on GBase, delete it also
	rm -rf ../../../otcp/OES_Analytics_STREAM/
	rm -rf ../../../otcp/OES_Analytics_PM_OfflineAnalys/
	(如果使用新的otcp，如1830，则包内不带有这两个文件可以忽略这部分的内容)

	./oki-cli install -m all -t otcp
    ```  
-   然后等待大约1个小时，安装完成后登录paas会显示  
-   在安装otcp1830的过程的最后，会发现  
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
    这个错误，经验证发现，这个错误是因为ume部分的问题，并不是部署的原因，具体因为在otcp包中/app/umebn-unified-servicecenter/umebn-unified-servicecenter-v12.19.10.b06/umebn-unified-servicecenter的目录下找不到snctunnel-center-ui的镜像导致umebn-unified-servicecenter无法部署成功  
    解决办法：安装相应补丁即可，补丁地址：\\10.8.9.82\版本发布\测试版本\UMEBN\v12.19.10.b06-patch\umebn\umebn-unified-servicecenter  
# Hello World!  
-   编写Spring boot项目  
    1.  DockerApplication.java如下  
        ```java
        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        @SpringBootApplication
        public class DockerApplication {
            public static void main(String[] args) {
                SpringApplication.run(DockerApplication.class, args);
            }
        }
        ```  
    2.  DockerController.java如下  
        ```java
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        @RestController
        public class DockerController {
            @RequestMapping("/")
            public String index() {
                return "Hello World";
            }
        }
        ```  
    3.  pom.xml文件如下  
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
            <modelVersion>4.0.0</modelVersion>
            <groupId>com.zte</groupId>
            <artifactId>testPaas</artifactId>
            <version>1.0-SNAPSHOT</version>
            <parent>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-parent</artifactId>
                <version>2.0.0.RELEASE</version>
            </parent>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-web</artifactId>
                </dependency>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-test</artifactId>
                    <scope>test</scope>
                </dependency>
            </dependencies>
            <build>
                <plugins>
                    <plugin>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-maven-plugin</artifactId>
                    </plugin>
                </plugins>
            </build>
        </project>
        ```  
    4.  执行```mvn package```  
-   将Spring boot 项目通过docker打包成tar镜像  
    1.  编写dockerfile文件用于描述要打包的镜像  
        ```
        FROM openjdk:8-jdk-alpine
        MAINTAINER testPaasAuthor
        ENV http_proxy=http://proxy/zte.com.cn:80/
        ADD testPaas-1.0-SNAPSHOT.jar app.jar
        EXPOSE 8080
        ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar","/app.jar"]
        ```  
    2.  深入命令构建镜像并导出  
        ```
        sudo docker build -t testpaasauthor/testpaas .
        sudo docker save -o testpaas.tar testpaasauthor/testpaas
        ```  
-   将tar镜像上传  
-   构建蓝图，部署，提供访问地址  
    1.  在http://[ip]/portal登录，此处使用(admintest/111111为新建的账户),填写项目名称，其他保持默认。  
    2.  点击进入项目，Software Repository-Image-Import，填写镜像名称和版本号，选择打包好的tar文件，"Released as a public image"为是否将这个镜像发布为公共镜像，公共镜像是其他的项目也可以使用的。  
    3.  点击Upload即可完成。  
    4.  发布镜像后，在Software Repository-Blueprint-New Blueprint  
    5.  进行配置Type选择Microservice，输入名称和版本号，Frame type选择Pod  
        >注意此步如果出现被踢出项目，或其他奇怪的问题，可以尝试使用Chrome浏览器，并清除缓存。  
    6.  进入蓝图配置后，点击Pod/Container，找到刚刚发布的镜像，将其拖动至蓝图名称的框内。  
    7.  添加EndPoint，点击Basic Type-EndPoint，将其拖动到镜像框的边缘。  
        >注意此步骤要将其拖动到框的边缘  
    8.  单击镜像进行配置，选择Advanced settings-Port，在Protocol选择TCP，并填入docker镜像暴露的接口。  
    9.  单击EndPoint，在Port中选择刚刚配置的端口。protocol选择HTTP，Visual range选择External。  
    10. 再次拖动一个Nic到蓝图最外框，点击进行配置，Attach to network 输入lan  
    11. 保存并部署