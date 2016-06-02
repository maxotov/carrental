package kz.project.carrental.logic.wrapper;

public class LogicResult<T> {

    private T result;
    private String errorMessage;

    /**
     * Returns a result of the logic.
     *
     * @return result, may be null
     */
    public T getResult() {
        return result;
    }

    /**
     * Sets the result of the logic.
     *
     * @param result of the logic.
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * Returns an error message.
     *
     * @return error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets an error message.
     *
     * @param errorMessage error message.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Checks for error in the result.
     *
     * @return true - if no error in result, or else false.
     */
    public boolean noError() {
        return (errorMessage == null ? true : false);
    }
}
