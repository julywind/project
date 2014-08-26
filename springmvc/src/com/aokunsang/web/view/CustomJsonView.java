package com.aokunsang.web.view;

import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.util.Map;

public class CustomJsonView extends MappingJacksonJsonView {
    protected Object filterModel(Map<String, Object> model) {
        Map<?, ?> result = (Map<?, ?>) super.filterModel(model);
        if (result.size() == 1) {
            return result.values().iterator().next();
        } else {
            return result;
        }
    }
}