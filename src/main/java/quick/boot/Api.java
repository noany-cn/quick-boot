package quick.boot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title: Api
 * @projectName quick-boot
 * @author 南迷
 * @date 2021/6/5 22:37
 * @description
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Api {
	/**
	 * 映射路径
	 * @description 自定义类的映射路径名字，如果不定义那么会用类名 开头字母小写做为Controller访问路径
	 * @return 映射路径
	 */
	String mapping() default "";

	/**
	 * 映射路径前缀
	 * @description  自定义前缀路径 最终会用  prefix + mapping 组合成Controller访问路径
	 * @return 映射路径前缀
	 */
	String prefix() default "";
}
