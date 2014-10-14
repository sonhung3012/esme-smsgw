package com.fis.framework.service;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;

import com.fis.framework.bo.GenericBo;

public abstract class GenericService<T, PK extends Serializable, IBusiness extends GenericBo<T, PK>>
  implements GenericBo<T, PK>//,Isession
{
	Session session = null;

	public Session getSession() {
		 return this.session;
	}
	public void setSession(Session session) {
		 this.session=session;
	}
  private Class<?> type;
  private IBusiness interfaceBo;

  protected Class<?> getInterfaceBo()
  {
    return this.type;
  }
  public IBusiness getBusiness() {
    return this.interfaceBo;
  }

  public GenericService()
  {
    ParameterizedType superclass = (ParameterizedType)getClass()
      .getGenericSuperclass();
    this.type = 
      ((Class<?>)superclass
      .getActualTypeArguments()[2]);
    this.interfaceBo = (IBusiness) ((GenericBo)ServiceLocator.createService(this.type));
  }
}