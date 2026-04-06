package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.InternetBean;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class InternetModel {
//NextPK****-----------------
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_internet");
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
	public long add(InternetBean bean) throws ApplicationException, DuplicateRecordException, SQLException{

		InternetBean duplicate = findByName(bean.getName());

		if (duplicate != null) {
			throw new DuplicateRecordException("already exists");
		}
		Connection conn = null;
		int pk = 0;
		try {
			pk = nextPk();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_internet values(?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setDouble(3, bean.getPrice());
			pstmt.setInt(4,bean.getRange());
			
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());

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
	public void delete(InternetBean bean) throws SQLException, ApplicationException {
		Connection conn=null;
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_internet where id=?");
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
	
	public void update(InternetBean bean) throws SQLException, ApplicationException, DuplicateRecordException {
		Connection conn = null;
		
		InternetBean duplicate = findByName(bean.getName()); // Check if updated Role
		  if (duplicate != null && duplicate.getId() !=bean.getId()) { 
			  throw new DuplicateRecordException("Name already exists");
			  }
		 
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("update st_internet set name=? , price=?, `range`=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstmt.setString(1, bean.getName());
			pstmt.setDouble(2, bean.getPrice());
			pstmt.setInt(3,bean.getRange());
			
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());
			
			int i=pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			try {
			conn.rollback();
			}
			catch(Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
				}
				throw new ApplicationException("Exception in updating Role " + e);
      		}
		
		finally {
			JDBCDataSource.closeConnection(conn);
		}
	}
	//find by pk***************************---------------------------------
	public InternetBean findByPk(long pk) throws ApplicationException {
	
		InternetBean bean=null;
		Connection conn=null;
		try {
	conn=JDBCDataSource.getConnection();
	PreparedStatement pstmt=conn.prepareStatement("select * from st_internet where id=?");
	pstmt.setLong(1, pk);
	ResultSet rs = pstmt.executeQuery();
	while (rs.next()) {
		bean = new InternetBean();
		bean.setId(rs.getLong(1));
		bean.setName(rs.getString(2));
		bean.setPrice(rs.getInt(3));
		bean.setRange(rs.getInt(4));
		
		bean.setCreatedBy(rs.getString(5));
		bean.setModifiedBy(rs.getString(6));
		bean.setCreatedDatetime(rs.getTimestamp(7));
		bean.setModifiedDatetime(rs.getTimestamp(8));

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
	public InternetBean findByName(String name) throws ApplicationException {
		StringBuffer sql=new StringBuffer("select * from st_internet where name=?");
		InternetBean bean=null;
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean=new InternetBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setPrice(rs.getInt(3));
				bean.setRange(rs.getInt(4));
				
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				
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
	public List list(InternetBean bean) throws ApplicationException {
		
		return search(bean,0,0);
	}
	
	
	
	//search by filter************** with pagination---------------------
	public List search(InternetBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_internet where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id= " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND name like '" + bean.getName() + "%'");
			}
			if (bean.getPrice() > 0) {
				sql.append(" AND price = " + bean.getPrice());
			}
			if (bean.getRange() > 0) {
				sql.append(" AND `range` = " + bean.getRange());
			}
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
				bean=new InternetBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setPrice(rs.getInt(3));
				bean.setRange(rs.getInt(4));
				
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
		e.printStackTrace();
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
		StringBuffer sql = new StringBuffer("select * from st_internet");

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
				InternetBean bean = new InternetBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setPrice(rs.getInt(3));
				bean.setRange(rs.getInt(4));
				
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
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