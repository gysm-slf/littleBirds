## A little bird yearning for freedom...
## I hope this bird can fly very high!
## 可以记一些自己的笔记
获取全局配置文件中的变量值
获取单个
配置文件中
SERVER.VIDEO.PATH=http://127.0.0.1:6068/static/video
CONTROLLER文件中
@VALUE("${SERVER.VIDEO.PATH}")
private String videoPath;

多个，注意：此种方式需要为变量生成setget方法
SERVER.VIDEOPATH=http://127.0.0.1:6068/static/video
SERVER.MUSICPATH=http://127.0.0.1:6068/static/music
SERVER.PICTUREPATH=http://127.0.0.1:6068/static/picture
CONTROLLER文件中
类名上加注解@ConfigurationProperties(prefix="SERVER") 前缀加变量名等于配置文件中变量名即可
private String VIDEOPATH;
private String MUSICPATH;
private String PICTUREPATH;

Profile配置:
Profile是针对不同的环境对不同的配置提供支持的，全局Profile配置使用application-*.properties
(application-prod.properties,application-sit.properties,application-dev.properties)

通过在application.properties中设置spring.profiles.active=dev来指定活动的Profile.


SpringBoot的日志的级别有7个： 如INFO,则会打印INFO,WARN,ERROR,FATAL,OFF
TRACE , DEBUG , INFO , WARN , ERROR , FATAL , OFF


默认情况下，Spring Boot将日志输出到控制台，不会写到日志文件。如果要编写除控制台输出之外的日志文件，则需在application.properties中设置logging.file或logging.path属性。
logging.file，设置文件，可以是绝对路径，也可以是相对路径。如：logging.file=log/my.log(相对)或者/log/my.log(绝对)
logging.path，设置目录，会在该目录下创建spring.log文件，并写入日志内容，如：logging.path=/var/log
如果只配置 logging.file，会在项目的当前路径下生成一个 xxx.log 日志文件。
如果只配置 logging.path，在 /var/log文件夹生成一个日志文件为 spring.log
注：二者不能同时使用，如若同时使用，则只有logging.file生效

通过系统属性和传统的Spring Boot外部配置文件依然可以很好的支持日志控制和管理。
根据不同的日志系统，你可以按如下规则组织配置文件名，就能被正确加载：
•	Logback：logback-spring.xml, logback-spring.groovy, logback.xml, logback.groovy
•	Log4j：log4j-spring.properties, log4j-spring.xml, log4j.properties, log4j.xml
•	Log4j2：log4j2-spring.xml, log4j2.xml
•	JDK (Java Util Logging)：logging.properties


在SpringBoot中加载静态资源和在普通的web应用中不太一样。默认情况下，Spring Boot从classpath的/static,/public或/META-INF/resources文件夹或从ServletContext根目录提供静态内容,也可以↓
#设定静态文件路径，js,css,image等spring.resources.static-locations=classpath:/static/，且指定该路径后，boot默认的静态资源路径会失效
且不需要加该文件夹路径，如src/main/resources/static/hello.jpg,则对应的访问路径为localhost:8080/hello.jpg

在全局配置文件中：
          spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp

使用framearker，引入jar包，如果不是用默认配置可在全局配置文件中#springboot整合freemarker
spring.freemarker.allow-request-override=false
spring.freemarker.cache=true
spring.freemarker.check-template-location=true
spring.freemarker.charset=UTF-8
spring.freemarker.content-type=text/html
spring.freemarker.expose-request-attributes=false
spring.freemarker.expose-session-attributes=false
spring.freemarker.expose-spring-macro-helpers=false
spring.freemarker.suffix=.ftl
spring.freemarker.template-loader-path=classpath:/templates

@MapperScan("cn.core.*.dao")  注意 ： 一定要具体到mapper.xml文件所在的一层