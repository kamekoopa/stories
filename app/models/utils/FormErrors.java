package models.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import play.data.Form;
import play.data.validation.ValidationError;
import play.i18n.Messages;

public class FormErrors implements Iterable<String>{

	private final Form<?> form;

	public FormErrors(Form<?> form){
		this.form = form;
	}

	public boolean exists(){
		return this.form.hasErrors();
	}

	@Override
	public Iterator<String> iterator(){

		List<String> errorMessages = new ArrayList<>();

		for(Entry<String, List<ValidationError>> errorMap : this.form.errors().entrySet()){
			for(ValidationError error : errorMap.getValue()){
				errorMessages.add(Messages.get(error.message(), error.arguments().toArray()));
			}
		}

		return errorMessages.iterator();
	}
}
