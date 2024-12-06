package br.ifsp.persistence;

import br.ifsp.services.EmployeeDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SqliteEmployeeDAO implements EmployeeDAO {

    @Override
    public void save(EmployeeDTO employee) {
        final String sql = """
                INSERT INTO Employee (id, name, birth_date, sold_value, consultant_in_charge) 
                VALUES (?, ?, ?, ?, ?)
                """;
        try (var stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, employee.id());
            stmt.setString(2, employee.name());
            stmt.setString(3, employee.birthDate().toString());
            stmt.setDouble(4, employee.soldValue());
            stmt.setString(5, employee.inChargeId());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(EmployeeDTO employee) {
        final String sql = """
                UPDATE Employee SET 
                    name = ?, 
                    birth_date = ?, 
                    sold_value = ?, 
                    consultant_in_charge = ? 
                WHERE id = ?
                """;
        try (var stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, employee.name());
            stmt.setString(2, employee.birthDate().toString());
            stmt.setDouble(3, employee.soldValue());
            stmt.setString(4, employee.inChargeId());
            stmt.setString(5, employee.id());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public Optional<EmployeeDTO> findById(String id) {
        final String sql = "SELECT * FROM Employee WHERE id = ?";
        try (var stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, id);

            final ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new EmployeeDTO(
                        rs.getString("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("birth_date")),
                        rs.getDouble("sold_value"),
                        rs.getString("consultant_in_charge")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<EmployeeDTO> findAll() {
        final List<EmployeeDTO> employees = new ArrayList<>();
        final String sql = "SELECT * FROM Employee";
        try (var stmt = ConnectionFactory.createPreparedStatement(sql)){
            final ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                employees.add(new EmployeeDTO(
                        rs.getString("id"),
                        rs.getString("name"),
                        LocalDate.parse(rs.getString("birth_date")),
                        rs.getDouble("sold_value"),
                        rs.getString("consultant_in_charge")
                ));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public void delete(String id) {
        final String sql = "DELETE FROM Employee WHERE id = ?";
        try (var stmt = ConnectionFactory.createPreparedStatement(sql)){
            stmt.setString(1, id);
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
