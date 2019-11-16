package com.scaler.pay.mapper;

import com.scaler.pay.mapper.entity.PaymentTransactionLogEntity;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

@Component
public interface PaymentTransactionLogMapper {

	@Insert("INSERT INTO `payment_transaction_log` VALUES (NULL, NULL, #{asyncLog},NULL, #{transactionId}, null, null, NOW(), null, NOW());")
	public int insertTransactionLog(PaymentTransactionLogEntity paymentTransactionLog);

}
