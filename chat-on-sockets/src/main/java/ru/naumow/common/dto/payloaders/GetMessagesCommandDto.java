package ru.naumow.common.dto.payloaders;

import ru.naumow.common.dto.CommandPayloader;

public class GetMessagesCommandDto extends CommandPayloader<GetMessagesCommandDto> {

    private Integer page;
    private Integer size;

    public Integer getPage() {
        return page;
    }

    public GetMessagesCommandDto setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getSize() {
        return size;
    }

    public GetMessagesCommandDto setSize(Integer size) {
        this.size = size;
        return this;
    }
}
