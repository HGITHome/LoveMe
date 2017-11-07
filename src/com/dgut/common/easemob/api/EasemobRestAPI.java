package com.dgut.common.easemob.api;

import com.dgut.common.easemob.comm.ClientContext;


public abstract class EasemobRestAPI implements RestAPI{

    private ClientContext context;

    private RestAPIInvoker invoker;

    public RestAPIInvoker getInvoker() {
        return invoker;
    }

    public void setInvoker(RestAPIInvoker invoker) {
        this.invoker = invoker;
    }

    public ClientContext getContext() {
        return context;
    }

    public void setContext(ClientContext context) {
        this.context = context;
    }
}
