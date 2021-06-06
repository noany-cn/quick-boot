package quick.boot.handler.web;

import java.lang.reflect.Method;

import quick.boot.tools.GenerateRequestMappingInfo;

import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @title: Api映射处理器
 * @projectName quick-boot
 * @author 南迷
 * @date 2021/6/5 22:50
 * @description 处理所有使用了quick.boot.Api注解的类，解析Mapping
 */
public class ApiAutoHandlerRegisterHandlerMapping extends RequestMappingHandlerMapping {

	public ApiAutoHandlerRegisterHandlerMapping() {
		// 如果不提升等级那么会造成 org.springframework.web.servlet.DispatcherServlet 接收请求的时候错误的/**解析造成找不到请求路径正常的Handler
		super.setOrder(1);
	}


	/**
	 * 判断是否符合触发自定义注解的实现类方法
	 */
	@Override
	protected boolean isHandler(Class<?> beanType) {
		return GenerateRequestMappingInfo.isApi(beanType) || super.isHandler(beanType);
	}

	/**
	 * 扫到实现类的方法时，与这里返回的url进行板顶
	 */
	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {

		if (GenerateRequestMappingInfo.isApi(handlerType)) return  GenerateRequestMappingInfo.generateRequestMappingInfo(method,handlerType);

		return super.getMappingForMethod(method, handlerType);
	}
}
