package pojo;

import java.util.Date;

/**
 * Created by mitesh on 21/12/16.
 */
public class File {
    public String Name;
    public long Size;
    public Date LastModifiedAt;

    public File() {}

    public File(String Name, long Size, Date LastModifiedAt) {
        this.Name = Name;
        this.Size = Size;
        this.LastModifiedAt = LastModifiedAt;
    }
}