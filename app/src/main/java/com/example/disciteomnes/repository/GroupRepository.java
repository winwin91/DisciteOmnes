package com.example.disciteomnes.repository;

import android.content.Context;

import com.example.disciteomnes.api.ApiClient;
import com.example.disciteomnes.api.DisciteOmnesApi;
import com.example.disciteomnes.model.Group;
import com.example.disciteomnes.model.GroupRequest;
import com.example.disciteomnes.model.ApiResponse;
import com.example.disciteomnes.model.TaskRequest;
import com.example.disciteomnes.model.TaskUpdateRequest;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class GroupRepository {

    private final DisciteOmnesApi api;

    public GroupRepository(Context context) {
        api = ApiClient.getClient(context).create(DisciteOmnesApi.class);
    }

    public void getGroups(String token, Callback<List<Group>> callback) {
        Call<List<Group>> call = api.getGroups("Bearer " + token);
        call.enqueue(callback);
    }

    public void createGroup(String token, GroupRequest request, Callback<Group> callback) {
        Call<Group> call = api.createGroup("Bearer " + token, request);
        call.enqueue(callback);
    }

    public void joinGroup(String token, String groupId, Callback<ApiResponse> callback) {
        Call<ApiResponse> call = api.joinGroup("Bearer " + token, groupId);
        call.enqueue(callback);
    }

    public void addTask(String token, String groupId, com.example.disciteomnes.model.TaskRequest taskRequest,
                        Callback<com.example.disciteomnes.model.Task> callback) {
        Call<com.example.disciteomnes.model.Task> call = api.addTask("Bearer " + token, groupId, taskRequest);
        call.enqueue(callback);
    }



    public void deleteGroup(String token, String groupId, Callback<ApiResponse> callback) {
        Call<ApiResponse> call = api.deleteGroup("Bearer " + token, groupId);
        call.enqueue(callback);
    }


}
