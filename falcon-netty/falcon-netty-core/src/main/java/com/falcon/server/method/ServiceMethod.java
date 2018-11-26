package com.falcon.server.method;

import com.falcon.util.analysis.ServiceManager;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by fanshuai on 15-2-7.
 */
@Data
public class ServiceMethod {
    private Method method;
    private Object service;
    private Class[] paramClasses;
    private String paramNamesStr;
    public ServiceMethod(Object service,Method method){
        this.service = service;
        this.method = method;
        this.paramClasses = this.method.getParameterTypes();
        this.paramNamesStr = ServiceManager.getMethodParamTypeString(paramClasses);
    }

    public Object invoke(Object[] params) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(service,params);
    }



//    public static  Class<?>[] regulateTypes(Class<?>[] types) {
//        for(int i = 0; i < types.length; ++i) {
//            if(types[i] == Byte.TYPE) {
//                types[i] = Byte.class;
//            } else if(types[i] == Short.TYPE) {
//                types[i] = Short.class;
//            } else if(types[i] == Integer.TYPE) {
//                types[i] = Integer.class;
//            } else if(types[i] == Boolean.TYPE) {
//                types[i] = Boolean.class;
//            } else if(types[i] == Long.TYPE) {
//                types[i] = Long.class;
//            } else if(types[i] == Float.TYPE) {
//                types[i] = Float.class;
//            } else if(types[i] == Double.TYPE) {
//                types[i] = Double.class;
//            }
//        }
//        return types;
//    }
}
