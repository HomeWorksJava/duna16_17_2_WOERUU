package fileUpload.Tables;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Log {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    public Integer id;

    public String fileName;

    public String url;

    public String host;

    @Column(name = "insert_date", insertable = true, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date insert_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Date getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(Date insert_date) {
        this.insert_date = insert_date;
    }

}
