package fileUpload.Tables;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UploadedFile")
public class UploadedFile {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    public Integer id;

    @Column(name = "fileName")
    public String fileName;

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

    public Date getInsert_date() {
        return insert_date;
    }

    public void setInsert_date(Date insert_date) {
        this.insert_date = insert_date;
    }
    }
