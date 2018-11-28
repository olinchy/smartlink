#### Function List
1. NE

   Display:
   1. Icon
        承载未自定义，使用默认图标
        平台功能接口wiki：[https://wiki.zte.com.cn/pages/viewpage.action?pageId=220375016](https://wiki.zte.com.cn/pages/viewpage.action?pageId=220375016)
   1. Status & Icon
   1. tooltips : 承载未实现，微波要单独实现
   1. menus

   Function:
   1. Auto Discover
1. Link

   Display:
   1. Style
   1. Status & Icon
   1. tooltips ：承载未实现，微波要单独实现
   1. menus

   Function:
   1. CRUD
   1. Auto Discover
1. Others
   1. 图标库的制作：https://wiki.zte.com.cn/pages/viewpage.action?pageId=232234486

      规范：https://wiki.zte.com.cn/pages/viewpage.action?pageId=370216338
      https://wiki.zte.com.cn/pages/viewpage.action?pageId=311167338


#### Develop Tips

平台功能接口wiki：[https://wiki.zte.com.cn/pages/viewpage.action?pageId=184299871](https://wiki.zte.com.cn/pages/viewpage.action?pageId=184299871)

承载的图标和右键菜单在meMgMt_init里，主要配置文件为
umebn\support\nfmgmtui\meMgmt-init\src\main\assembly\resource\conf\function.json
umebn\support\nfmgmtui\meMgmt-init\src\main\assembly\resource\conf\mapping.json


"iconLibrary": [
    {
      "fontName": "umebnIcons",
      "libraryName": "承载图标库",
      "src": "../../../iui/umebn-spt-melcm-ui/assets/umebnIcons/style.css"
    }
  ],

这里的../../../iui/umebn-spt-melcm-ui是<b>前端标准微服务命名</b>