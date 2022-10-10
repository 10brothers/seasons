
参照SpringBoot使用内嵌Tomcat的方式，在SpringFramework环境下使用内嵌Tomcat

使用内嵌的Tomcat容器，基于注解的配置类



filter 

handle执行前后的扩展点 

自定义参数解析  HandlerMethodArgumentResolver 

返回值解析   HandlerMethodReturnValueHandler 不同类型的返回类型（content-type）,使用不同的Handler，同时根据具体的值类型（Map POJO String等）来进行消息内容转换 

都涉及到converter，


handler方法最终会通过反射获取Method对象，然后包装成 InvocableHandlerMethod  这一步哪里处理？


DataBinder

WebDataBinder 将Web请求参数绑定到JavaBean对象上 