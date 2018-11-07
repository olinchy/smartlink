-   问题描述
    在部署PaaS时出现组件创建失败的错误。
-   解决方案    
    1.  在部署PaaS的时候，修改完配置文件，如果出现了  
        ```
        EEROR - getting no node info will cause deploying error, please check posd and the uniview
        ERROR - deploy_baremetal_node_os error
        ERROR - Execute initial component: posd rear hook failed!
        ```
        建议，查询是否存在OCSA_VM的配置文件，**文件名是否错误**（这点很致命，因为这个文件是手动创建的，如果文件名错误会导致这个错误，而且很难查出）。