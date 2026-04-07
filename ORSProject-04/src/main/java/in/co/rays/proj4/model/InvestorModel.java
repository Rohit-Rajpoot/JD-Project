package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.InvestorBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class InvestorModel {
	
	public Integer nextPk() throws DatabaseException {
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_investor");
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
	public long add(InvestorBean bean) throws ApplicationException, DuplicateRecordException, SQLException{

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
			PreparedStatement pstmt = conn.prepareStatement("insert into st_investor values(?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getInvestorName());
			pstmt.setInt(3, bean.getInvestmentAmount());
			pstmt.setString(4, bean.getInvestmentType());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());

			int i = pstmt.executeUpdate();
			System.out.println(i + "inserted");
			conn.commit();
			pstmt.close();

	} catch (Exception e) {
		e.printStackTrace();
		try {
			conn.rollback();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
		}
		throw new ApplicationException("Exception : Exception in add Investor");
	} finally {
		JDBCDataSource.closeConnection(conn);
	}
	return pk;
}
	//Delete*****************-----------------------------
	public void delete(InvestorBean bean) throws SQLException, ApplicationException {
		Connection conn=null;
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("delete from st_investor where id=?");
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
	
	public void update(InvestorBean bean) throws SQLException, ApplicationException, DuplicateRecordException {
		Connection conn=null;
		
		InvestorBean duplicate = findByName(bean.getInvestorName()); // Check if updated Role
		  if (duplicate != null && duplicate.getId() !=bean.getId()) { 
			  throw new DuplicateRecordException("Name already exists");
			  }
		 
		try {
			
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt=conn.prepareStatement("update st_investor set investor_name=? ,investment_amount=?,investment_type=? ,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstmt.setString(1, bean.getInvestorName());
			pstmt.setInt(2, bean.getInvestmentAmount());
			pstmt.setString(3, bean.getInvestmentType());
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
	public InvestorBean findByPk(long pk) throws ApplicationException {
	
		InvestorBean bean=null;
		Connection conn=null;
		try {
	conn=JDBCDataSource.getConnection();
	PreparedStatement pstmt=conn.prepareStatement("select * from st_investor where id=?");
	pstmt.setLong(1, pk);
	ResultSet rs = pstmt.executeQuery();
	while (rs.next()) {
		bean = new InvestorBean();
		bean.setId(rs.getLong(1));
		bean.setInvestorName(rs.getString(2));
		bean.setInvestmentAmount(rs.getInt(3));
		bean.setInvestmentType(rs.getString(4));
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
	public InvestorBean findByName(String investorName) throws ApplicationException {
		
		StringBuffer sql=new StringBuffer("select * from st_investor where investor_name = ?");
		InvestorBean bean=null;
		Connection conn=null;
		try {
			conn=JDBCDataSource.getConnection();
			PreparedStatement pstmt=conn.prepareStatement(sql.toString());
			pstmt.setString(1, investorName);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				bean = new InvestorBean();
				bean.setId(rs.getLong(1));
				bean.setInvestorName(rs.getString(2));
				bean.setInvestmentAmount(rs.getInt(3));
				bean.setInvestmentType(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
	}
		}
			catch(Exception e) {
				e.printStackTrace();
				throw new ApplicationException("Exception : Exception in geting investor name");
				
			}
			finally {
				JDBCDataSource.closeConnection(conn);
			}
			return bean;
		}
	
	
		//search by filter************** with pagination---------------------
	public List search(InvestorBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("select * from st_investor where 1=1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" AND id= " + bean.getId());
			}
			if (bean.getInvestorName() != null && bean.getInvestorName().length() > 0) {
				sql.append(" AND investor_name like '" + bean.getInvestorName() + "%'");
			}
		
			if (bean.getInvestmentType() != null && bean.getInvestmentType().length() > 0) {
				sql.append(" AND investment_type like '" + bean.getInvestmentType() + "%'");
			}
			if (bean.getInvestmentAmount() > 0) {
				sql.append(" AND investment_amount = " + bean.getInvestmentAmount());
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
				bean = new InvestorBean();
				bean.setId(rs.getLong(1));
				bean.setInvestorName(rs.getString(2));
				bean.setInvestmentAmount(rs.getInt(3));
				bean.setInvestmentType(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

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
		StringBuffer sql = new StringBuffer("select * from st_investor");

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
				
				InvestorBean bean = new InvestorBean();
				bean.setId(rs.getLong(1));
				bean.setInvestorName(rs.getString(2));
				bean.setInvestmentAmount(rs.getInt(3));
				bean.setInvestmentType(rs.getString(4));
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
