package com.milkcoop.services;

import com.milkcoop.data.model.vo.InventorySaleVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface InventorySaleServices {

    InventorySaleVO create(InventorySaleVO inventorySaleVO);

    InventorySaleVO update(InventorySaleVO inventorySaleVO);

    void delete(Long id);

    Page<InventorySaleVO> find(Pageable pageable, LocalDate dataRegister);

    InventorySaleVO findById(Long id);
}
