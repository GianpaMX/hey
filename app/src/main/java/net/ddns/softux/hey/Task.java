package net.ddns.softux.hey;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by juan on 5/06/16.
 */

public class Task extends BaseObservable implements Parcelable {
    private String key;
    private String title;
    private String description;

    public Task() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(net.ddns.softux.hey.BR.title);
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(net.ddns.softux.hey.BR.description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.title);
        dest.writeString(this.description);
    }

    protected Task(Parcel in) {
        this.key = in.readString();
        this.title = in.readString();
        this.description = in.readString();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel source) {
            return new Task(source);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("description",description);
        return map;
    }
}
