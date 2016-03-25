package com.ash.radio.mainservice.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ash.radio.mainservice.models.User;
import com.ash.radio.mainservice.persistent.HibernateUtil;

public class UserRepository {

	public UserRepository(){
		
	}
	public boolean login(User user){
		Session s=HibernateUtil.getSessionFactory().openSession();
		String query="SELECT * FROM Users U WHERE U.gcmid =:gcmId AND U.role=:role";
		Query q=s.createSQLQuery(query);
		q.setParameter("gcmId", user.getGcmId());
		q.setParameter("role", user.getRole());
		return q.list().size()>0;
	}
	
	public boolean isGCMIDExist(User user){
		Session s=HibernateUtil.getSessionFactory().openSession();
		String query="SELECT * FROM Users U WHERE U.gcmid =:gcmId";
		Query q=s.createSQLQuery(query);
		q.setParameter("gcmId", user.getGcmId());		
		return q.list().size()>0;
	}
	
	public User getUser(int id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		String query = "FROM User  u where u.id=" + id;

		Query q = s.createQuery(query);
		List srs = q.list();
		if (srs.size() > 0){
			User u=(User) q.list().get(0);
			s.close();
			return u;
		}
		else
			s.close();
			return null;

	}
	
	public void updateUser(User user){
		Session s=HibernateUtil.getSessionFactory().openSession();
		Transaction t=s.beginTransaction();
		s.update(user);
		t.commit();
	}
	
	public void addUser(User user){
		Session s=HibernateUtil.getSessionFactory().openSession();
		Transaction t=s.beginTransaction();
		s.save(user);
		t.commit();
	}
	public List<User> getAllUsers(int role){
		Session s=HibernateUtil.getSessionFactory().openSession();
		String q="FROM User as U WHERE U.role ="+role;
		Query query=s.createQuery(q);
		return query.list();		
	}
	public void updateRole(int userId,int role)
	{
		Session s=HibernateUtil.getSessionFactory().openSession();
		Transaction t=s.beginTransaction();
		User user= getUser(userId);
		if(user!=null){
			user.setRole(role);
			s.update(user);
			t.commit();
		}
	}
}
