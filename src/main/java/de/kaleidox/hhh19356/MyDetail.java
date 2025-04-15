package de.kaleidox.hhh19356;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MyDetail {
    private @Convert(converter = MyConverter.class) MyOption option;
    private                                         int      number;
    private                                         String   value;

    public MyOption getOption() {
        return option;
    }

    public void setOption(MyOption attribute) {
        this.option = attribute;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int someNumber) {
        this.number = someNumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String someText) {
        this.value = someText;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOption(), getNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyDetail myDetail = (MyDetail) o;
        return getNumber() == myDetail.getNumber() && Objects.equals(getOption(), myDetail.getOption());
    }
}
