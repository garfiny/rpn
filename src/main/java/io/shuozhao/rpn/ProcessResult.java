package io.shuozhao.rpn;

public class ProcessResult {

    private String error;
    private String result;

    public boolean hasError() {
        return this.error != null && !this.error.isBlank();
    }

    public String getError() {
        return error;
    }

    public String getResult() {
        return result;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
