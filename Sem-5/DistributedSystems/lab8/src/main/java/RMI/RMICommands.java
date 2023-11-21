package RMI;

import DAO.DataAccessObject;
import Models.Group;
import Models.Student;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RMICommands extends UnicastRemoteObject implements RMICommandsInterface {
    private DataAccessObject dao;

    public RMICommands() throws RemoteException {
    }

    public void createNewGroup(Group group) throws RemoteException {
        dao.createNewGroup(group);
    }

    public void createNewStudent(Student student) throws RemoteException {
        dao.createNewStudent(student);
    }

    public void deleteGroup(Integer id) throws RemoteException {
        dao.deleteGroup(id);
    }

    public void deleteStudent(Integer id) throws RemoteException {
        dao.deleteStudent(id);
    }

    public void updateGroup(Group group) throws RemoteException {
        dao.updateGroup(group);
    }

    public void updateStudent(Student student) throws RemoteException {
        dao.updateStudent(student);
    }

    public Group findGroupById(Integer id) throws RemoteException {
        return dao.findGroupById(id).get();
    }

    public Student findStudentById(Integer id) throws RemoteException {
        return dao.findStudentById(id).get();
    }

    public List<Student> findAllStudentsWithGroupId(Integer id) throws RemoteException {
        return dao.findAllStudentsWithGroupId(id);
    }

    public List<Group> findAllGroups() throws RemoteException {
        return dao.findAllGroups();
    }

    public void connectToDataBase() throws RemoteException {
        dao = new DataAccessObject();
    }

    public void closeConnection() throws RemoteException {
        dao.closeConnection();
    }
}