package com.example.disciteomnes.api;

import com.example.disciteomnes.model.Group;
import com.example.disciteomnes.model.ApiResponse;
import com.example.disciteomnes.model.GroupRequest;
import com.example.disciteomnes.model.LoginRequest;
import com.example.disciteomnes.model.LoginResponse;
import com.example.disciteomnes.model.RegisterRequest;
import com.example.disciteomnes.model.TaskRequest;
import com.example.disciteomnes.model.Task;

import retrofit2.Call;
import retrofit2.http.*;
import java.util.List;

// Define request/response models separately
public interface DisciteOmnesApi {
    // --- User Authentication ---
    @POST("auth/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest registerRequest);
    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
    // --- DisciteOmnes Management ---
    @GET("groups")
    Call<List<Group>> getGroups(@Header("Authorization") String token);
    @POST("groups")
    Call<Group> createGroup(@Header("Authorization") String token, @Body GroupRequest groupRequest);
    @POST("groups/{groupId}/join")
    Call<ApiResponse> joinGroup(@Header("Authorization") String token, @Path("groupId") String groupId);
    // --- Task Management ---
    @GET("groups/{groupId}/tasks")
    Call<List<Task>> getTasks(@Header("Authorization") String token, @Path("groupId") String groupId);
    @POST("groups/{groupId}/tasks")
    Call<Task> addTask(@Header("Authorization") String token, @Path("groupId") String groupId, @Body TaskRequest taskRequest);
    @PUT("tasks/{taskId}")
    Call<Task> updateTaskStatus(@Header("Authorization") String token, @Path("taskId") String taskId, @Body TaskRequest updateRequest);

    @DELETE("tasks/{taskId}")
    Call<ApiResponse> deleteTask(@Header("Authorization") String token, @Path("taskId") String taskId);

    @PUT("groups/{groupId}")
    Call<Group> updateGroup(
            @Header("Authorization") String token,
            @Path("groupId") String groupId,
            @Body GroupRequest groupRequest
    );

    @DELETE("groups/{groupId}")
    Call<ApiResponse> deleteGroup(
            @Header("Authorization") String token,
            @Path("groupId") String groupId
    );



}
