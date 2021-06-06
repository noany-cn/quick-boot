package quick.boot.handler.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.annotation.RequestParamMapMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolverComposite;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.HttpEntityMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestPartMethodArgumentResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

/**
* @title: ApiRequestArgsWrapBean
* @projectName quick-boot
* @author 南迷
* @date 2021/6/6 19:22
*/public class ApiRequestArgsWrapBean  implements InitializingBean {

	private final RequestMappingHandlerAdapter adapter;

	ApiRequestArgsWrapBean(RequestMappingHandlerAdapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public void afterPropertiesSet() {
		List<HandlerMethodArgumentResolver> argumentResolvers = adapter.getArgumentResolvers();
		if (argumentResolvers == null || argumentResolvers.isEmpty()) argumentResolvers = new ArrayList<>();
		List<HandlerMethodArgumentResolver> handlers = new ArrayList<>(argumentResolvers);
		decorateHandlers(handlers);
		adapter.setArgumentResolvers(handlers);
	}

	private void decorateHandlers(List<HandlerMethodArgumentResolver> handlers) {
		HandlerMethodArgumentResolver requestResponseBodyMethodProcessor = handlers.stream()
				.filter(handler -> handler instanceof RequestResponseBodyMethodProcessor).findAny().get();

		HandlerMethodArgumentResolver servletModelAttributeMethodProcessor = handlers.stream()
				.filter(handler -> handler instanceof ServletModelAttributeMethodProcessor).findAny().get();


		ApiRequestArgsWrapHandler decorator = new ApiRequestArgsWrapHandler(requestResponseBodyMethodProcessor,servletModelAttributeMethodProcessor);
		handlers.set(handlers.indexOf(requestResponseBodyMethodProcessor), decorator);
		handlers.set(handlers.indexOf(servletModelAttributeMethodProcessor), decorator);
	}
}
