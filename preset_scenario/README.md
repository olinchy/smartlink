#### preparation
- create 2 network interface

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
- upload config files
```
/etc/pdm/conf/nodes -> /root/nodes
/etc/pdm/conf/conf.json
/etc/pdm/conf/paas.conf
```

- deploy offline
```
pdm-cli deploy --offline
https://wiki.zte.com.cn/pages/viewpage.action?pageId=73242279
```