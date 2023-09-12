package alarm.access.processor;

import com.alibaba.fastjson.JSONObject;
import com.jst.common.enums.EnumBooleanState;
import com.jst.jstdao.model.TDatasource;
import com.jst.jstdao.model.TEvaluator;
import com.jst.jstdao.repository.TEvaluatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class EvaluatorFieldsFilter {
    Logger logger = LoggerFactory.getLogger(EvaluatorFieldsFilter.class);

    @Resource
    private TEvaluatorRepository evaluatorRepository;

    private static ConcurrentHashMap<String, Pattern> patternMap = new ConcurrentHashMap<String, Pattern>(32);

    public List<TEvaluator> doFilter(TDatasource datasource, JSONObject data) {
        if (StringUtils.isEmpty(datasource.getFieldKey())) {
            logger.warn("field key is empty");
            return Collections.EMPTY_LIST;
        } else if (StringUtils.isEmpty(datasource.getTimeAtt())) {
            logger.warn("time attribute is  empty");
            return Collections.EMPTY_LIST;
        } else if (!data.containsKey(datasource.getTimeAtt())) {
            logger.warn("Could not find FieldKey from raw data, data={}, FieldKey={}", data, datasource.getFieldKey());
            return Collections.EMPTY_LIST;
        }

        List<TEvaluator> evaluatorList = evaluatorRepository.findAllByGroupCodeAndDsCode(datasource.getGroupCode(), datasource.getCode());
        return evaluatorList.stream().filter(e -> checkFields(e, data, datasource.getFieldKey())).collect(Collectors.toList());

    }


    public boolean checkFields(TEvaluator e, JSONObject json, String strFieldKey) {
        try {
            if (EnumBooleanState.TRUE.getValue() != e.getEnable()) {
                return false;
            }
            if (StringUtils.isEmpty(e.getOwner())) {
                return false;
            }
            StringBuffer sb = new StringBuffer();
            sb.append(strFieldKey).append("=").append(e.getOwner());
            if (sb.length() > 0) {
                sb.append(" && ");
            }
            sb.append(e.getFields());

            String strFields = sb.toString();
            String[] arrs = strFields.split("&&");
            for (String pairs : arrs) {
                String[] pair = pairs.split("=");
                if (pair.length != 2) {
                    logger.warn("incorrect evaluator fields {}", strFields);
                    return false;
                }
                String key = pair[0].trim();
                String value = pair[1].trim();
                if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)
                        && json.containsKey(key)) {

                    String data = json.get(key).toString();
                    boolean matches = isMatches(value, data);
                    if (!matches) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        } catch (Exception e1) {
            logger.error("doFilter error ", e);
            return false;
        }
    }


    private boolean isMatches(String value, String data) {
        boolean matches;
        if (value.startsWith("[") && value.endsWith("]")) {
            //处理正则
            String regex = value.substring(1, value.length() - 1);
            Pattern pattern = getCachePattern(regex);
            matches = pattern.matcher(data).matches();
        } else if (value.contains("*")) {
            //
            matches = PatternMatchUtils.simpleMatch(value, data);
        } else {
            matches = value.equals(data);
        }
        return matches;
    }

    private Pattern getCachePattern(String regex) {
        if (patternMap.contains(regex)) {
            return patternMap.get(regex);
        } else {
            Pattern pattern = Pattern.compile(regex);
            patternMap.put(regex, pattern);
            return pattern;
        }

    }


}
