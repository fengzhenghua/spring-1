package com.tydic.unicom.vdsBase.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tydic.uda.core.Sort;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface like {
	Like type() default Like.RIGHT;
	String paramName() default "";
	public enum Like{
		RIGHT,LEFT,ALL
	}
}
