package com.example.disciteomnes.repository;

import android.content.Context;

import com.example.disciteomnes.api.ApiClient;
import com.example.disciteomnes.api.DisciteOmnesApi;
import com.example.disciteomnes.model.Task;
import com.example.disciteomnes.model.TaskRequest;
import com.example.disciteomnes.model.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class TaskRepository {
    private final DisciteOmnesApi api;

    public TaskRepository(Context context) {
        api = ApiClient.getClient(context).create(DisciteOmnesApi.class);
    }

    public void createTask(String token, String groupId, TaskRequest request, Callback<Task> callback) {
        api.addTask("Bearer " + token, groupId, request).enqueue(callback);
    }

    public void getTasks(String token, String groupId, Callback<List<com.example.disciteomnes.model.Task>> callback) {
        Call<List<com.example.disciteomnes.model.Task>> call = api.getTasks("Bearer " + token, groupId);
        call.enqueue(callback);
    }

    public void updateTask(String token, String taskId, TaskRequest request, Callback<Task> callback) {
        api.updateTaskStatus("Bearer " + token, taskId, request).enqueue(callback);
    }

    public void deleteTask(String token, String taskId, Callback<ApiResponse> callback) {
        Call<ApiResponse> call = api.deleteTask("Bearer " + token, taskId);
        call.enqueue(callback);
    }
}
