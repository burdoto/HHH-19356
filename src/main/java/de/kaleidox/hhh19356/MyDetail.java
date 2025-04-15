package de.kaleidox.hhh19356;

import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.Objects;

@Embeddable
public class MyDetail {
    @Convert(converter = MyConverter.class) Instant time;
    String someText;
    int    someNumber;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public String getSomeText() {
        return someText;
    }

    public void setSomeText(String someText) {
        this.someText = someText;
    }

    public int getSomeNumber() {
        return someNumber;
    }

    public void setSomeNumber(int someNumber) {
        this.someNumber = someNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTime(), getSomeText(), getSomeNumber());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MyDetail myDetail = (MyDetail) o;
        return getSomeNumber() == myDetail.getSomeNumber() && Objects.equals(getTime(), myDetail.getTime()) && Objects.equals(getSomeText(),
                myDetail.getSomeText());
    }
}
