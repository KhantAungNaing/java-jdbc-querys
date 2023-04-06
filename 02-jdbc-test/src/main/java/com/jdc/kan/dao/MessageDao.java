package com.jdc.kan.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdc.kan.connectionassit.ConnectionManager;
import com.jdc.kan.dto.Message;

public class MessageDao {

	ConnectionManager manager;

	public MessageDao(ConnectionManager manager) {
		this.manager = manager;
	}

	public void createTable(String tableName) {

		String sql = """

				create table %s(
				id int primary key auto_increment,
				name varchar(50) not null,
				course varchar(100) not null,
				address varchar(100) not null,
				phNumber varchar(12) not null,
				email varchar(100) not null)

				""".formatted(tableName);

		try (var conn = manager.getConnection(); var stmt = conn.createStatement()) {

			stmt.execute(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public Message insertIntoTable(Message data) {

		String sql = "insert into message (name,course,address,phNumber,email) values (?,?,?,?,?) ";

		try (var conn = manager.getConnection();
				var stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setString(1, data.name());
			stmt.setString(2, data.course());
			stmt.setString(3, data.address());
			stmt.setString(4, data.phNumber());
			stmt.setString(5, data.email());

			stmt.executeUpdate();
			var res = stmt.getGeneratedKeys();

			if (res.next()) {
				var result = res.getInt(1);
				return data.cloneWithId(result);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Message findById(int id) {

		String sql = "select * from message where id = ?";

		try (var conn = manager.getConnection(); var stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			var res = stmt.executeQuery();

			if (res.next()) {
				return new Message(res.getInt(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public int updateById(int id, Message data) {

		String sql = "update message set name = ?, course = ?,address = ?,phNumber = ?,email = ? where id = ?";

		try (var conn = manager.getConnection(); var stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, data.name());
			stmt.setString(2, data.course());
			stmt.setString(3, data.address());
			stmt.setString(4, data.phNumber());
			stmt.setString(5, data.email());
			stmt.setInt(6, id);

			return stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int deleteById(int id) {

		String sql = "delete from message where id = ?";

		try (var conn = manager.getConnection(); var stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			return stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}
	
	public List<Message> insertMany(List<Message> messages) {
		
		List<Message> list = new ArrayList<>();
		
		if(null == messages) {
			return list;
		}
		
		String sql = "insert into message (name,course,address,phNumber,email) values (?,?,?,?,?) ";
		
		try(var conn = manager.getConnection();
				var stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
			
			for(Message message : messages) {
				stmt.setString(1,message.name());
				stmt.setString(2,message.course());
				stmt.setString(3,message.address());
				stmt.setString(4,message.phNumber());
				stmt.setString(5,message.email());
				stmt.addBatch();
			}
			
			stmt.executeBatch();
			
			var keys = stmt.getGeneratedKeys();
			int index = 0;
			
			while(keys.next()) {
				list.add(messages.get(index).cloneWithId(keys.getInt(1)));
				index++;
			}
			
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return null;
	}

}
