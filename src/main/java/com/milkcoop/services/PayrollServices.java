package com.milkcoop.services;

import com.milkcoop.data.model.vo.PayrollVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayrollServices {

    PayrollVO create(PayrollVO payrollVO);

    PayrollVO update(PayrollVO payrollVO);

    void delete(Long id);

    Page<PayrollVO> find(Pageable pageable, PayrollVO request);

    PayrollVO findById(Long id);
    PayrollVO generatePayrollForPendingDeliveries(boolean persist);
    void confirmPayment(Long id);

    }
