1. change scenario type
    ```
    "region": {
        "scenariotype": "baremetal",
        "region_detail": ""
    },
    ```
2. change node pools
    1. define only one nodepool
    2. set min_num = 0 for 'n in 1'
    ```
    "min_num": 0,
    "max_num": 10,
    ```
3. clear data in 'roles' and insert data to 'roles_relation'
    1. roles and roles_relation are mutually exclusive
    2. elk set to 0 for light weight
    ```
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
    ```
4. config cluster
    1. keep cluster1 only
    2. add 'minion' to roles
    3. add 'paas_controller' to 'node_role'
    4. labels.dpdk = true, labels.privilege = true
    5. add node_config
    6. app_exclusive_count 
        1. default is 0
        2. how many cpu cores for 1 application
    ```
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
                        "np_name": "default_np",
                        "node_role": ["paas_controller"]
                    },
                    "node_config": {
                        "app_exclusive_count": 0,
                        "hugepage_2m_total_size": 0
                    },
                    "labels": {
                        "dpdk": "true",
                        "privilege": "true"
                    }
                }
            ]
        }
    ],
    ```
5. config network
    1. bm_network
        - admin_ip set to 88.88.1.*
        - api_vlan_id and mgt_vlan_id must in configured vlan section
        - use default for net_api and net_mgt
        - net_iapi use <b>floating ip</b>
        - provider:segmentation_id must in <b>vlan section</b>
        
    ```
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
                            "start": "10.86.110.253",
                            "end": "10.86.110.254"
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
    ```