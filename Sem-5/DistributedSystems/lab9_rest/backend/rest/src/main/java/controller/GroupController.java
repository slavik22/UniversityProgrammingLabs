package controller;

import dao.DataAccessObject;
import model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/groups")
@CrossOrigin
public class GroupController {

    private final DataAccessObject dao;

    public GroupController(){
        this.dao = new DataAccessObject();
    }

    @GetMapping
    public List<Group> getAllGroups() {
        return dao.findAllGroups();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Integer id) {
        Optional<Group> group = dao.findGroupById(id);
        return group.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Boolean> createGroup(@RequestBody String groupName) {
        dao.createNewGroup(groupName);
        return ResponseEntity.status(HttpStatus.CREATED).body(true);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateGroup(@PathVariable Integer id, @RequestBody Group updatedGroup) {
        dao.updateGroup(updatedGroup);
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Integer id) {
        dao.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}

