package fileUpload.Repository;

import fileUpload.Tables.Log;
import fileUpload.Tables.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {
}
