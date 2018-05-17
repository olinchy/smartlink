#### networking plan
![networking plan pic.1](../networking.png)
##### prepare ip address
- create static route for admin connections (ps: i donot know why) (<b>version repository deployed locally</b>)
![necessary connections](../route.png) 
```
## 10.67.18.8 is the address of PaaS repository
## admin connection for administrator
route add -net [ip section of terminal].0/24 gw [local gateway]
## admin connection to PaaS repository
route add -net 10.67.18.0/24 gw [local gateway]
```
- ip address deployment
```
## use public ip as access from client terminal
## create admin net using 88.88.1.* in any interface (don't know why)
## enp129s0f0 connect to where and why 88.88.1.*?
ifconfig enp129s0f0 88.88.1.2/24 up

```
- 4 ip
1. 1 public ip for controller
2. 2 continuous ip use as floating ip
3. temporary ip for version downloading 

- network realms
1. net_api : physnet1 or physnet2
2. net_mgt : physnet1 or physnet2
3. net_iapi : physnet0  
4. net_ctrl : physnet1 or physnet2
5. net_media : physnet1 or physnet2 
6. summary:
    - count(interfaces with connection) = count(physnet)
    - physnet0 with public ip
    - physnet1 or physnet2 and so on with no private ip

##### install pdm-cli & CPaaS offline 

- download and install pdm-cli [CPaaS package ver. 1.17.30.03.p10](https://artxa.zte.com.cn:443/artifactory/oes_tcp-release-generic/embpaas/both/v1.17.30.03.p10_1595805_1/version) 
```
## after download the packages
## enter dowload folder
## 33 files in total
mkdir -p /paasdata/offline/paas
mv paas* /paasdata/offline/paas
cd /paasdata/offline/paas
cat paas*.tar.gz* | tar -xzf - && cd pdm-cli && ./install.sh
```

##### configure parameters for PaaS
- questions: 
  - what are phynet1 and phynet2 for?
  - How to deploy ZICT PaaS with only one network interface?
- steps:
 1. understand 'net_api', 'net_mgt', 'net_admin' and 'net_store'(see also pic.1)
 2. modify [/etc/pdm/conf/vnm_network.conf](./vnm_network.conf)
     ```
    # origin from UEP : network_vlan_ranges = physnet0:100:200,physnet1:100:200,physnet2:100:200
    # still don't know what does vlan for and Can I ignore it? 
    # according to /etc/network/Inet_deploy.conf.tmpl, 3 phynet assigned to same interface, so still follow uep for now 
    network_vlan_ranges = physnet0:100:200,physnet1:100:200,physnet2:100:200
     ``` 
 3. modify [/etc/pdm/conf/conf.json](./conf.json) according to <b>chapter - 4.2</b> in [http://openpalette.zte.com.cn/docs/ver/v1.17.30.03.p10/installation_guide/paasInstall/n_merged_into_one_new.html#id10](http://openpalette.zte.com.cn/docs/ver/v1.17.30.03.p10/installation_guide/paasInstall/n_merged_into_one_new.html#id10)
     ```
     ## set network type to 'flat' means segmentation_id can be null
     "provider:network_type": "flat",
     "provider:physical_network": "physnet0",
     "provider:segmentation_id": ""
     ```
 4. modify [/etc/network/Inet_deploy.conf.tmpl](./Inet_deploy.conf.tmpl) (using only one network interface)
 5. modify [/etc/pdm/OCSA_VM.conf](./OCSA_VM.conf)

 6. deploy offline
    ```
    pdm-cli deploy --offline
    ```
#### verify deploy 

   
