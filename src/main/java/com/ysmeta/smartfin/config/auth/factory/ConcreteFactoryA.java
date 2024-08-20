package com.ysmeta.smartfin.config.auth.factory;

import com.ysmeta.smartfin.config.auth.factory.product.ConcreteProductA;
import com.ysmeta.smartfin.config.auth.factory.product.IProduct;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 20.
 */
public class ConcreteFactoryA extends AbstractFactory {
	@Override
	protected IProduct createProduct() {
		return new ConcreteProductA();
	}
}
