package quick.boot.handler;

import java.util.Map;

import quick.boot.EnableApiImportBeanDefinitionRegistrar;
import quick.boot.handler.web.ApiAutoHandlerRegisterHandlerMapping;
import quick.boot.handler.web.ApiRequestArgsWrapBean;
import quick.boot.handler.web.ApiResponseBodyWrapBean;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author 南迷
 * @title: GlobalImportBeanDefinitionRegistrar
 * @projectName quick-develop-test
 * @date 2021-06-03 15:29
 * @description 将EnableApiImportBeanDefinitionRegistrar.class basePackages指定路径下面quick-boot自定义的注解的类交给spring管理
 */
public class ApiImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    ResourceLoader resourceLoader;
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //扫描注解
        Map<String, Object> annotationAttributes = importingClassMetadata
                .getAnnotationAttributes(EnableApiImportBeanDefinitionRegistrar.class.getName());
        assert annotationAttributes != null;
        String[] basePackages = (String[]) annotationAttributes.get("basePackages");
        if (basePackages.length > 0) {
            //扫描包下所有的实例
            ApiClassPathBeanDefinitionScanner scanner = new ApiClassPathBeanDefinitionScanner(registry, false);
            scanner.setResourceLoader(resourceLoader);
            scanner.registerTypeFilter();
            scanner.doScan(basePackages);
        }


        //生成HTTP自定义拦截bean
        BeanDefinitionBuilder mappingBuilder = BeanDefinitionBuilder.genericBeanDefinition(ApiAutoHandlerRegisterHandlerMapping.class);
        registry.registerBeanDefinition("apiAutoHandlerRegisterHandlerMapping", mappingBuilder.getBeanDefinition());


        //生成@RequestArgs处理
        BeanDefinitionBuilder requestArgsWrapBean = BeanDefinitionBuilder.genericBeanDefinition(ApiRequestArgsWrapBean.class);
        registry.registerBeanDefinition("apiRequestArgsWrapBean", requestArgsWrapBean.getBeanDefinition());

        //生成@ResponseBody处理
        BeanDefinitionBuilder responseBodyWrapBean = BeanDefinitionBuilder.genericBeanDefinition(ApiResponseBodyWrapBean.class);
        registry.registerBeanDefinition("apiResponseBodyWrapBean", responseBodyWrapBean.getBeanDefinition());
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;

    }
}
