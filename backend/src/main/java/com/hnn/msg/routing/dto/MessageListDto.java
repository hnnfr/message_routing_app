package com.hnn.msg.routing.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class MessageListDto {
    private List<MessageDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    // for test
    public static MessageListDto getInstance(List<MessageDto> content, long totalElements, int totalPages) {
        MessageListDto dto = new MessageListDto();
        dto.setContent(content);
        dto.setPageNumber(0);
        dto.setPageSize(0);
        dto.setTotalElements(totalElements);
        dto.setTotalPages(totalPages);
        return dto;

    }

    public static MessageListDto fromPage(Page<MessageDto> page) {
        MessageListDto dto = new MessageListDto();
        dto.setContent(page.getContent());
        dto.setPageNumber(page.getNumber());
        dto.setPageSize(page.getSize());
        dto.setTotalElements(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        return dto;
    }

    public List<MessageDto> getContent() {
        return content;
    }

    public void setContent(List<MessageDto> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
