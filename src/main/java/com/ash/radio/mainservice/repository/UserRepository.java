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
		String query="SELECT * FROM User U WHERE U.name =:name AND U.password=:pswrd";
		
		Query q=s.createSQLQuery(query);
		q.setParameter("name", user.getName());
		q.setParameter("pswrd", user.getPassword());
		return q.list().size()>0;
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
}
