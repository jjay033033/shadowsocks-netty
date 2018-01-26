基于netty4.0实现的shadowsocks客户端（加入自动更新账号密码功能）
====

使用
---
1.maven打包，生成shadowsocks-netty-0.1.0-alpha-bin.zip包<br>
2.解压zip包，conf/config.xml中配置捕获shadowsocks服务器账号密码的网页地址及相关匹配信息<br>
3.执行shell文件夹中的bat/sh文件启动代理服务器<br>

chrome代理设置
---
需要安装Proxy SwitchySharp插件（或者Proxy SwitchyOmega：https://github.com/FelisCatus/SwitchyOmega/releases，在线规则：https://raw.githubusercontent.com/gfwlist/gfwlist/master/gfwlist.txt），然后设置socks代理，指定本地代理服务器就行了


firefox代理设置
---
firefox自带设置socks代理功能，直接设置就行了

原作者更多详细介绍
---
[基于netty4.0的shadowsocks客户端](http://my.oschina.net/OutOfMemory/blog/744475)