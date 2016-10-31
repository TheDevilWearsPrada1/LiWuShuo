package com.qf.liwushuo.service;




import com.qf.liwushuo.utils.UrlUtil;


import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by Administrator on 2016/10/14 0014.
 */

public class RetrofitInstance {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(UrlUtil.URL_HOME)//
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    /**
     * 返回具体类的请求方法
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getService(Class<T> tClass){
        return retrofit.create(tClass);
    }


   /* private static OkHttpClient getCacheOkHttpClient() {
        //设置缓存路径
        final File httpCacheDirectory = new File(RxApplication.getInstance().getCacheDir(), "okhttpCache");

        Log.i(TAG, httpCacheDirectory.getAbsolutePath());
        //设置缓存 10M
        Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);   //缓存可用大小为10M

        return new OkHttpClient.Builder()
                .writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(15 * 1000, TimeUnit.MILLISECONDS)
                //设置拦截器，显示日志信息
                .addInterceptor(httpLoggingInterceptor)
                //.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                //.authenticator(new TokenAuthenticator())
                .build();
    }

    private final static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);



    private final static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //获取网络状态
            int netWorkState = NetUtils.getNetworkState(RxApplication.getInstance());
            //无网络，请求强制使用缓存
            if (netWorkState == NetUtils.NETWORK_NONE) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            switch (netWorkState) {
                case NetUtils.NETWORK_MOBILE://mobile network 情况下缓存一分钟
                    int maxAge = 60;
                    return originalResponse.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();

                case NetUtils.NETWORK_WIFI://wifi network 情况下不使用缓存
                    maxAge = 0;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();

                case NetUtils.NETWORK_NONE://none network 情况下离线缓存4周
                    int maxStale = 60 * 60 * 24 * 4 * 7;
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                default:
                    throw new IllegalStateException("network state  is Erro!");
            }
        }
    };
*/
}
