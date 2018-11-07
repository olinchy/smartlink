-   部署PaaS平台时，使用  
    ```pdm-cli deploy --offline```  
    后显示的控制台的Log,可以从这个Log中看到所部署的组件  
    版本号：paas-offline-v1.18.20.11_2449090_12  
    ```
    2018-11-07 10:24:00,809 - INFO - Check user configuration and deployment environment.
    2018-11-07 10:24:46,072 - INFO - Begin to upgrade opration system,it will last about 20 minutes
     [WARNING]: Consider using yum module rather than running yum
    2018-11-07 10:25:12,568 - INFO - upgrade opration system in controller nodes successfully.
    2018-11-07 10:25:55,838 - INFO - distribute_bare_controller_vip successfully
    2018-11-07 10:25:56,854 - INFO - Set ntp server.
    2018-11-07 10:26:00,360 - INFO - Set dns server.
    2018-11-07 10:26:03,820 - INFO - Deploy initial component: docker.
    2018-11-07 10:26:44,859 - INFO - Deploy initial component: docker successful!
    2018-11-07 10:26:44,859 - INFO - Deploy initial component: pacemaker_cluster.
    2018-11-07 10:27:31,335 - INFO - Deploy initial component: pacemaker_cluster successful!
    2018-11-07 10:27:32,337 - INFO - Deploy initial component: swarm_cluster.
    2018-11-07 10:27:39,911 - INFO - Deploy initial component: swarm_cluster successful!
    2018-11-07 10:27:40,965 - INFO - Deploy initial component: ubu_mysql.
    2018-11-07 10:28:50,925 - INFO - Deploy initial component: ubu_mysql successful!
    2018-11-07 10:28:51,934 - INFO - Deploy initial component: postgresql.
    2018-11-07 10:30:22,406 - INFO - Deploy initial component: postgresql successful!
    2018-11-07 10:30:23,481 - INFO - Deploy initial component: ubu_rabbit.
    2018-11-07 10:31:10,960 - INFO - Deploy initial component: ubu_rabbit successful!
    2018-11-07 10:31:12,006 - INFO - Deploy initial component: cf-nbm.
    2018-11-07 10:31:25,677 - INFO - Deploy initial component: cf-nbm successful!
    2018-11-07 10:31:26,738 - INFO - Deploy initial component: vnm.
    2018-11-07 10:35:51,684 - INFO - start to create net_iapi
    2018-11-07 10:35:51,776 - INFO - create net_iapi success
    2018-11-07 10:35:52,401 - INFO - create_net_in_baremetal_notoken complete
    2018-11-07 10:35:53,508 - INFO - Deploy initial component: vnm successful!
    2018-11-07 10:35:54,542 - INFO - Deploy initial component: inetagent.
    2018-11-07 10:36:08,426 - INFO - Deploy initial component: inetagent successful!
    2018-11-07 10:36:09,451 - INFO - Deploy initial component: inetmanager.
    2018-11-07 10:36:34,627 - INFO - Deploy initial component: inetmanager successful!
    2018-11-07 10:36:35,621 - INFO - Deploy initial component: zenap_msb_consul_server.
    2018-11-07 10:36:51,853 - INFO - Deploy initial component: zenap_msb_consul_server successful!
    2018-11-07 10:36:52,971 - INFO - Deploy initial component: zenap_msb_sdclient.
    2018-11-07 10:38:08,027 - INFO - Deploy initial component: zenap_msb_sdclient successful!
    2018-11-07 10:38:09,074 - INFO - Deploy initial component: zenap_msb_apigateway.
    2018-11-07 10:39:08,400 - INFO - Deploy initial component: zenap_msb_apigateway successful!
    2018-11-07 10:39:09,440 - INFO - Deploy initial component: zenap_msb_router.
    2018-11-07 10:40:09,287 - INFO - Deploy initial component: zenap_msb_router successful!
    2018-11-07 10:40:10,339 - INFO - Deploy initial component: cf-base.
    2018-11-07 10:40:37,371 - INFO - Deploy initial component: cf-base successful!
    2018-11-07 10:40:38,393 - INFO - Deploy initial component: cf-api.
    2018-11-07 10:40:53,835 - INFO - Deploy initial component: cf-api successful!
    2018-11-07 10:40:54,856 - INFO - Deploy initial component: cf-pdeploy.
    2018-11-07 10:41:12,273 - INFO - Deploy initial component: cf-pdeploy successful!
    2018-11-07 10:41:13,282 - INFO - Deploy initial component: cf-pdman.
    2018-11-07 10:41:32,022 - INFO - Deploy initial component: cf-pdman successful!
    2018-11-07 10:41:33,048 - INFO - Deploy initial component: cf-broker.
    2018-11-07 10:41:49,113 - INFO - Deploy initial component: cf-broker successful!
    2018-11-07 10:41:50,156 - INFO - Deploy initial component: blockstorage.
    2018-11-07 10:42:45,428 - INFO - Deploy initial component: blockstorage successful!
    2018-11-07 10:42:46,445 - INFO - Deploy initial component: cf-pnode.
    2018-11-07 10:43:05,403 - INFO - Deploy initial component: cf-pnode successful!
    2018-11-07 10:43:06,491 - INFO - Deploy initial component: posd.
    2018-11-07 10:43:55,386 - INFO - begin deploy node OS,wait about 30 minutes!
    2018-11-07 10:46:55,571 - INFO - Deploy initial component: posd successful!
    2018-11-07 10:46:56,626 - INFO - Deploy initial component: cf-pcluster.
    2018-11-07 10:47:13,854 - INFO - Deploy initial component: cf-pcluster successful!
    2018-11-07 10:47:14,874 - INFO - Deploy initial component: cf-vnpm-api.
    2018-11-07 10:47:34,935 - INFO - Deploy initial component: cf-vnpm-api successful!
    2018-11-07 10:47:36,145 - INFO - Deploy initial component: cf-vnpm.
    2018-11-07 10:47:54,004 - INFO - Deploy initial component: cf-vnpm successful!
    2018-11-07 10:47:55,056 - INFO - Deploy initial component: cf-vnpm_poll.
    2018-11-07 10:48:14,247 - INFO - Deploy initial component: cf-vnpm_poll successful!
    2018-11-07 10:48:15,326 - INFO - Deploy initial component: app-gateway.
    2018-11-07 10:48:37,789 - INFO - Deploy initial component: app-gateway successful!
    2018-11-07 10:48:38,834 - INFO - Deploy initial component: cf-csm-api.
    2018-11-07 10:48:56,569 - INFO - Deploy initial component: cf-csm-api successful!
    2018-11-07 10:48:57,646 - INFO - Deploy initial component: cf-csm.
    2018-11-07 10:49:15,709 - INFO - Deploy initial component: cf-csm successful!
    2018-11-07 10:49:16,877 - INFO - Deploy initial component: cf-zartcli.
    2018-11-07 10:49:24,711 - INFO - Deploy initial component: cf-zartcli successful!
    2018-11-07 10:49:25,784 - INFO - Deploy initial component: zart.
    2018-11-07 10:49:44,387 - INFO - Deploy initial component: zart successful!
    2018-11-07 10:49:44,387 - INFO - Deploy initial component: registry.
    2018-11-07 10:49:58,206 - INFO - Deploy initial component: registry successful!
    2018-11-07 10:50:08,679 - INFO - Deploy progress...
    2018-11-07 10:50:23,947 - INFO - Step 3: Create Nodepool, total 20 steps
    2018-11-07 10:50:38,980 - INFO - Step 5: Create Swr, total 20 steps
    2018-11-07 10:51:39,101 - INFO - Step 11: Download Packages, total 20 steps
    2018-11-07 10:51:54,121 - INFO - Step 16: Deploy Base, total 20 steps
    2018-11-07 11:04:10,676 - INFO - Step 20: Check All Deploy, total 20 steps
    2018-11-07 11:20:27,586 - INFO - Complete steps percent: 100%!
    2018-11-07 11:20:32,113 - INFO - Check all tasks ok!
    2018-11-07 11:20:32,127 - INFO - Deploy PaaS successfully, and create cluster complete! 
    2018-11-07 11:20:36,741 - INFO - msb sys floatip: 10.86.110.251, sys_ip_v6: 4ffe:ffff:0:f101::10e, app floatip: 10.86.110.251 change successfully
    2018-11-07 11:20:36,743 - INFO - Please ssh paas_controller with floating ip 10.86.110.251 now 
    ```