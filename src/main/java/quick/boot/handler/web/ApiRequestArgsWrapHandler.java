package quick.boot.handler.web;

import quick.boot.tools.GenerateRequestMappingInfo;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @title: ApiRequestArgsWrapHandler
 * @projectName quick-boot
 * @author 南迷
 * @date 2021/6/6 19:24
 */
public class ApiRequestArgsWrapHandler implements HandlerMethodArgumentResolver {

	private final HandlerMethodArgumentResolver requestResponseBodyMethodProcessor;
	private final HandlerMethodArgumentResolver servletModelAttributeMethodProcessor;

	public ApiRequestArgsWrapHandler(HandlerMethodArgumentResolver requestResponseBodyMethodProcessor,HandlerMethodArgumentResolver servletModelAttributeMethodProcessor) {
		this.requestResponseBodyMethodProcessor = requestResponseBodyMethodProcessor;
		this.servletModelAttributeMethodProcessor = servletModelAttributeMethodProcessor;
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return GenerateRequestMappingInfo.isApi(methodParameter.getDeclaringClass());
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		Object o = requestResponseBodyMethodProcessor
				.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
		if (o != null) return o;
		return servletModelAttributeMethodProcessor
				.resolveArgument(methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory);
	}
}
