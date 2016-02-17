package com.ash.radio.mainservice.repository;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ash.radio.mainservice.models.SongRequest;
import com.ash.radio.mainservice.models.User;
import com.ash.radio.mainservice.persistent.HibernateUtil;

public class SongRequestRepository {

	public void addRequest(SongRequest request) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		session.save(request);
		tr.commit();
		session.close();
	}

	public SongRequest getRequest(int id) {
		Session s = HibernateUtil.getSessionFactory().openSession();
		String query = "FROM SongRequest  SR where SR.id=" + id;

		Query q = s.createQuery(query);
		List srs = q.list();
		if (srs.size() > 0)
			return (SongRequest) q.list().get(0);
		else
			return null;

	}

	public List<SongRequest> getNotAcceptedRequests() {
		Session s = HibernateUtil.getSessionFactory().openSession();
		String query = "FROM SongRequest SR where SR.accept=false";

		Query q = s.createQuery(query);
		return q.list();
	}

	public void IsAccept(int requestId, boolean accept) {
		SongRequest sr = getRequest(requestId);
		if (sr != null) {
			sr.setAccept(accept);

			Session s = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = s.beginTransaction();
			s.update(sr);
			transaction.commit();
		}
	}
}
