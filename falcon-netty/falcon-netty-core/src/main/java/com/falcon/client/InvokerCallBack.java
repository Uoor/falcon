package com.falcon.client;

import com.falcon.server.servlet.FalconRequest;
import com.falcon.server.servlet.FalconResponse;
import org.apache.log4j.Logger;

/**
 * Created by fanshuai on 15-2-11.
 */
public class InvokerCallBack {
    private final static Logger log = Logger.getLogger(InvokerCallBack.class);
    private boolean hasComplace = false;
    private Object returnObject;
    private boolean hasError = false;
    private Throwable throwable;
    private FalconRequest request;
    private long startTime = System.currentTimeMillis();
    public void processResponse(FalconResponse response){
        synchronized (this){
            returnObject = response.getRetObject();
            if(response.getThrowable()!=null){
                this.throwable = response.getThrowable();
                hasError = true;
            }
            if(response.getErrorMsg()!=null){
                this.throwable = new Exception(response.getErrorMsg());
                hasError = true;
            }
            hasComplace = true;
            notifyAll();
        }
    }

    public Object get(long timeOut) throws Exception {
        synchronized (this){
            long lastTime = timeOut;
            while (!hasComplace){
                try {
                    if(lastTime<=0){
                        log.error(request.getRequestInfo() +" time out ("+(timeOut)+")" +" use time ("+(System.currentTimeMillis()-startTime)+")");
                        throw new Exception("sequence["+request.getSequence()+"]time out");
                    }
                    wait(timeOut);
                    lastTime -= timeOut;
                } catch (InterruptedException e) {
                    log.error(request.getRequestInfo() +" thread wait exception : ",e);
                }
            }
            if (hasError){
                log.error(request.getRequestInfo() +" has error ",throwable);
                throw new Exception(throwable);
            }
        }
        log.error(request.getRequestInfo() +" success with time ("+(System.currentTimeMillis()-startTime)+")");
        return returnObject;

    }

    public void processFailedResponse(Throwable cause) {
        synchronized (this) {
            this.throwable = cause;
            hasComplace = true;
            hasError = true;
            notifyAll();
        }
    }

    public FalconRequest getRequest() {
        return request;
    }

    public void setRequest(FalconRequest request) {
        this.request = request;
    }
}
