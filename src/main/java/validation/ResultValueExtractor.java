package validation;

import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.ValueExtractor;

/**
 * 在此处添加备注信息
 *
 * @author yourbatman
 * @site https://www.yourbatman.cn
 * @date 2020/10/25 10:01
 * @see Result
 */
public class ResultValueExtractor implements ValueExtractor<Result<@ExtractedValue ?>> {

    @Override
    public void extractValues(Result<?> originalValue, ValueExtractor.ValueReceiver receiver) {
        receiver.value(null, originalValue.getData());
    }

}