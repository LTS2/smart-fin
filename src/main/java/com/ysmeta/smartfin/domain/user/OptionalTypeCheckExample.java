package com.ysmeta.smartfin.domain.user;

import java.beans.PropertyEditor;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 11.
 */
public class OptionalTypeCheckExample {

	public static void main(String[] args) {
		// 예제용 BindingResult와 FieldError 생성
		BindingResult bindingResult = new MockBindingResult();
		FieldError fieldError = new FieldError("objectName", "field", "This is the default message.");
		bindingResult.addError(fieldError);

		// Step 1: Optional<FieldError>
		Optional<FieldError> optionalFieldError = Optional.ofNullable(bindingResult.getFieldError());
		System.out.println("Step 1: " + optionalFieldError.getClass().getModule());

		// Step 2: Optional<String>
		Optional<String> optionalMessage = optionalFieldError.map(DefaultMessageSourceResolvable::getDefaultMessage);
		System.out.println("Step 2: " + optionalMessage.getClass().getName());
		System.out.println("Step 2: " + optionalMessage.orElseThrow(() -> new NullPointerException("ddd")));

		// Step 3: String (Final result)
		String errorMessage = optionalMessage.orElse("유효하지 않은 값입니다.");
		System.out.println("Step 3: Final result is a String: " + errorMessage);
	}
}

// Mock class for BindingResult (for demonstration purposes)
class MockBindingResult implements BindingResult {
	private FieldError fieldError;

	@Override
	public String getObjectName() {
		return "";
	}

	@Override
	public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

	}

	@Override
	public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

	}

	@Override
	public List<ObjectError> getGlobalErrors() {
		return List.of();
	}

	@Override
	public List<FieldError> getFieldErrors() {
		return List.of();
	}

	@Override
	public FieldError getFieldError() {
		return this.fieldError;
	}

	@Override
	public Object getFieldValue(String field) {
		return null;
	}

	public void addError(FieldError fieldError) {
		this.fieldError = fieldError;
	}

	@Override
	public Object getTarget() {
		return null;
	}

	@Override
	public Map<String, Object> getModel() {
		return Map.of();
	}

	@Override
	public Object getRawFieldValue(String field) {
		return null;
	}

	@Override
	public PropertyEditor findEditor(String field, Class<?> valueType) {
		return null;
	}

	@Override
	public PropertyEditorRegistry getPropertyEditorRegistry() {
		return null;
	}

	@Override
	public String[] resolveMessageCodes(String errorCode) {
		return new String[0];
	}

	@Override
	public String[] resolveMessageCodes(String errorCode, String field) {
		return new String[0];
	}

	@Override
	public void addError(ObjectError error) {

	}

}
