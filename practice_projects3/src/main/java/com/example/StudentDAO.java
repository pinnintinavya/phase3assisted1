package com.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;

public class StudentDAO {

	private JdbcTemplate temp;

	public void setTemp(JdbcTemplate temp) {
		this.temp = temp;
	}
	
	/*Driver ->connection -> statement ->execute -> close 
	 * 
	 * */
	
	//Driver ->connection[spring.xml]
	
	public int insert(Student sobj) {
		String sql="insert into student values("+sobj.getId()+",'"+sobj.getName()+"','"+sobj.getEmail()+"')";
		//insert update delete 
		return temp.update(sql);
		
	}
	
	
	
	public Student getDetails(String name) {
		String sql="select * from student where sname=?";
		List<Student> students=temp.query(sql,new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1,name);
				
			}
		}, new StudentMapper() );
				
	return students.get(0);
	}



public List<Student> getallstudents(){
	String sql="select * from student";
	return temp.query(sql, new ResultSetExtractor<List<Student>>() {

		@Override
		public List<Student> extractData(ResultSet rs) throws SQLException, DataAccessException {
			// TODO Auto-generated method stub
			List<Student> list=new ArrayList<>();
			while(rs.next()) {
				Student student=new Student();
				student.setId(rs.getInt(1));
				student.setName(rs.getString(2));
				student.setEmail(rs.getString(3));
				list.add(student);
			}
			return list;
			
		}
	});
}

public int delete(int id) {
	String sql="delete from student where sid='"+id+"'";
	return temp.update(sql);
	
}
}

