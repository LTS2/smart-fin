package com.ysmeta.smartfin.config.auth.factory;

import com.ysmeta.smartfin.config.auth.factory.product.IProduct;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 20.
 */
public abstract class AbstractFactory {
	final IProduct createOperation() {
		IProduct product = createProduct(); // 서브클래스에서 구체화한 팩토리 메서드 실행
		product.setting();
		return product;
	}

	abstract protected IProduct createProduct();
}
