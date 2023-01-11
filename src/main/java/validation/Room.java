package validation;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Data
public class Room {

    @NotNull
    public String name;

    @AssertTrue
    public boolean finished;

}
