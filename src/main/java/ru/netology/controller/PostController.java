package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.model.Post;
import ru.netology.service.PostService;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;
    public PostController(PostService service) {
        this.service = service;
    }
    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final java.util.List<ru.netology.model.Post> data = service.all();
        final Gson gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }
    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final Gson gson = new Gson();
        try {
            final ru.netology.model.Post post = service.getById(id);
            response.getWriter().print(gson.toJson(post));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final Gson gson = new Gson();
        try {
            final var post = gson.fromJson(body, Post.class);
            final var savedPost = service.save(post);
            response.getWriter().print(gson.toJson(savedPost));
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    public void removeById(long id, HttpServletResponse response) {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            service.removeById(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}