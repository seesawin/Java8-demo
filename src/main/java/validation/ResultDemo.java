package validation;

import lombok.Data;

import javax.validation.Valid;

@Data
public class ResultDemo {
    public Result<@Valid Room> roomResult;
}
