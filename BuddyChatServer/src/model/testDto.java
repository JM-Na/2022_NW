package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class testDto implements Serializable {
    private String hello;

    public testDto() {
        hello = "hello";
    }
}
