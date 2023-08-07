package com.milkcoop.transform;

import com.milkcoop.data.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PayrollTransform {
    private List<ProducerDelivery> producerDeliveries = null;
    boolean persiste = false;
    private Payroll payroll = null;
    private LinkedHashMap<Long, List<ProducerDelivery>> producerDeliveriesForProducers = null;


    public Payroll gerarPayroll(List<ProducerDelivery> producerDeliveriesForPayroll, boolean persiste) {
        this.limparVariaveis(producerDeliveriesForPayroll);
        this.persiste = persiste;
        this.deliverysForProducer();
        this.computerValues();
        return this.payroll;
    }


    private void limparVariaveis(List<ProducerDelivery> producerDeliveriesForPayroll) {
        this.payroll = new Payroll();
        this.producerDeliveries = producerDeliveriesForPayroll;
        this.producerDeliveriesForProducers = new LinkedHashMap<Long, List<ProducerDelivery>>();
    }

    private void deliverysForProducer() {
        for (var p : this.producerDeliveries) {
            Long idProducer = p.getProducer().getId();
            if (this.producerDeliveriesForProducers.containsKey(idProducer)) {
                this.producerDeliveriesForProducers.get(idProducer).add(p);
            } else {
                List<ProducerDelivery> producerDeliveries = new ArrayList<ProducerDelivery>();
                producerDeliveries.add(p);
                this.producerDeliveriesForProducers.put(idProducer, producerDeliveries);
            }
        }
    }

    private void computerValues() {
        this.constructPayroll();
    }

    private void constructPayroll() {
        this.initPayrollProducers();
        this.calcAmountPayroll();

    }

    private void initPayrollProducers() {
        for (Map.Entry<Long, List<ProducerDelivery>> entry : producerDeliveriesForProducers.entrySet()) {
            PayrollProducer pp = this.constructPayrollProducer(entry.getValue());
            if (this.payroll.getPayrollProducerList() == null) {
                this.payroll.setPayrollProducerList(new ArrayList<>());
            }
            this.payroll.getPayrollProducerList().add(pp);
        }
    }

    private PayrollProducer constructPayrollProducer(List<ProducerDelivery> deliveriesForProducer) {
        PayrollProducer payrollProducer = new PayrollProducer();
        Producer producer = deliveriesForProducer.get(0).getProducer();

        payrollProducer.setPayroll(this.payroll);
        payrollProducer.setProducer(producer);

        this.initPayrollProducerDelivery(payrollProducer, deliveriesForProducer);
        this.calcAmount(payrollProducer);

        return payrollProducer;
    }

    private void initPayrollProducerDelivery(PayrollProducer payrollProducer, List<ProducerDelivery> deliveriesForProducer) {
        for (var p : deliveriesForProducer) {
            PayrollProducerDelivery bpf = this.constructPayrollProducerDelivery(payrollProducer, p);
            if (payrollProducer.getPayrollProducerDeliveries() == null) {
                payrollProducer.setPayrollProducerDeliveries(new ArrayList<>());
            }
            payrollProducer.getPayrollProducerDeliveries().add(bpf);
        }
        validPayrollProducer(payrollProducer);
    }

    private PayrollProducerDelivery constructPayrollProducerDelivery(PayrollProducer payrollProducer, ProducerDelivery producerDelivery) {
        var payrollProducerDelivery = new PayrollProducerDelivery();
        payrollProducerDelivery.setProducerDelivery(producerDelivery);
        payrollProducerDelivery.setPayrollProducer(payrollProducer);
        return payrollProducerDelivery;
    }

    private void validPayrollProducer(PayrollProducer payrollProducer) {
        validPayRollProducerDelivery(payrollProducer);
    }

    private void validPayRollProducerDelivery(PayrollProducer payrollProducer) {
        String cpf = payrollProducer.getProducer().getCpf();
        for (var pdd : payrollProducer.getPayrollProducerDeliveries()) {
            var producerDelivery = pdd.getProducerDelivery();
        }
    }

    private void calcAmount(PayrollProducer payrollProducer) {
        payrollProducer.setAmount(BigDecimal.ZERO);
        payrollProducer.setQuantity(BigDecimal.ZERO);
        for (var payrollProducerDelivery : payrollProducer.getPayrollProducerDeliveries()) {
            payrollProducer.setAmount(payrollProducer.getAmount().add(payrollProducerDelivery.getProducerDelivery().getQuantity().multiply(payrollProducerDelivery.getProducerDelivery().getProduct().getPrice())));
            payrollProducer.setQuantity(payrollProducer.getQuantity().add(payrollProducerDelivery.getProducerDelivery().getQuantity()));
        }
    }


    private void calcAmountPayroll() {
        this.payroll.setAmount(BigDecimal.ZERO);
        this.payroll.setQuantity(BigDecimal.ZERO);
        for (var payrollProducer : this.payroll.getPayrollProducerList()) {
            this.payroll.setAmount(this.payroll.getAmount().add(payrollProducer.getAmount()));
            this.payroll.setQuantity(this.payroll.getQuantity().add(payrollProducer.getQuantity()));
        }
    }


}
