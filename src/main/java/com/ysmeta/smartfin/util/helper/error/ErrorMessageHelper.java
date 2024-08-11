package com.ysmeta.smartfin.util.helper.error;

import java.util.Optional;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

/**
 * TODO: 2024. 8. 12. 에 현재 클래스 위치는 smartfin/util/helper/error/ 에 위치한다. 하지만 해당 에러 객체가 전역적인 관심사가 된다면 common 패키지로 옮기는 것을 고려중이다.
 * ErrorMessageHelper 클래스는 유효성 검사 과정에서 발생하는 오류 메시지를 처리하는 헬퍼 클래스입니다.
 * <p>
 * 이 클래스는 BindingResult 객체에서 발생한 첫 번째 오류 메시지를 추출하여 반환합니다.
 * 만약 오류 메시지가 존재하지 않으면 기본적으로 "유효하지 않은 값입니다."라는 메시지를 반환합니다.
 * <p>
 * 이 클래스는 상태를 가지지 않으며, 정적 메서드를 통해 오류 메시지 처리를 지원합니다.
 * 상태를 가지지 않는다는 말의 의미는 멤버 변수가 없다는 것을 뜻하고 만약 있더라도 메서드 호출이 해당 멤버 변수에 영향을 주지 않는 경우입니다.
 * 주로 컨트롤러에서 유효성 검사 실패 시 반환할 오류 메시지를 생성하는 데 사용됩니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 12.
 */
public class ErrorMessageHelper {

	/**
	 * 주어진 BindingResult 객체에서 첫 번째 유효성 검사 오류 메시지를 반환하거나, 오류 메시지가 없을 경우 기본 메시지("유효하지 않은 값입니다.")를 반환합니다.
	 *
	 * @param bindingResult 유효성 검사 결과를 담고 있는 BindingResult 객체
	 * @return 첫 번째 유효성 검사 오류 메시지를 반환하며, 오류가 없을 경우 기본 메시지("유효하지 않은 값입니다.")를 반환합니다.
	 */
	public static String getOrDefaultErrorMessage(BindingResult bindingResult) {
		return Optional.ofNullable(bindingResult.getFieldError())
			.map(DefaultMessageSourceResolvable::getDefaultMessage)
			.orElse("유효하지 않은 값입니다.");
	}
}
