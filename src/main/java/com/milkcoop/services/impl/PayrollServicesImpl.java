package com.milkcoop.services.impl;

import com.milkcoop.data.model.Payroll;
import com.milkcoop.data.model.enums.PaymentStatus;
import com.milkcoop.data.model.vo.PayrollVO;
import com.milkcoop.exceptions.ResourceNotFoundException;
import com.milkcoop.repository.PayrollRepository;
import com.milkcoop.repository.ProducerDeliveryRepository;
import com.milkcoop.services.PayrollServices;
import com.milkcoop.transform.PayrollTransform;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PayrollServicesImpl implements PayrollServices {
    @Autowired
    private PayrollRepository payrollRepository;

    @Autowired
    private ProducerDeliveryRepository producerDeliveryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PayrollVO create(PayrollVO payrollVO) {
        var entity = toConvert(payrollVO);
        return toConvert(payrollRepository.save(entity));
    }

    @Override
    public PayrollVO update(PayrollVO payrollVO) {
        return null;
    }

    @Override
    public void delete(Long id) {
        var entity = payrollRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to delete in this scenario"));
        payrollRepository.delete(entity);
    }

    @Override
    public Page<PayrollVO> find(Pageable pageable, PayrollVO request) {
        if (request.getDataRegister() != null && request.getAmount() == null) {
            var page = payrollRepository.findByDataRegister(pageable, request.getDataRegister());
            return page.map(this::convertToPayrollVO);
        } else if (request.getDataRegister() == null && request.getAmount() != null) {
            var page = payrollRepository.findByAmount(pageable, request.getAmount());
            return page.map(this::convertToPayrollVO);
        } else if (request.getDataRegister() != null && request.getAmount() != null) {
            var page = payrollRepository.findByAmountAndDataRegister(pageable, request.getAmount(), request.getDataRegister());
            return page.map(this::convertToPayrollVO);
        } else {
            var page = payrollRepository.findAll(pageable);
            return page.map(this::convertToPayrollVO);
        }
    }

    @Override
    public PayrollVO findById(Long id) {
        return toConvert(payrollRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class}, timeout = 1111111)
    public PayrollVO generatePayrollForPendingDeliveries(boolean persist) {
        try {
            var producerDeliveries = producerDeliveryRepository.findByStatus(PaymentStatus.PAGAMENTO_PENDENTE);
            Payroll payroll;
            try {
                payroll = new PayrollTransform().gerarPayroll(producerDeliveries, persist);
                payroll.setDataRegister(LocalDate.now());
            } catch (Exception e) {
                throw new ResourceNotFoundException("Error creating payroll");
            }
            if (persist) {
                for (var payrollProducer : payroll.getPayrollProducerList()) {
                    for (var payrollProducerDelivery : payrollProducer.getPayrollProducerDeliveries()) {
                        payrollProducerDelivery.getProducerDelivery().setStatus(PaymentStatus.PAGAMENTO_EM_PROCESSAMENTO);
                    }
                }

                return toConvert(payrollRepository.save(payroll));
            }
            return toConvert(payroll);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error creating payroll");
        }
    }

    @Override
    public void confirmPayment(Long id) {
        var payroll = payrollRepository.findById(id).orElseThrow();
        for (var payrollProducer : payroll.getPayrollProducerList()) {
            for (var payrollProducerDelivery : payrollProducer.getPayrollProducerDeliveries()) {
                payrollProducerDelivery.getProducerDelivery().setStatus(PaymentStatus.PAGAMENTO_CONCLUIDO);
            }
        }
        payroll.setStatus(PaymentStatus.PAGAMENTO_CONCLUIDO);
        payrollRepository.save(payroll);
    }

    private PayrollVO convertToPayrollVO(Payroll payroll) {
        return toConvert(payroll);

    }

    private PayrollVO toConvert(Payroll payroll) {
        PayrollVO payrollVO = modelMapper.map(payroll, PayrollVO.class);
        return payrollVO;

    }

    private Payroll toConvert(PayrollVO payrollVO) {
        return modelMapper.map(payrollVO, Payroll.class);

    }
}
