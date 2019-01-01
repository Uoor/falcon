package com.falcon.util.analysis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析的服务结构
 * Created by fanshuai on 18/10/27.
 */
public class ServiceStructureInfo implements Serializable{
    /**
     * 服务id
     */
    private String serviceId;
    /**
     * 服务类的完整路径
     */
    private String serviceTypeName;
    /**
     * 服务描述，用于生成文档
     */
    private String serviceDesc;
    /**
     * 服务的具体实现
     */
    private transient Object serviceInstance;
    /**
     * 当前服务提供的所有方法列表
     */
    private List<ServiceMethodStructureInfo> serviceMethodStructureInfos;
    private Map<String,ServiceMethodStructureInfo> serviceMethodStructureInfoMap = new HashMap<String, ServiceMethodStructureInfo>();

    public String getServiceId() {
        if (serviceId==null){
            serviceId = serviceTypeName;
        }
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public Object getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(Object serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public List<ServiceMethodStructureInfo> getServiceMethodStructureInfos() {
        return serviceMethodStructureInfos;
    }

    public void setServiceMethodStructureInfos(List<ServiceMethodStructureInfo> serviceMethodStructureInfos) {
        this.serviceMethodStructureInfos = serviceMethodStructureInfos;
        Map<String,ServiceMethodStructureInfo> methodMap = new HashMap<String, ServiceMethodStructureInfo>();
        if (serviceMethodStructureInfos==null){
            serviceMethodStructureInfoMap = methodMap;
            return;
        }
        for (ServiceMethodStructureInfo serviceMethodStructureInfo : serviceMethodStructureInfos){
            methodMap.put(serviceMethodStructureInfo.getMethodId(),serviceMethodStructureInfo);
        }
        serviceMethodStructureInfoMap = methodMap;
    }

    @Deprecated
    public ServiceMethodStructureInfo getServiceMethodStructureInfo(String methodName,String paramTypes) {
        return serviceMethodStructureInfoMap.get(methodName+paramTypes);
    }

    public ServiceMethodStructureInfo getServiceMethodStructureInfo(String methodInfo) {
        return serviceMethodStructureInfoMap.get(methodInfo);
    }


}
