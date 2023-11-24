package com.sameh.taskjava.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Company implements Parcelable {

    private int id;
    private String name;
    private String subTitle;
    private String overview;
    private String website;
    private int logo;
    private int cover;
    private Companies company;

    protected Company(Parcel in) {
        id = in.readInt();
        name = in.readString();
        subTitle = in.readString();
        overview = in.readString();
        website = in.readString();
        logo = in.readInt();
        cover = in.readInt();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(subTitle);
        dest.writeString(overview);
        dest.writeString(website);
        dest.writeInt(logo);
        dest.writeInt(cover);
    }

    public Company() {
    }

    public Company(int id, String name, String subTitle, String overview, String website, int logo, int cover, Companies company) {
        this.id = id;
        this.name = name;
        this.subTitle = subTitle;
        this.overview = overview;
        this.website = website;
        this.logo = logo;
        this.cover = cover;
        this.company = company;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }

    public Companies getCompany() {
        return company;
    }

    public void setCompany(Companies company) {
        this.company = company;
    }
}
