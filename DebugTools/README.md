#### 代码获取
可以多种方式，一个是找承载相关人员要某个功能的代码库，然后申请权限，但是一般承载的一个ie功能涉及多个库，很分散，开发人员可能给不全
所以可以从安装好的网管里反编译

0. 登录 https://10.86.110.251/portal/  umebn 111111
0. Software Repository ->Image，搜索涉及的镜像，点进去后Export出镜像到本地
0. 本地导入镜像
` docker load --input imagexxx.tar `
0. 找到镜像id
` docker images `
0. 启动容器
` docker run imageid `
0. 找到容器的id
` docker ps -a `
0. 进入容器的sh
` docker exex -it containerid /bin/sh `
0. 找出需要拷贝出的文件的路径
0. 把文件拷贝到本地 ` docker cp containerid：filepath  localpath `

#### 代码工程导入
导入代码时，maven的settings文件，用这里附带的

#### 如何调试容器内的代码
[how to debug docker](HowToDebugDocker.md)
