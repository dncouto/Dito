package br.com.microserviceDito.Data;

import java.util.List;

public class ResponseWrapper {
    
    private String result;
    
    private Object msgSaida;
    
    private List<String> error;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getMsgSaida() {
        return msgSaida;
    }

    public void setMsgSaida(Object msgSaida) {
        this.msgSaida = msgSaida;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }
}
