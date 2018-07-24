package org.seraph.mvprxjavaretrofit.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Activity作用域
 * date：2017/4/6 09:55
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityScoped {
}