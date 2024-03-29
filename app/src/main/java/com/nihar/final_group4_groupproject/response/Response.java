package com.nihar.final_group4_groupproject.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    public class GeneralResponse {
        @SerializedName("message")
        private String message;
        @SerializedName("response")
        private String response;

        public String getMessage() {
            return message;
        }

        public String getResponse() {
            return response;
        }
    }

    public class UserLogin {
        @SerializedName("message")
        private String message;
        @SerializedName("response")
        private String response;
        @SerializedName("user")
        private UserInfo user;

        public String getMessage() {
            return message;
        }

        public String getResponse() {
            return response;
        }

        public UserInfo getUser() {
            return user;
        }
    }

    public class AllUsers {
        @SerializedName("response")
        private String response;
        @SerializedName("users")
        private List<UserInfo> users;

        public String getResponse() {
            return response;
        }

        public List<UserInfo> getUsers() {
            return users;
        }
    }

    public class UserInfo {
        @SerializedName("anniversary")
        private String anniversary;
        @SerializedName("bloodgroup")
        private String bloodgroup;
        @SerializedName("category")
        private String category;
        @SerializedName("city")
        private String city;
        @SerializedName("country")
        private String country;
        @SerializedName("dob")
        private String dob;
        @SerializedName("email")
        private String email;
        @SerializedName("first_name")
        private String firstName;
        @SerializedName("gender")
        private String gender;
        @SerializedName("last_name")
        private String lastName;
        @SerializedName("middle_name")
        private String middleName;
        @SerializedName("mobile_primary")
        private String mobilePrimary;
        @SerializedName("mobile_secondary")
        private String mobileSecondary;
        @SerializedName("position")
        private String position;
        @SerializedName("residential_address")
        private String residentialAddress;
        @SerializedName("state")
        private String state;
        @SerializedName("user_id")
        private String userId;
        @SerializedName("zipcode")
        private String zipcode;
        @SerializedName("admin_flag")
        private boolean adminFlag;

        public String getAnniversary() {
            return anniversary;
        }

        public String getBloodgroup() {
            return bloodgroup;
        }

        public String getCategory() {
            return category;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getDob() {
            return dob;
        }

        public String getEmail() {
            return email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getGender() {
            return gender;
        }

        public String getLastName() {
            return lastName;
        }

        public String getMiddleName() {
            return middleName;
        }

        public String getMobilePrimary() {
            return mobilePrimary;
        }

        public String getMobileSecondary() {
            return mobileSecondary;
        }

        public String getPosition() {
            return position;
        }

        public String getResidentialAddress() {
            return residentialAddress;
        }

        public String getState() {
            return state;
        }

        public String getUserId() {
            return userId;
        }

        public String getZipcode() {
            return zipcode;
        }

        public boolean isAdminFlag() {
            return adminFlag;
        }
    }

    public class UploadResponse {
        @SerializedName("error")
        private boolean error;
        @SerializedName("file")
        private String file;
        @SerializedName("message")
        private String message;

        public boolean isError() {
            return error;
        }

        public String getFile() {
            return file;
        }

        public String getMessage() {
            return message;
        }
    }

    public class GetNewsletters {
        @SerializedName("newsletter")
        public List<Newsletter> newsletter;
        @SerializedName("response")
        public String response;

        public List<Newsletter> getNewsletter() {
            return newsletter;
        }

        public String getResponse() {
            return response;
        }
    }

    public class Newsletter {
        @SerializedName("file_name")
        private String fileName;
        @SerializedName("id")
        private String id;
        @SerializedName("path")
        private String path;
        @SerializedName("type")
        private String type;
        @SerializedName("upload_time")
        private String uploadTime;

        public String getFileName() {
            return fileName;
        }

        public String getId() {
            return id;
        }

        public String getPath() {
            return path;
        }

        public String getType() {
            return type;
        }

        public String getUploadTime() {
            return uploadTime;
        }
    }

    public class GetAnnouncement {
        @SerializedName("id")
        private String id;
        @SerializedName("message")
        private String message;
        @SerializedName("response")
        private String response;
        @SerializedName("time")
        private String time;
        @SerializedName("title")
        private String title;

        public String getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }

        public String getResponse() {
            return response;
        }

        public String getTime() {
            return time;
        }

        public String getTitle() {
            return title;
        }
    }

    public class GetPhotosLinks {
        @SerializedName("photos")
        private List<Photo> photos;
        @SerializedName("response")
        private String response;

        public List<Photo> getPhotos() {
            return photos;
        }

        public String getResponse() {
            return response;
        }
    }

    public class Photo {
        @SerializedName("description")
        private String description;
        @SerializedName("id")
        private String id;
        @SerializedName("link")
        private String link;
        @SerializedName("time")
        private String time;

        public String getDescription() {
            return description;
        }

        public String getId() {
            return id;
        }

        public String getLink() {
            return link;
        }

        public String getTime() {
            return time;
        }
    }
}