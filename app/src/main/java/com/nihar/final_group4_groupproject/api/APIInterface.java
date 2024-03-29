package com.nihar.final_group4_groupproject.api;

import com.nihar.final_group4_groupproject.response.Response;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface APIInterface {
    //user login
    //user login
    @POST("Login.php")
    @FormUrlEncoded
    Call<Response.UserLogin> performUserLogin(
            @Field("mobile_primary") String mobile_primary,
            @Field("password") String password
    );

    //signup user
    @POST("Signup.php")
    @FormUrlEncoded
    Call<Response.GeneralResponse> performUserSignup(
            @Field("mobile_primary") String mobile_primary,
            @Field("first_name") String first_name,
            @Field("middle_name") String middle_name,
            @Field("last_name") String last_name,
            @Field("password") String password
    );

    //modify user details
    @POST("ModifyProfile.php")
    @FormUrlEncoded
    Call<Response.GeneralResponse> performUpdateUserProfile(
            @Field("user_id") String user_id,
            @Field("mobile_primary") String mobile_primary,
            @Field("first_name") String first_name,
            @Field("middle_name") String middle_name,
            @Field("last_name") String last_name,
            @Field("mobile_secondary") String mobile_secondary,
            @Field("email") String email,
            @Field("dob") String dob,
            @Field("anniversary") String anniversary,
            @Field("bloodgroup") String bloodgroup,
            @Field("gender") String gender,
            @Field("country") String country,
            @Field("state") String state,
            @Field("city") String city,
            @Field("zipcode") String zipcode,
            @Field("residential_address") String residential_address,
            @Field("position") String position,
            @Field("category") String category
    );

    //get all users
    @POST("GetAllUsers.php")
    @FormUrlEncoded
    Call<Response.AllUsers> performGetAllUsers(
            @Field("user_id") String user_id,
            @Field("mobile_primary") String mobile_primary
    );

    @POST("GetNewsletters.php")
    @FormUrlEncoded
    Call<Response.GetNewsletters> performGetNewsletters(
            @Field("user_id") String user_id,
            @Field("mobile_primary") String mobile_primary
    );

    @POST("GetAnnouncement.php")
    @FormUrlEncoded
    Call<Response.GetAnnouncement> performGetAnnouncement(
            @Field("topic") String topic
    );

    @POST("GetPhotosLinks.php")
    @FormUrlEncoded
    Call<Response.GetPhotosLinks> performGetPhotosLinks(
            @Field("user_id") String user_id,
            @Field("mobile_primary") String mobile_primary
    );

    // admin login
    @POST("Admin/AdminLogin.php")
    @FormUrlEncoded
    Call<AdminLoginModel> performAdminLogin(
            @Field("user_id") String user_id,
            @Field("admin_password") String admin_password
    );

    @POST("SendFeedback.php")
    @FormUrlEncoded
    Call<Response.GeneralResponse> performSendFeedback(
            @Field("user_id") String user_id,
            @Field("rating") String rating,
            @Field("feedback") String feedback,
            @Field("brand") String brand,
            @Field("model") String model,
            @Field("device") String device,
            @Field("version") String version
    );

    //admin registers new user
    @POST("Admin/AdminRegistersNewUser.php")
    @FormUrlEncoded
    Call<AdminGeneralResponse> performAdminRegistersNewUser(
            @Field("admin_id") String admin_id,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("category") String category,
            @Field("position") String position,
            @Field("mobile_primary") String mobile_primary
    );

    @POST("Admin/DeleteUser.php")
    @FormUrlEncoded
    Call<AdminGeneralResponse> performDeleteUser(
            @Field("user_id") String user_id
    );

    @POST("Admin/MakeNewAdmin.php")
    @FormUrlEncoded
    Call<AdminGeneralResponse> performMakeNewAdmin(
            @Field("user_id") String user_id,
            @Field("admin_made_by") String admin_made_by
    );

    @POST("Admin/ChangeAdminPassword.php")
    @FormUrlEncoded
    Call<AdminGeneralResponse> performChangeAdminPassword(
            @Field("admin_id") String admin_id,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );

    //admin registers new user
    @POST("Admin/MakeAnnouncement.php?apicall=new")
    @FormUrlEncoded
    Call<MakeAnnouncementResponse> performMakeAnnouncement(
            @Field("admin_id") String admin_id,
            @Field("topic") String topic,
            @Field("title") String title,
            @Field("message") String message
    );

    @POST("Admin/UploadPhotosLink.php")
    @FormUrlEncoded
    Call<AdminGeneralResponse> performUploadPhotosLink(
            @Field("admin_id") String admin_id,
            @Field("link") String link,
            @Field("description") String description
    );

    @Multipart
    @POST("FileUploader/API.php?apicall=profile_picture")
    Call<Response.UploadResponse> uploadProfilePicture(
            @Part MultipartBody.Part image,
            @Part("user_id") RequestBody userId
    );

    @Multipart
    @POST("FileUploader/API.php?apicall=newsletter")
    Call<Response.UploadResponse> uploadNewsletter(
            @Part MultipartBody.Part file,
            @Part("admin_id") RequestBody adminId,
            @Part("file_name") RequestBody fileName
    );

    @Streaming
    @GET
    Call<ResponseBody> downloadFileByUrl(@Url String fileName);

}
