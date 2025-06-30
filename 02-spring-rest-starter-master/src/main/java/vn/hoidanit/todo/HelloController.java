package vn.hoidanit.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hoidanit.todo.entity.Todo;
import vn.hoidanit.todo.entity.User;

@RestController
public class HelloController {

    @Autowired
    private ObjectMapper om;


    @GetMapping("/")
    public ResponseEntity<String> index() throws Exception {
        String json = """
                {
                    "name": "eric",
                    "email": "hoidanit@gmail.com"
                }
                """;

        User test = om.readValue(json, User.class);

        User eric = new User(null, "Eric", "test@gmail.com");

        String ericJson = om.writeValueAsString(eric);

        return ResponseEntity.ok(ericJson);
    }

    @GetMapping("/hoidanit")
    public ResponseEntity<Todo> hoidanit() {
        Todo test = new Todo(false, "hoidanit todo");
        return ResponseEntity.ok(test);
    }
}
