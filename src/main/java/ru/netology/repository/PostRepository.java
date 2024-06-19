package ru.netology.repository;


import ru.netology.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
public class PostRepository {
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);
    public List<Post> all() {
        return posts.values().stream().collect(Collectors.toList());
    }
    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }
    public Post save(Post post) {
        if (post.getId() == 0) {
            long id = counter.incrementAndGet();
            Post newPost = new Post(id, post.getContent());
            posts.put(id, newPost);
            return newPost;
        } else {
            posts.put(post.getId(), post);
            return post;
        }
    }
    public void removeById(long id) {
        posts.remove(id);
    }
}
