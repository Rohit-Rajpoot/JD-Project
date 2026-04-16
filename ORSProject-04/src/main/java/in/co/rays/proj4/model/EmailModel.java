package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.EmailBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class EmailModel {
	
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_email");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			 throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}
//ADD***************--------------------
	public long add(EmailBean bean) throws ApplicationException, DuplicateRecordException, SQLException{

		/*
		 * ArtBean duplicate = findByName(bean.getName());
		 * 
		 * if (duplicate != null) { throw new
		 * DuplicateRecordException("already exists"); }
		 */
		Connection conn = null;
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_email values(?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getEmailCode());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getSubject());
			pstmt.setString(5, bean.getStatus());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();
			System.out.println(i + "inserted");
			conn.commit();
			pstmt.close();
		}

		catch (Exception e) {
			conn.rollback();
			throw new ApplicationException("Exception : Exceptionin add Role");
		
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk;

	}
	//Delete*****************-----------------------------
	public void delete(EmailBean bean) throws SQLException, ApplicationException {
		Connection conn=null;
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_email where id=?");
			pstmt.setLong(1, bean.getId());
			
		int i=pstmt.executeUpdate();
		System.out.println( i +"row affected");
		conn.commit();
	
		pstmt.close();
		}
		
		catch(Exception e) {
			try{conn.rollback();
			}
			
			catch(Exception ex) {
		throw new ApplicationException("Exception : Delete rollback");

			}
			 throw new ApplicationException("Exception : Exception in delete Role");
			
			
		}
		 finally {
				JDBCDataSource.closeConnection(conn);
			}
	}
	
	//update*****************--------------------------------
	
	public void update(EmailBean bean) throws SQLException, ApplicationException, DuplicateRecordException {
		Connection conn=null;
		
		EmailBean duplicate = findByCode(bean.getEmailCode()); // Check if updated Role
		  if (duplicate != null && duplicate.getId() !=bean.getId()) { 
			  throw new DuplicateRecordException("Code already exists");
			  }
		 
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("update st_email set email_code=? , address=?, subject=?,status=?  ,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstmt.setString(1, bean.getEmailCode());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getSubject());
			pstmt.setString(4, bean.getStatus());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			
			int i=pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		}
		catch(Exception e) {
			try {
			conn.rollback();
			}
			catch(Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());

			}
			
			throw new ApplicationException("Exception in updating Role ");


		}
		
		finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	//find by pk***************************---------------------------------
	public EmailBean findByPk(long pk) throws ApplicationException {
	
		EmailBean bean=null;
		Connection conn=null;
		try {
	conn=JDBCDataSource.getConnection();
	PreparedStatement pstmt=conn.prepareStatement("select * from st_email where id=?");
	pstmt.setLong(1, pk);
	ResultSet rs = pstmt.executeQuery();
	while (rs.next()) {
		bean = new EmailBean();
		bean.setId(rs.getLong(1));
		bean.setEmailCode(rs.getString(2));
		bean.setAddress(rs.getString(3));
	
		bean.setSubject(rs.getString(4));
		bean.setStatus(rs.getString(5));
		bean.setCreatedBy(rs.getString(6));
		bean.setModifiedBy(rs.getString(7));
		bean.setCreatedDatetime(rs.getTimestamp(8));
		bean.setModifiedDatetime(rs.getTimestamp(9));

	}
		}
		catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
	return bean;
	}
	
	//find by name***************************---------------------------------
	public EmailBean findByCode(String emailCode) throws ApplicationException {
		StringBuffer sql=new StringBuffer("select * from st_email where email_code=?");
		EmailBean bean=null;
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1, emailCode);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean = new EmailBean();
				bean.setId(rs.getLong(1));
				bean.setEmailCode(rs.getString(2));
				bean.setAddress(rs.getString(3));
			
				bean.setSubject(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				
			}
		}
			catch(Exception e) {
				throw new ApplicationException("Exception : Exception in geting User by emailId");
				
			}
			finally {
				JDBCDataSource.closeConnection(conn);
			}
			return bean;
		
		
		
	}
	//find by filter without pagination ***************-----------------------------------
	public List list(EmailBean bean) throws ApplicationException {
		
		return search(bean,0,0);
	}
	
	
	
	//search by filter************** with pagination---------------------
	public List search(EmailBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_email where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id= " + bean.getId());
			}
			if (bean.getEmailCode() != null && bean.getEmailCode().length() > 0) {
				sql.append(" AND email_code like '" + bean.getEmailCode() + "%'");
			}
		
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" AND address like '" + bean.getAddress() + "%'");
			}
			 if (bean.getStatus() != null && bean.getStatus().length() > 0)
	                sql.append(" and status like '" +  bean.getStatus() + "%'");

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + "," + pageSize);

		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new EmailBean();
				bean.setId(rs.getLong(1));
				bean.setEmailCode(rs.getString(2));
				bean.setAddress(rs.getString(3));
			
				bean.setSubject(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			
	 throw new ApplicationException("Exception : Exception in search Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
	
	
//list all data search
	

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	
	public List list(int pageNo, int pageSize) throws ApplicationException {
		

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_email");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " , " + pageSize);
		}

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				EmailBean bean = new EmailBean();
				bean.setId(rs.getLong(1));
				bean.setEmailCode(rs.getString(2));
				bean.setAddress(rs.getString(3));
			
				bean.setSubject(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			
			throw new ApplicationException("Exception : Exception Geting list of Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	
		return list;
	}

}
