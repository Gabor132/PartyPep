package com.gabor.partypeps.common;

import com.gabor.partypeps.enums.RequestPathEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class UrlHelper {

    public String mainURL;

    public String getURL(RequestPathEnum pathDesired) {
        return getURL(pathDesired, null);
    }

    public String getURL(RequestPathEnum pathDesired, HashMap<String, Object> variables) {
        StringBuilder builder = new StringBuilder();
        builder.append(mainURL).append(pathDesired.getUrl());
        String path = builder.toString();
        if (variables != null) {
            for (String key : variables.keySet()) {
                path = path.replace(String.format("{%s}", key), variables.get(key).toString());
            }
        }
        return path;
    }


}
