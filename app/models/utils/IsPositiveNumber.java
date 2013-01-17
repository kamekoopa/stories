package models.utils;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsPositiveNumber.NumberValidator.class})
public @interface IsPositiveNumber {

	String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
	@Retention(RUNTIME)
	@Documented
	@interface List {
    	IsPositiveNumber[] value();
	}


	class NumberValidator implements ConstraintValidator<IsPositiveNumber, Object> {

		@Override
		public void initialize(IsPositiveNumber constraintAnnotation) {
		}

		@Override
		public boolean isValid(Object value, ConstraintValidatorContext context) {

			if(value == null){
				return true;
			}

			if(value instanceof Number ){

				Number num = (Number)value;

				return num.intValue() >= 0;

			}if(value instanceof String ){

				String str = (String)value;

				try{
					Number num = Double.valueOf(str);
					return num.intValue() >= 0;
				}catch(NumberFormatException e){
					return false;
				}

			}else{
				return false;
			}
		}
	}
}
