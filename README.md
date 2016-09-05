1 设置pom
<dependency>
            <groupId>com.yizhenmoney.damocles</groupId>
            <artifactId>damocles-configcenter-client</artifactId>
            <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
                <groupId>com.google.guava</groupId>
                <artifactId>guava</artifactId>
                <version>11.0.1</version>
</dependency>
 
 
2 修改applicationContext.xml
<bean
        class="com.yizhenmoney.damocles.configcenter.config.ZooKeeperPropertyPlaceholderConfigurer">
        <property name="systemPropertiesModeName" value="SYSTEM_PROPERTIES_MODE_OVERRIDE" />
        <property name="ignoreResourceNotFound" value="true" />
</bean>
 
3 从管理页面http://192.168.220.141:8090/configcenter/login ，使用ldap登录
 
新增环境导入配置，并获取token加入jvm启动项内  例如：
 
-Dacl=MTkyLjE2OC4yMjAuMTk0OjIxODE:DyzZz_92PZUzhR4qB2hStQ
 
ps: 如果开发使用嵌入式jetty，jetty的配置文件请使用spring自带的PropertyPlaceholderConfigurer引入jetty相关配置
