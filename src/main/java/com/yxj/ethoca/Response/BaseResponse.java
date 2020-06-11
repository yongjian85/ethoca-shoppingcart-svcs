package com.yxj.ethoca.Response;

import java.util.List;

public class BaseResponse {

    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "errors=" + errors +
                '}';
    }
}
