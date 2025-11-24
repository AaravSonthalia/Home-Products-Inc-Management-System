import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.util.ArrayList;  
import java.util.List;  
  
public class StudentService {  
  
  
    public List<Student> getAllStudents() {  
        List<Student> students = new ArrayList<>();  
        String query = "SELECT ID, FirstName, LastName FROM Student";  
  
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query);  
             ResultSet resultSet = preparedStatement.executeQuery()) {  
  
            while (resultSet.next()) {  
                Student student = new Student();  
                student.setId(resultSet.getInt("ID"));  
                student.setFirstName(resultSet.getString("FirstName"));  
                student.setLastName(resultSet.getString("LastName"));   
  
                students.add(student);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return students;  
    }  
  
    public Student getStudentById(int id) {  
        Student student = null;  
        String query = "SELECT * FROM Student WHERE ID = ?";  
  
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  
            preparedStatement.setInt(1, id);  
            try (ResultSet resultSet = preparedStatement.executeQuery()) {  
                if (resultSet.next()) {  
                    student = new Student();  
                    student.setId(resultSet.getInt("ID"));  
                    student.setFirstName(resultSet.getString("FirstName"));  
                    student.setLastName(resultSet.getString("LastName"));  
                    student.setAddress1(resultSet.getString("Address1"));  
                    student.setAddress2(resultSet.getString("Address2"));  
                    student.setCity(resultSet.getString("City"));  
                    student.setState(resultSet.getString("State"));  
                    student.setZip(resultSet.getString("Zip"));  
                    student.setBirthDay(resultSet.getDate("BirthDay"));  
                    student.setAdvisorId(resultSet.getInt("AdvisorID"));  
                }  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return student;  
    }  

    public boolean addStudent(Student student) {  
        String query = "INSERT INTO Student (FirstName, LastName, Address1, Address2, City, State, Zip, BirthDay, AdvisorID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";  
        boolean success = false;

        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  
            preparedStatement.setString(1, student.getFirstName());  
            preparedStatement.setString(2, student.getLastName());  
            preparedStatement.setString(3, student.getAddress1());  
            preparedStatement.setString(4, student.getAddress2());  
            preparedStatement.setString(5, student.getCity());  
            preparedStatement.setString(6, student.getState());  
            preparedStatement.setString(7, student.getZip());  
            preparedStatement.setDate(8, student.getBirthDay());  
            preparedStatement.setObject(9, student.getAdvisorId());  
  
            int rowsAffected = preparedStatement.executeUpdate();  
            if (rowsAffected > 0) {  
                success = true;
                //System.out.println("Student added successfully.");  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  

        return success;
    }     
}  
