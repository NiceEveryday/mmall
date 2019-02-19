package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public  class  TokenCache {
    public static Logger logger = LoggerFactory.getLogger(TokenCache.class);
    public static final String TOKEN_PREFIX = "token_";
    /**
     *
     */
    public static LoadingCache<String,String> loadingCache = CacheBuilder.newBuilder().initialCapacity(1000).
            maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String, String>() {
        @Override
        public String load(String s) throws Exception {
            return "null";
        }
    });

    public static  String getToken(String key) {
        String token = null;
        try {
            token = loadingCache.get(key);
            if(token.equals("null")){
                return null;
            }
            return token;
        } catch (ExecutionException e) {
            logger.error("loadingCache get error",e);
        }
        return null;
    }

    public static void setToken(String key,String value) {
            loadingCache.put(key,value);
    }
}
