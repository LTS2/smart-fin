package com.ysmeta.smartfin.config.auth.factory;

import com.ysmeta.smartfin.config.auth.factory.product.ConcreteProductB;
import com.ysmeta.smartfin.config.auth.factory.product.IProduct;

/**
 * 클래스입니다.
 *
 * @author : ewjin
 * @version : 0.0.1
 * @since : 2024. 8. 20.
 */
public class ConcreteFactoryB extends AbstractFactory {
	@Override
	protected IProduct createProduct() {
		return new ConcreteProductB();
	}
}
