package kz.project.carrental.action.wrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestWrapper {

    private static final String ACTION = "action";

    private Map<String, String[]> requestParameters;
    private HashMap<String, Object> requestAttributes;
    private HashMap<String, Object> sessionAttributes;

    public RequestWrapper() {
    }

    public String getActionName() {
        String actionName = null;
        if (requestParameters.get(ACTION) != null) {
            actionName = requestParameters.get(ACTION)[0];
        }
        return actionName;
    }

    public RequestWrapper(HttpServletRequest request) {
        wrap(request);
    }

    public Map<String, String[]> getRequestParameters() {
        return requestParameters;
    }

    public void setRequestParameters(Map<String, String[]> requestParameters) {
        this.requestParameters = requestParameters;
    }

    public HashMap<String, Object> getRequestAttributes() {
        return requestAttributes;
    }

    public void setRequestAttributes(HashMap<String, Object> requestAttributes) {
        this.requestAttributes = requestAttributes;
    }

    public HashMap<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public void setSessionAttributes(HashMap<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    public void wrap(HttpServletRequest request) {
        requestAttributes = new HashMap<String, Object>();
        sessionAttributes = new HashMap<String, Object>();
        String elementName;
        Enumeration<String> attributeNames = request.getAttributeNames();
        Enumeration<String> sessionAttributeNames = request.getSession().getAttributeNames();

        requestParameters = request.getParameterMap();

        while (attributeNames.hasMoreElements()) {
            elementName = attributeNames.nextElement();
            requestAttributes.put(elementName, request.getAttribute(elementName));
        }
        while (sessionAttributeNames.hasMoreElements()) {
            elementName = sessionAttributeNames.nextElement();
            sessionAttributes.put(elementName, request.getSession().getAttribute(elementName));
        }
    }

    public void unwrap(HttpServletRequest request) {
        for (Map.Entry<String, Object> entry : requestAttributes.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : sessionAttributes.entrySet()) {
            request.getSession().setAttribute(entry.getKey(), entry.getValue());
        }
    }

    public String getSingleRequestParameter(String key) {
        String[] values = requestParameters.get(key);
        if (values != null && values.length > 0) {
            return values[0];
        } else {
            return null;
        }
    }


    public void clear() {
        requestAttributes = new HashMap<String, Object>();
        requestAttributes = new HashMap<String, Object>();
        sessionAttributes = new HashMap<String, Object>();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("RequestWrapper");
        sb.append("{requestParameters=").append(requestParameters);
        sb.append(", requestAttributes=").append(requestAttributes);
        sb.append(", sessionAttributes=").append(sessionAttributes);
        sb.append('}');
        return sb.toString();
    }
}
