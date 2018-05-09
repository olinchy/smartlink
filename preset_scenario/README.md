#### Using CPaaS with OICT component package 

1. [wiki path](https://wiki.zte.com.cn/pages/viewpage.action?pageId=367729056)
2. [CPaaS package ver. 1.17.30.03.p10](https://artxa.zte.com.cn:443/artifactory/oes_tcp-release-generic/embpaas/both/v1.17.30.03.p10_1595805_1/version)
3. [CGSL iso](http://openpalette.zte.com.cn/docs/ver/v1.17.30.03.p10/installation_guide/images_release_notes.html)
4. [OTCP package ver.1.18.10.06.p01 ](https://artxa.zte.com.cn/artifactory/oes_tcp-release-generic/VERSION/v1.18.10.06.p01)

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