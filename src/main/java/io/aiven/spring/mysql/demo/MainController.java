package io.aiven.spring.mysql.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
@RestController

@RequestMapping(path = "/news")
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody news addNewNews(@RequestParam String title , @RequestParam String body ,@RequestParam String authorName ){

        news springNews = new news();
        springNews.setTitle(title);
        springNews.setBody(body);
        springNews.setAuthorName(authorName);
        springNews.setLikes(0);
        springNews.setPostDate(LocalDateTime.now().toString());
        userRepository.save(springNews);
        return springNews;
    }

    @GetMapping(path="all")
    public @ResponseBody Iterable<news> getAllNews(){
        return userRepository.findAll();
    }

    @GetMapping(path="/like/{id}")
    public @ResponseBody news likeNews(@PathVariable Integer id){
        news news = userRepository.findById(id).get();
        news.setLikes(news.getLikes()+1);
        userRepository.save(news);
        return news;
    }

    @GetMapping(path="/dislike/{id}")
    public @ResponseBody news dislikeNews(@PathVariable Integer id){
        news news = userRepository.findById(id).get();
        news.setLikes(news.getLikes()-1);
        userRepository.save(news);
        return news;
    }

    @GetMapping(path="/delete/{id}")
    public @ResponseBody String deleteNews(@PathVariable Integer id){
        userRepository.deleteById(id);
        return "Deleted";
    }

    @GetMapping(path="/update/{id}")
    public @ResponseBody news updateNews(@PathVariable Integer id,@RequestParam String title , @RequestParam String body ,@RequestParam String authorName ){
        news news = userRepository.findById(id).get();
        news.setTitle(title);
        news.setBody(body);
        news.setAuthorName(authorName);
        userRepository.save(news);
        return news;
    }

    @GetMapping(path="/get/{id}")
    public @ResponseBody news getNews(@PathVariable Integer id){
        return userRepository.findById(id).get();
    }

}
