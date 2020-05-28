package Server.Collection;

import java.io.Serializable;

public class CityObjects implements Serializable {
    private String message;
    private City object;


    public void setMessage(String message) {
        this.message = message;
    }

    public void setObject(City object) {
        this.object = object;
    }

    public City getObject() {
        return object;
    }

    public String getMessage() {
        return message;
    }

}
