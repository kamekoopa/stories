package models.utils;

import java.sql.SQLException;

import javax.persistence.PersistenceException;

import models.exception.DuplicateException;

public class ExceptionConverter {

	public static void convertAndThrow(PersistenceException pe) throws DuplicateException {

		if (pe.getCause() instanceof SQLException){

			SQLException se = (SQLException)pe.getCause();
			switch (se.getErrorCode()){
				case 23505:
					throw new DuplicateException();
			}

		}else{
			throw pe;
		}
	}
}
