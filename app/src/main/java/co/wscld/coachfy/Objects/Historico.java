package co.wscld.coachfy.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Historico extends RealmObject {
    @PrimaryKey
    private String id;
    private String treinoId;
    private long date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTreinoId() {
        return treinoId;
    }

    public void setTreinoId(String treinoId) {
        this.treinoId = treinoId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
