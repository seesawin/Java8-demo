package validation;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.NotNull;

@ScriptAssert(script = "_this.name==_this.fullName", lang = "javascript")
@Data
public class User {

    @NotNull
    private String name;

    @Length(min = 20)
    @NotNull
    private String fullName;
}
