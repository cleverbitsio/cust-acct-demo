import java.io.Serializable;
import java.util.Date;
//import org.joda.time.DateTime;
//import org.joda.time.LocalDate;


public class Transaction implements Serializable{

    private static final long serialVersionUID = 1L;
    private Date time;
    private String type, description;
    private Integer paid_in, paid_out;

    public Transaction(Date time, String type, String description, Integer paid_in, Integer paid_out) {
        this.time = time;
        this.type = type;
        this.description = description;
        this.paid_in = paid_in;
        this.paid_out = paid_out;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPaid_in() {
        return paid_in;
    }

    public void setPaid_in(Integer paid_in) {
        this.paid_in = paid_in;
    }

    public Integer getPaid_out() {
        return paid_out;
    }

    public void setPaid_out(Integer paid_out) {
        this.paid_out = paid_out;
    }

}
