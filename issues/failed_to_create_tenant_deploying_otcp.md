-   问题描述
    部署集成UME的OTCP出现问题，无法开始部署，创建租户失败
-   解决方案    
    1.  部署集成UME的OTCP时出现
        ```
        =========== begin create tenant  ===========
        Traceback (most recent call last):
          File "/home/ubuntu/paasdata/oki-tools/tools/basetools/utils/paas_ip_tool.py", line 36, in <module>
            accesstoken = getAcceccToken(url, username, password)
          File "/home/ubuntu/paasdata/oki-tools/tools/basetools/utils/tenant_tool.py", line 32, in getAcceccToken
            raise Exception('getAcceccToken error %s' % str(e.message))
        Exception: getAcceccToken error {"code":"USER_OR_PWD_ERR","message":"username or password error"}
        ```
        创建租户失败，是OTCP的版本和PaaS的版本不匹配，建议换一个版本的OTCP。  