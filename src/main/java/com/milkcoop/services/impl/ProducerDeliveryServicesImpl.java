package com.milkcoop.services.impl;

import com.milkcoop.data.model.Producer;
import com.milkcoop.data.model.ProducerDelivery;
import com.milkcoop.data.model.enums.PaymentStatus;
import com.milkcoop.data.model.vo.ProducerDeliveryVO;
import com.milkcoop.exceptions.ResourceNotFoundException;
import com.milkcoop.repository.ProducerDeliveryRepository;
import com.milkcoop.repository.ProducerRepository;
import com.milkcoop.repository.ProductRepository;
import com.milkcoop.services.ProducerDeliveryServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProducerDeliveryServicesImpl implements ProducerDeliveryServices {

    @Autowired
    ProducerDeliveryRepository producerDeliveryRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProducerRepository producerRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProducerDeliveryVO create(ProducerDeliveryVO producerDeliveryVO) throws ParseException {
        var entity = toConvert(producerDeliveryVO);
        entity.setStatus(PaymentStatus.PAGAMENTO_PENDENTE);
        var product = productRepository.findNearestToLancamento(entity.getDataRegister()).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrato!"));
        product.setQuantity(product.getQuantity().add(entity.getQuantity()));
        entity.setProduct(product);
        var producer = producerRepository.findById(entity.getProducer().getId()).orElseThrow(() -> new ResourceNotFoundException("Produtor não encontrato!"));
        entity.setProducer(producer);
        return toConvert(producerDeliveryRepository.save(entity));
    }

    @Override
    public ProducerDeliveryVO update(ProducerDeliveryVO producerDeliveryVO) {
        return null;
    }

    @Override
    public void delete(Long id) {
        var entity = producerDeliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unable to delete in this scenario"));
        entity.getProduct().setQuantity(entity.getProduct().getQuantity().subtract(entity.getQuantity()));
        producerDeliveryRepository.delete(entity);
    }

    @Override
    public Page<ProducerDeliveryVO> find(Pageable pageable, Long idProducer) {
        var page = producerDeliveryRepository.findByProducerId(pageable, idProducer);
        return page.map(this::convertToProducerDeliveryVO);
    }

    public Map<String, BigDecimal> dashboardProducer(Long idProducer) {
        Map<String, BigDecimal> mapDashBoard = new HashMap<>();

        var primeiroDiaMes = getPrimeiroDiaDoMesAtual();
        var ultimoDiaMes = getUltimoDiaDoMesAtual();
        var primeiroDiaAno = getPrimeiroDiaDoAnoAtual();
        var ultimoDiaAno = getUltimoDiaDoAnoAtual();

        BigDecimal totalProduzido = BigDecimal.ZERO;
        BigDecimal totalNoMes = BigDecimal.ZERO;
        BigDecimal totalNoAno = BigDecimal.ZERO;
        BigDecimal totalParaReceber = BigDecimal.ZERO;
        BigDecimal totalRecebidoNoAno = BigDecimal.ZERO;
        BigDecimal totalEmProcessamento = BigDecimal.ZERO;
        List<ProducerDelivery> listProducerDelivery;
        if (idProducer != null) {
            listProducerDelivery = producerDeliveryRepository.findByProducerId(idProducer);
        } else {
            listProducerDelivery = producerDeliveryRepository.findAll();
        }

        for (var producerDelivery : listProducerDelivery) {
            // Aqui, você deve implementar a lógica para calcular os valores necessários
            // com base nas entregas de produtores e atualizar as variáveis de total.

            // Exemplo hipotético de lógica:
            BigDecimal totalEntrega = producerDelivery.getQuantity().multiply(producerDelivery.getProduct().getPrice());
            BigDecimal quantidade =  producerDelivery.getQuantity();
            totalProduzido = totalProduzido.add(quantidade);

            LocalDate dataEntrega = producerDelivery.getDataRegister();
            if (dataEntrega.isAfter(primeiroDiaMes) && dataEntrega.isBefore(ultimoDiaMes)) {
                totalNoMes = totalNoMes.add(quantidade);
            }

            if (dataEntrega.isAfter(primeiroDiaAno) && dataEntrega.isBefore(ultimoDiaAno)) {
                totalNoAno = totalNoAno.add(quantidade);
            }

            if (producerDelivery.getStatus() == PaymentStatus.PAGAMENTO_PENDENTE) {
                totalParaReceber = totalParaReceber.add(totalEntrega);
            }
            if (producerDelivery.getStatus() == PaymentStatus.PAGAMENTO_EM_PROCESSAMENTO) {
                totalEmProcessamento = totalEmProcessamento.add(totalEntrega);
            }

            if (producerDelivery.getStatus() == PaymentStatus.PAGAMENTO_CONCLUIDO && dataEntrega.isAfter(primeiroDiaAno)) {
                totalRecebidoNoAno = totalRecebidoNoAno.add(totalEntrega);
            }
        }

        mapDashBoard.put("totalProduzido", totalProduzido);
        mapDashBoard.put("totalNoMes", totalNoMes);
        mapDashBoard.put("totalNoAno", totalNoAno);
        mapDashBoard.put("totalParaReceber", totalParaReceber);
        mapDashBoard.put("totalEmProcessamento", totalEmProcessamento);
        mapDashBoard.put("totalRecebidoNoAno", totalRecebidoNoAno);
       /* if (idProducer != null) {
            mapDashBoard.put("Total Produzido", producerDeliveryRepository.totalQuantity(idProducer));
            mapDashBoard.put("Total no Mês", producerDeliveryRepository.totalMonth(idProducer, getPrimeiroDiaDoMesAtual(), getUltimoDiaDoMesAtual()));
            mapDashBoard.put("Total no Ano", producerDeliveryRepository.totalYear(idProducer, getPrimeiroDiaDoAnoAtual(), getUltimoDiaDoAnoAtual()));
            mapDashBoard.put("Total para Receber", producerDeliveryRepository.totalReceivable(idProducer, PaymentStatus.PAGAMENTO_PENDENTE));
            mapDashBoard.put("Total recebido no ano", producerDeliveryRepository.receivedInTheYear(idProducer, getPrimeiroDiaDoAnoAtual(), getUltimoDiaDoAnoAtual(), PaymentStatus.PAGAMENTO_CONCLUIDO));
        } else {
            mapDashBoard.put("Total Produzido", producerDeliveryRepository.totalQuantity());
            mapDashBoard.put("Total no Mês", producerDeliveryRepository.totalMonth(getPrimeiroDiaDoMesAtual(), getUltimoDiaDoMesAtual()));
            mapDashBoard.put("Total no Ano", producerDeliveryRepository.totalYear(getPrimeiroDiaDoAnoAtual(), getUltimoDiaDoAnoAtual()));
            mapDashBoard.put("Total para Receber", producerDeliveryRepository.totalReceivable(PaymentStatus.PAGAMENTO_PENDENTE));
            mapDashBoard.put("Total recebido no ano", producerDeliveryRepository.receivedInTheYear(getPrimeiroDiaDoAnoAtual(), getUltimoDiaDoAnoAtual(), PaymentStatus.PAGAMENTO_CONCLUIDO));
        }
*/
        return mapDashBoard;
    }
    public List<ProducerDelivery> findCurrentMonthDeliveries() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        return producerDeliveryRepository.findByDataRegisterBetween(firstDayOfMonth, lastDayOfMonth);
    }

    public List<ProducerDelivery> findPreviousMonthDeliveries() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfPreviousMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfPreviousMonth = today.minusMonths(1).withDayOfMonth(today.minusMonths(1).lengthOfMonth());
        return producerDeliveryRepository.findByDataRegisterBetween(firstDayOfPreviousMonth, lastDayOfPreviousMonth);
    }
    public BigDecimal calculateTotalDeliveries(List<ProducerDelivery> deliveries) {
        BigDecimal totalDeliveries = BigDecimal.ZERO;

        for (ProducerDelivery delivery : deliveries) {
            totalDeliveries = totalDeliveries.add(BigDecimal.ONE);
        }

        return totalDeliveries;
    }
    @Override
    public Map<String, Object> organizeInformation() {
        Map<String, Object> informationMap = new HashMap<>();
        var currentMonthDeliveries = findCurrentMonthDeliveries();
        var previousMonthDeliveries = findPreviousMonthDeliveries();

        // Calcular a produção total de leite no mês atual
        BigDecimal totalMilkProduction = calculateTotalMilkProduction(currentMonthDeliveries);
        informationMap.put("totalDaProducao", totalMilkProduction);

        // Calcular a quantidade total de entregas no mês atual
        BigDecimal totalDeliveries = calculateTotalDeliveries(currentMonthDeliveries);
        informationMap.put("totalDeEntregas", totalDeliveries);

        // Encontrar o produtor com maior produção no mês atual
        Map<String, BigDecimal> producerProductionMap = findProducerProduction(currentMonthDeliveries);
        Map<String, Object> producerWithHighestProduction = getProducerWithHighestProduction(producerProductionMap);
        informationMap.put("producerWithHighestProduction", producerWithHighestProduction);

        // Encontrar o produtor com maior crescimento em relação ao mês anterior
        Map<String, BigDecimal> previousProducerProductionMap = findProducerProduction(previousMonthDeliveries);
        Map<String, Object> producerWithHighestGrowth = getProducerWithHighestGrowth(producerProductionMap, previousProducerProductionMap);
        informationMap.put("producerWithHighestGrowth", producerWithHighestGrowth);

        // Calcular o valor total de pagamento para cada status de pagamento no mês atual
        Map<PaymentStatus, BigDecimal> paymentByStatusMap = calculatePaymentByStatus(currentMonthDeliveries);
        informationMap.put("paymentByStatus", paymentByStatusMap);

        return informationMap;
    }

    private Map<PaymentStatus, BigDecimal> calculatePaymentByStatus(List<ProducerDelivery> producerDeliveries) {
        Map<PaymentStatus, BigDecimal> paymentByStatusMap = new HashMap<>();
        paymentByStatusMap.put(PaymentStatus.PAGAMENTO_CONCLUIDO, BigDecimal.ZERO);
        paymentByStatusMap.put(PaymentStatus.PAGAMENTO_PENDENTE, BigDecimal.ZERO);
        paymentByStatusMap.put(PaymentStatus.PAGAMENTO_EM_PROCESSAMENTO, BigDecimal.ZERO);
        for (ProducerDelivery producerDelivery : producerDeliveries) {
            PaymentStatus status = definePagamentoStatus(producerDelivery);
            BigDecimal productionQuantity = producerDelivery.getQuantity();
            BigDecimal pricePerUnit = producerDelivery.getProduct().getPrice();
            BigDecimal totalPayment = productionQuantity.multiply(pricePerUnit);

            // Atualizar o valor total de pagamento para o status de pagamento
            paymentByStatusMap.put(status, paymentByStatusMap.getOrDefault(status, BigDecimal.ZERO).add(totalPayment));
        }
        return paymentByStatusMap;
    }

    private PaymentStatus definePagamentoStatus(ProducerDelivery producerDelivery) {
        if (producerDelivery.getStatus() == PaymentStatus.PAGAMENTO_CONCLUIDO ) {
            return PaymentStatus.PAGAMENTO_CONCLUIDO;
        } else if (producerDelivery.getStatus()==PaymentStatus.PAGAMENTO_PENDENTE) {
            return PaymentStatus.PAGAMENTO_PENDENTE;
        } else {
            return PaymentStatus.PAGAMENTO_EM_PROCESSAMENTO;
        }
    }

    private Map<String, BigDecimal> findProducerProduction(List<ProducerDelivery> deliveries) {
        Map<String, BigDecimal> producerProductionMap = new HashMap<>();

        for (ProducerDelivery delivery : deliveries) {
            Producer producer = delivery.getProducer();
            BigDecimal production = producerProductionMap.getOrDefault(producer.getFullName(), BigDecimal.ZERO);
            production = production.add(delivery.getQuantity());
            producerProductionMap.put(producer.getFullName(), production);
        }

        return producerProductionMap;
    }
    private BigDecimal getProducerWithHighestProductionValue(Map<String, BigDecimal> producerProductionMap) {
        BigDecimal highestProduction = BigDecimal.ZERO;

        for (BigDecimal production : producerProductionMap.values()) {
            if (production.compareTo(highestProduction) > 0) {
                highestProduction = production;
            }
        }

        return highestProduction;
    }

    private Map<String, Object> getProducerWithHighestProduction(Map<String, BigDecimal> producerProductionMap) {
        BigDecimal highestProduction = getProducerWithHighestProductionValue(producerProductionMap);
        Map<String, Object> producerWithHighestProductionMap = new HashMap<>();

        for (Map.Entry<String, BigDecimal> entry : producerProductionMap.entrySet()) {
            String producerName = entry.getKey();
            BigDecimal production = entry.getValue();
            if (production.compareTo(highestProduction) == 0) {
                producerWithHighestProductionMap.put("nome", producerName);
                producerWithHighestProductionMap.put("valor", production);
                break; // Encontramos o produtor com a maior produção, não precisamos mais percorrer o mapa
            }
        }

        return producerWithHighestProductionMap;
    }
    public BigDecimal calculateTotalMilkProduction(List<ProducerDelivery> listProducerDelivery) {
        BigDecimal totalProduction = BigDecimal.ZERO;

        for (ProducerDelivery delivery : listProducerDelivery) {
            totalProduction = totalProduction.add(delivery.getQuantity());
        }

        return totalProduction;
    }

    private Map<String, Object> getProducerWithHighestGrowth(Map<String, BigDecimal> currentMonthProductionMap, Map<String, BigDecimal> previousMonthProductionMap) {
        String producerWithHighestGrowth = null;
        BigDecimal highestGrowthPercentage = BigDecimal.ZERO;

        for (Map.Entry<String, BigDecimal> entry : currentMonthProductionMap.entrySet()) {
            String producerName = entry.getKey();
            BigDecimal currentMonthProduction = entry.getValue();
            BigDecimal previousMonthProduction = previousMonthProductionMap.getOrDefault(producerName, BigDecimal.ZERO);

            BigDecimal growthPercentage;
            if (previousMonthProduction.compareTo(BigDecimal.ZERO) != 0) {
                // Corrigir o cálculo da porcentagem de crescimento
                growthPercentage = currentMonthProduction.subtract(previousMonthProduction)
                        .divide(previousMonthProduction, 4, BigDecimal.ROUND_HALF_UP)
                        .multiply(BigDecimal.valueOf(100));
            } else {
                // Caso especial quando o valor anterior é zero, para evitar divisão por zero
                // Nesse caso, consideramos o crescimento como zero
                growthPercentage = BigDecimal.ZERO;
            }

            // Utilizar o valor absoluto da porcentagem de crescimento para encontrar o maior crescimento
            if (growthPercentage.abs().compareTo(highestGrowthPercentage.abs()) > 0) {
                highestGrowthPercentage = growthPercentage;
                producerWithHighestGrowth = producerName;
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("producerName", producerWithHighestGrowth);
        result.put("growthPercentage", highestGrowthPercentage);

        return result;
    }

    @Override
    public ProducerDeliveryVO findById(Long id) {
        return toConvert(producerDeliveryRepository.findById(id).orElseThrow());
    }

    public static LocalDate getPrimeiroDiaDoMesAtual() {
        return LocalDate.now().withDayOfMonth(1);
    }

    // Método para obter o último dia do mês atual
    public static LocalDate getUltimoDiaDoMesAtual() {
        LocalDate ultimoDiaDoMes = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        return ultimoDiaDoMes.minusDays(1);
    }

    // Método para obter o primeiro dia do ano atual
    public static LocalDate getPrimeiroDiaDoAnoAtual() {
        return LocalDate.now().withDayOfYear(1);
    }

    // Método para obter o último dia do ano atual
    public static LocalDate getUltimoDiaDoAnoAtual() {
        return LocalDate.now().withDayOfYear(1).plusYears(1).minusDays(1);
    }

    private ProducerDeliveryVO convertToProducerDeliveryVO(ProducerDelivery producerDelivery) {
        return toConvert(producerDelivery);

    }

    private ProducerDeliveryVO toConvert(ProducerDelivery producerDelivery) {
        var producerDeliveryVO = modelMapper.map(producerDelivery, ProducerDeliveryVO.class);
        return producerDeliveryVO;
    }

    private ProducerDelivery toConvert(ProducerDeliveryVO producerDeliveryVO) {
        return modelMapper.map(producerDeliveryVO, ProducerDelivery.class);

    }
}
