package com.milkcoop.data.model.enums;

public enum PaymentStatus {
    PAGAMENTO_CONCLUIDO("Pagamento Concluído"),
    PAGAMENTO_PENDENTE("Pagamento Pendente"),
    PAGAMENTO_EM_PROCESSAMENTO("Pagamento em Processamento");

    private String descricao;

    private PaymentStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
