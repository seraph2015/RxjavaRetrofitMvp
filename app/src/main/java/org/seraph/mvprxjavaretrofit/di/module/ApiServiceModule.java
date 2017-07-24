package org.seraph.mvprxjavaretrofit.di.module;

import org.seraph.mvprxjavaretrofit.data.network.ApiBuild;
import org.seraph.mvprxjavaretrofit.data.network.service.Api12306Service;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiBaiduService;
import org.seraph.mvprxjavaretrofit.data.network.service.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 网络请求module
 * date：2017/5/5 15:18
 * author：xiongj
 * mail：417753393@qq.com
 **/
@Module
public class ApiServiceModule {

    @Provides
    @Singleton
    ApiService apiService(ApiBuild apiBuild) {
        return apiBuild.buildApiInterface(ApiService.BASE_URL, ApiService.class);
    }

    @Provides
    @Singleton
    ApiBaiduService apiBaiduService(ApiBuild apiBuild) {
        return apiBuild.buildApiInterface(ApiBaiduService.BASE_URL_BAIDU, ApiBaiduService.class);
    }

    @Provides
    @Singleton
    Api12306Service api12306Service(ApiBuild apiBuild) {
        return apiBuild.buildApiInterface(Api12306Service.BASE_URL_12306, Api12306Service.class);
    }

}
