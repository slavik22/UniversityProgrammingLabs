package JMS;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class DataObject implements Serializable {
    private int command;
    private Object data;
}