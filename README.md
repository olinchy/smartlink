#### networking plan
![networking plan pic.1](networking.png)
#### prepare to install PaaS
##### download PDM-CLI
```
curl -s -u zxpaas:zxpaas -o GetVerLink ftp://zxpaas@10.67.18.8/GetVerLink && chmod +x GetVerLink
```
- select target version of PaaS: [https://wiki.zte.com.cn/pages/viewpage.action?pageId=2850873](https://wiki.zte.com.cn/pages/viewpage.action?pageId=2850873)
- execute install via PDM-CLI
```
## ./GetVerLink v1.17.30.03.p10
./GetVerLink [version number]
```
- with showback
```
Please check the input version number : v1.17.30.03.p06
Download pdm-cli command:
curl -s -u zxpaas:zxpaas -o install-pdm-cli.sh ftp://10.67.18.8/v1.17/v1.17.30/v1.17.30.03.p06/install-pdm-cli.sh && bash install-pdm-cli.sh valid_srepo
Upgrade pdm-cli command:
curl -s -u zxpaas:zxpaas -o install-pdm-cli.sh ftp://10.67.18.8/v1.17/v1.17.30/v1.17.30.03.p06/install-pdm-cli.sh && sed -i 's/.\/install.sh/.\/upgrade.sh/g' install-pdm-cli.sh && bash install-pdm-cli.sh valid_srepo
```
- copy commands under <b>Download pdm-cli command:</b> and execute it to finish downloading PDM-CLI
- create static route for admin connections (<b>version repository deployed locally</b>)
![necessary connections](route.png) 
```
## 10.67.18.8 is the address of PaaS repository
## admin connection for administrator
route add -net [ip section of terminal].0/24 gw [local gateway]
## admin connection to PaaS repository
route add -net 10.67.18.0/24 gw [local gateway]
```
##### deploy PaaS(finally)
- questions: 
  - what are phynet1 and phynet2 for?
  - How to deploy ZICT PaaS with only one network interface?
- steps:
 1. understand 'net_api', 'net_mgt', 'net_admin' and 'net_store'(see also pic.1)
 2. modify /etc/pdm/conf/vnm_network.conf
     ```
    # origin from UEP : network_vlan_ranges = physnet0:100:200,physnet1:100:200,physnet2:100:200
    # still don't know what does vlan for and Can I ignore it? 
    # according to /etc/network/Inet_deploy.conf.tmpl, 3 phynet assigned to same interface, so still follow uep for now 
    network_vlan_ranges = physnet0:100:200,physnet1:100:200,physnet2:100:200
     ``` 
 3. modify /etc/pdm/conf/conf.json  [conf.json](conf.json) according to <b>chapter - 4.2</b> in [http://openpalette.zte.com.cn/docs/ver/v1.17.30.03.p10/installation_guide/paasInstall/n_merged_into_one_new.html#id10](http://openpalette.zte.com.cn/docs/ver/v1.17.30.03.p10/installation_guide/paasInstall/n_merged_into_one_new.html#id10)
 
   
