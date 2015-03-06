package com.example.listmaker.server.dao;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.*;
import com.example.listmaker.common.domain.*;
import com.turbomanage.gwt.exception.TooManyResultsException;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Generic DAO for use with Objectify
 * 
 * @author turbomanage
 * 
 * @param <T>
 */
public class ObjectifyDao<T> extends OfyService
{

	static final int BAD_MODIFIERS = Modifier.FINAL | Modifier.STATIC
			| Modifier.TRANSIENT;

	protected Class<T> clazz;

	public ObjectifyDao()
	{
		// Magic: extract the type of T from the generic
		clazz = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Key<T> put(T entity)
	{
        return ofy().save().entity(entity).now();
	}

	public Map<Key<T>, T> putAll(Iterable<T> entities)
	{
		return ofy().save().entities(entities).now();
	}

	public void delete(T entity)
	{
        ofy().delete().entity(entity).now();
    }

    public void delete(long id)
    {
        Key<T> key = Key.create(clazz, id);
        deleteKey(key);
    }

    public void deleteKey(Key<T> entityKey)
	{
		ofy().delete().key(entityKey).now();
	}

	public void deleteAll(Iterable<T> entities)
	{
		ofy().delete().entities(entities).now();
	}

	public void deleteKeys(Iterable<Key<T>> keys)
	{
		ofy().delete().keys(keys).now();
	}

	public T get(Long id) throws NotFoundException
	{
		return ofy().load().type(this.clazz).id(id).safe();
	}

	public T get(Key<T> key) throws NotFoundException
	{
		return ofy().load().key(key).safe();
	}

	public Map<Key<T>, T> get(Iterable<Key<T>> keys)
	{
		return ofy().load().keys(keys);
	}

	public List<T> listAll()
	{
        return ofy().load().type(clazz).list();
    }

    /**
	 * Convenience method to get all objects matching a single property
	 * 
	 * @param propName
	 * @param propValue
	 * @return T matching Object
	 * @throws TooManyResultsException
	 */
	public T getByProperty(String propName, Object propValue)
			throws TooManyResultsException
	{
        QueryResultIterator<T> fetch = ofy().load().type(clazz)
                .filter(propName, propValue)
                .limit(2).iterator();
        if (!fetch.hasNext())
		{
			return null;
		}
		T obj = fetch.next();
		if (fetch.hasNext())
		{
			throw new TooManyResultsException(fetch.toString()
					+ " returned too many results");
		}
		return obj;
	}

	public List<T> listByProperty(String propName, Object propValue)
	{
        List<T> list = ofy().load().type(clazz).filter(propName, propValue).list();
        return list;
	}

	public List<Key<T>> listKeysByProperty(String propName, Object propValue)
	{
        return ofy().load().type(clazz).filter(propName, propValue).keys().list();
	}

//	public T getByExample(T exampleObj) throws TooManyResultsException
//	{
//		Query<T> q = buildQueryByExample(exampleObj);
//		Iterator<T> fetch = q.limit(2).list().iterator();
//		if (!fetch.hasNext())
//		{
//			return null;
//		}
//		T obj = fetch.next();
//		if (fetch.hasNext())
//		{
//			throw new TooManyResultsException(q.toString()
//					+ " returned too many results");
//		}
//		return obj;
//	}

//	public List<T> listByExample(T exampleObj)
//	{
//		Query<T> queryByExample = buildQueryByExample(exampleObj);
//		return queryByExample.list();
//	}

	public T getByOwner(User u) throws TooManyResultsException
	{
        Ref<User> userKey = Ref.create(u);
		return getByProperty("ownerKey", userKey);
	}

	public List<T> listByOwner(User u)
	{
        Ref<User> userKey = Ref.create(u);
        return listByProperty("ownerKey", userKey);
	}

	public Key<T> key(T obj)
	{
		return ofy().factory().keys().keyOf(obj);
	}

	public List<T> listChildren(Object parent)
	{
        return ofy().load().type(clazz).ancestor(parent).list();
	}

	public List<Key<T>> listChildKeys(Object parent)
	{
        return ofy().load().type(clazz).ancestor(parent).keys().list();
	}

//	protected Query<T> buildQueryByExample(T exampleObj)
//	{
//		Query<T> q = ofy().query(clazz);
//
//		// Add all non-null properties to query filter
//		for (Field field : clazz.getDeclaredFields())
//		{
//			// Ignore transient, embedded, array, and collection properties
//			if (field.isAnnotationPresent(Transient.class)
//					|| (field.isAnnotationPresent(Embedded.class))
//					|| (field.getType().isArray())
//					|| (field.getType().isArray())
//					|| (Collection.class.isAssignableFrom(field.getType()))
//					|| ((field.getModifiers() & BAD_MODIFIERS) != 0))
//				continue;
//
//			field.setAccessible(true);
//
//			Object value;
//			try
//			{
//				value = field.get(exampleObj);
//			} catch (IllegalArgumentException e)
//			{
//				throw new RuntimeException(e);
//			} catch (IllegalAccessException e)
//			{
//				throw new RuntimeException(e);
//			}
//			if (value != null)
//			{
//				q.filter(field.getName(), value);
//			}
//		}
//
//		return q;
//	}

}
