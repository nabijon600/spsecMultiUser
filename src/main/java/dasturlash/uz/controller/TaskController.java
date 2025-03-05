package dasturlash.uz.controller;

import dasturlash.uz.dto.TaskDTO;
import dasturlash.uz.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("")
    public ResponseEntity<TaskDTO> create(@RequestBody TaskDTO dto) {
        TaskDTO result = taskService.create(dto);
        return ResponseEntity.ok(result);
    }

    @GetMapping({"","/"})
    public ResponseEntity<List<TaskDTO>> getAll() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    //.requestMatchers(HttpMethod.GET,"/task/*").permitAll()
    @GetMapping("/activ")
    public ResponseEntity<List<TaskDTO>> getActiv() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }
    //.requestMatchers("/task/**").permitAll()
    @GetMapping("/activ/all")
    public ResponseEntity<List<TaskDTO>> getActivAll() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    //.requestMatchers("/task/finished/all", "/task/my/all").permitAll()
    @GetMapping("/finished/all")
    public ResponseEntity<List<TaskDTO>> getFinished() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }
    @GetMapping("/my/all")
    public ResponseEntity<List<TaskDTO>> getMyAll() {
        List<TaskDTO> result = taskService.getAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getById(@PathVariable String id) {
        TaskDTO result = taskService.getById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> update(@RequestBody TaskDTO student,
                                          @PathVariable("id") String id) {
        Boolean result = taskService.update(student, id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        taskService.delete(id);
        return ResponseEntity.ok().build();
    }
}
