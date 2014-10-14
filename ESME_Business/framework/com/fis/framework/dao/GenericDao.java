package com.fis.framework.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public abstract interface GenericDao<T, PK extends Serializable>
{
  public abstract PK persist(T paramT)
    throws Exception;

  public abstract void update(T paramT)
    throws Exception;

  public abstract void delete(T paramT)
    throws Exception;

  public abstract void deleteAll()
    throws Exception;

  public abstract T findById(PK paramPK)
    throws Exception;

  public abstract T findByIdLock(PK paramPK, boolean paramBoolean)
    throws Exception;

  public abstract T findByIdUnique(PK paramPK)
    throws Exception;

  public abstract List<T> findAll()
    throws Exception;

  public abstract List<T> findByExample(T paramT)
    throws Exception;

  public abstract void flush()
    throws Exception;

  public abstract void clear()
    throws Exception;

  public abstract Date getSystemDate()
    throws Exception;
}

/* Location:           D:\1 NTFS\Project\MCA\WIP\Source\McaBusiness\build\classes\
 * Qualified Name:     com.fis.framework.dao.GenericDao
 * JD-Core Version:    0.6.0
 */