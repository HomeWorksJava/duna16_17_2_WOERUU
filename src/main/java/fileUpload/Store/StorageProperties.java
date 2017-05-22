package fileUpload.Store;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("store")
public class StorageProperties {

    private String location = "feltöltött_fájlok";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
