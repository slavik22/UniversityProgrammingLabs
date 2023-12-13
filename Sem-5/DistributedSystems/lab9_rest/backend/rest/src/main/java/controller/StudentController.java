package controller;

import dao.DataAccessObject;
import model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin
public class StudentController {
    private final DataAccessObject dao;

    public StudentController(){
        this.dao = new DataAccessObject();
    }


    @GetMapping("")
    public List<Student> getAllStudents() {
        return dao.findAllStudents();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
//        Optional<Student> student = studentRepository.findById(id);
//        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @PostMapping
    public ResponseEntity<Boolean> createStudent(@RequestBody Student student) {
        dao.createNewStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        dao.updateStudent(updatedStudent);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        dao.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}