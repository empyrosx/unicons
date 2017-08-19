/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package consolidation;

import consolidation.SharedParams.SharedParam;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Димитрий
 */
public class SharedParamsImpl implements SharedParams {

    /**
     * Набор параметров ограничений
     */
    private Map<String,SharedParam> params;

    /**
     * Конструктор
     */
    public SharedParamsImpl() {
        params = new HashMap<String,SharedParam>();
    }

    @Override
    public Object getParamValue(String paramName) {
        SharedParam param = getParam(paramName);
        return param == null ? null : param.getValue();
    }

    @Override
    public void setParamValue(String paramName, Object paramValue) {
        SharedParam param = getParam(paramName);
        if (param != null) {
            param.setValue(paramValue);
        };
    }

    @Override
    public SharedParam getParam(String paramName) {
        return params.get(paramName);
    }

    @Override
    public int getParamCount() {
        return params.size();
    }

    @Override
    public SharedParam addParam(String paramName) {
        SharedParam param = new SharedParamImpl(paramName);
        params.put(paramName, param);
        return param;
    }
}