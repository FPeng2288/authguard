package com.fpeng2288.authguard.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * ClassName: PageWrapper
 * Package: com.fpeng2288.authguard.common
 * Description:
 *
 * @author Fan Peng
 * Create 2024/12/24 2:05
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageWrapper<T extends Serializable> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    public static <T extends Serializable> PageWrapper<T> from(Page<T> page) {
        if (page == null) {
            throw new IllegalArgumentException("Page cannot be null");
        }
        return new PageWrapper<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }
}

